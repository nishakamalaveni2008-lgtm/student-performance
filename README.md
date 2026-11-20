# Student Academic Performance and Result Analysis System (Scenario 8)

A teaching-project web application integrating Java, MySQL/DBMS, Software Engineering documentation and Python Foundations of Data Science.

## Technology Stack
- Java 17, Spring Boot 3.3, Spring Data JPA, Maven
- MySQL 8+
- HTML, Bootstrap 5, Thymeleaf
- Python 3.10+, Pandas, Matplotlib, Seaborn, mysql-connector-python

## Features
1. Register students and subjects.
2. Enter or update internal and exam marks.
3. Automatically calculate total, percentage, grade and pass/fail.
4. View all result records and individual student reports.
5. Retrieve records through JSON endpoints.
6. Analyze department averages, subject pass percentages and marks distribution with Python.

## Grade Rules Used in This Demonstration
O >=90, A+ >=80, A >=70, B+ >=60, B >=50, C >=40, RA <40. PASS requires total >= subject pass mark (default 40). Confirm and adjust these rules to match your institution's regulations.

## Prerequisites
- JDK 17
- Maven 3.8+
- MySQL Server 8+
- Python 3.10+

## Database Setup
Option A: let Spring Boot create/update tables. Configure `DB_USER` and `DB_PASSWORD` environment variables before running.

Option B: execute `database/schema.sql` in MySQL Workbench or CLI:
```bash
mysql -u root -p < database/schema.sql
```
The SQL script creates the database and sample data. The Java app uses `student_performance` by default.

## Run the Java Web Application
From the project root:
```bash
# Linux/macOS
export DB_USER=root
export DB_PASSWORD=your_mysql_password
./mvnw spring-boot:run
```
If Maven Wrapper is not present, use:
```bash
mvn spring-boot:run
```
Windows PowerShell:
```powershell
$env:DB_USER="root"
$env:DB_PASSWORD="your_mysql_password"
mvn spring-boot:run
```
Open http://localhost:8080

### Workflow
1. Add students.
2. Add subjects (maximum marks and pass marks).
3. Select a student and subject, enter internal and exam marks, and save.
4. The dashboard shows calculated results. Select View for an individual report.

### JSON endpoints
- `GET http://localhost:8080/api/students`
- `GET http://localhost:8080/api/subjects`
- `GET http://localhost:8080/api/results`

## Run Python Analysis
```bash
cd python
python -m venv .venv
# Windows: .venv\\Scripts\\activate
# Linux/macOS: source .venv/bin/activate
pip install -r requirements.txt
```
Set the same MySQL credentials:
```bash
# Linux/macOS
export DB_USER=root
export DB_PASSWORD=your_mysql_password
python analyze_performance.py
```
Windows PowerShell:
```powershell
$env:DB_USER="root"
$env:DB_PASSWORD="your_mysql_password"
python analyze_performance.py
```
Charts and `cleaned_performance.csv` are saved in `python/output/`.

## Project Structure
```text
scenario8_student_performance/
├── src/main/java/...             # Java application
├── src/main/resources/templates/ # Web pages
├── database/                     # MySQL schema and queries
├── python/                       # Data retrieval, preprocessing, charts
└── docs/                         # SRS, diagrams, testing
```

## Subject-wise Submission Mapping
- Java: Java source, business logic, input handling, web interface.
- DBMS: schema, ERD, relational model, SQL, normalization.
- Software Engineering: SRS, use cases, class/architecture diagrams, testing.
- FDS: Python database retrieval, preprocessing and visualizations.

## Limitations / Before Final Submission
This is an educational starter implementation, not a production student information system. Add login/role-based access, edit/delete screens, audit logs, CSRF/security configuration and deployment secrets before real institutional use. The supplied tests and test-case expected results must be executed and actual results recorded on your machine.
