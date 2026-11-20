package edu.velalar.performance.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity @Table(name="subjects")
public class Subject {
 @Id @Column(length=20) private String subjectId;
 @NotBlank private String subjectName;
 @Min(1) private int maxMarks=100;
 @Min(0) private int passMarks=40;
 public Subject() {}
 public Subject(String id,String name,int max,int pass){subjectId=id;subjectName=name;maxMarks=max;passMarks=pass;}
 public String getSubjectId(){return subjectId;} public void setSubjectId(String v){subjectId=v;}
 public String getSubjectName(){return subjectName;} public void setSubjectName(String v){subjectName=v;}
 public int getMaxMarks(){return maxMarks;} public void setMaxMarks(int v){maxMarks=v;}
 public int getPassMarks(){return passMarks;} public void setPassMarks(int v){passMarks=v;}
}
