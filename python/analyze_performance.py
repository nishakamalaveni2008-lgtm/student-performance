
"""Read academic results from PostgreSQL, preprocess, and save charts."""
import os
import getpass
from pathlib import Path

import pandas as pd
import psycopg2
import matplotlib.pyplot as plt
import seaborn as sns

OUT = Path(__file__).resolve().parent / "output"
OUT.mkdir(exist_ok=True)


def load_data():
    conn = psycopg2.connect(
        host=os.getenv("DB_HOST", "localhost"),
        port=int(os.getenv("DB_PORT", "5432")),
        user=os.getenv("DB_USER", "postgres"),
        password=os.getenv("DB_PASSWORD") or getpass.getpass(
            "Enter PostgreSQL password: "
        ),
        dbname=os.getenv("DB_NAME", "student_performance"),
    )

    try:
        query = """
        SELECT s.student_id, s.name, s.department, s.year_of_study,
               sub.subject_id, sub.subject_name, m.internal_marks,
               m.exam_marks, m.total_marks, m.average, m.grade, m.status
        FROM marks m
        JOIN students s ON s.student_id = m.student_id
        JOIN subjects sub ON sub.subject_id = m.subject_id
        """

        with conn.cursor() as cursor:
            cursor.execute(query)
            rows = cursor.fetchall()
            columns = [desc[0] for desc in cursor.description]

        return pd.DataFrame(rows, columns=columns)

    finally:
        conn.close()


def main():
    df = load_data()

    if df.empty:
        print("No mark records found. Add students, subjects and marks first.")
        return

    # Remove duplicate records and handle missing numeric values.
    df = df.drop_duplicates()

    numeric = ["internal_marks", "exam_marks", "total_marks", "average"]

    for col in numeric:
        df[col] = pd.to_numeric(df[col], errors="coerce")
        df[col] = df[col].fillna(df[col].median())

    df["department"] = df["department"].fillna("Unknown")
    df["status"] = df["status"].fillna("UNKNOWN").str.upper()

    # Save cleaned dataset.
    df.to_csv(OUT / "cleaned_performance.csv", index=False)

    sns.set_theme()

    # Department-wise average performance.
    dept = df.groupby("department", as_index=False)["average"].mean()

    plt.figure(figsize=(8, 5))
    sns.barplot(data=dept, x="department", y="average")
    plt.title("Average Academic Performance by Department")
    plt.xlabel("Department")
    plt.ylabel("Average (%)")
    plt.tight_layout()
    plt.savefig(OUT / "department_average.png", dpi=160)
    plt.close()

    # Subject-wise pass percentage.
    subject = df.groupby("subject_name", as_index=False).agg(
        appeared=("status", "count"),
        passed=("status", lambda x: (x == "PASS").sum())
    )

    subject["pass_percentage"] = (
        100 * subject["passed"] / subject["appeared"]
    )

    plt.figure(figsize=(9, 5))
    sns.barplot(data=subject, x="subject_name", y="pass_percentage")
    plt.title("Subject-wise Pass Percentage")
    plt.xlabel("Subject")
    plt.ylabel("Pass (%)")
    plt.ylim(0, 100)
    plt.xticks(rotation=20, ha="right")
    plt.tight_layout()
    plt.savefig(OUT / "subject_pass_percentage.png", dpi=160)
    plt.close()

    # Distribution of student average marks.
    plt.figure(figsize=(8, 5))
    sns.histplot(data=df, x="average", bins=10, kde=True)
    plt.title("Distribution of Student Average Marks")
    plt.xlabel("Average (%)")
    plt.tight_layout()
    plt.savefig(OUT / "marks_distribution.png", dpi=160)
    plt.close()

    print(f"Analysis complete. Files saved to: {OUT}")

    print("\nDepartment averages:\n", dept.to_string(index=False))
    print("\nSubject pass percentages:\n", subject.to_string(index=False))


if __name__ == "__main__":
    main()
