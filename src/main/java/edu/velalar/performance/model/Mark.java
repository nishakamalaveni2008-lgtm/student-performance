package edu.velalar.performance.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity @Table(name="marks", uniqueConstraints=@UniqueConstraint(columnNames={"student_id","subject_id"}))
public class Mark {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) @JoinColumn(name="student_id") private Student student;
 @ManyToOne(optional=false) @JoinColumn(name="subject_id") private Subject subject;
 @Min(0) private int internalMarks;
 @Min(0) private int examMarks;
 private int totalMarks;
 private double average;
 private String grade;
 private String status;
 public Mark() {}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public Student getStudent(){return student;} public void setStudent(Student v){student=v;}
 public Subject getSubject(){return subject;} public void setSubject(Subject v){subject=v;}
 public int getInternalMarks(){return internalMarks;} public void setInternalMarks(int v){internalMarks=v;}
 public int getExamMarks(){return examMarks;} public void setExamMarks(int v){examMarks=v;}
 public int getTotalMarks(){return totalMarks;} public void setTotalMarks(int v){totalMarks=v;}
 public double getAverage(){return average;} public void setAverage(double v){average=v;}
 public String getGrade(){return grade;} public void setGrade(String v){grade=v;}
 public String getStatus(){return status;} public void setStatus(String v){status=v;}
}
