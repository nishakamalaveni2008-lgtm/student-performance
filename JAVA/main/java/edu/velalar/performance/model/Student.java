package edu.velalar.performance.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity @Table(name="students")
public class Student {
 @Id @Column(length=20) private String studentId;
 @NotBlank private String name;
 @NotBlank private String department;
 @Min(1) @Max(5) private int yearOfStudy;
 public Student() {}
 public Student(String studentId,String name,String department,int yearOfStudy){this.studentId=studentId;this.name=name;this.department=department;this.yearOfStudy=yearOfStudy;}
 public String getStudentId(){return studentId;} public void setStudentId(String v){studentId=v;}
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getDepartment(){return department;} public void setDepartment(String v){department=v;}
 public int getYearOfStudy(){return yearOfStudy;} public void setYearOfStudy(int v){yearOfStudy=v;}
}
