package edu.velalar.performance.service;
import edu.velalar.performance.model.*;
import edu.velalar.performance.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class ResultService {
 private final StudentRepository students; private final SubjectRepository subjects; private final MarkRepository marks;
 public ResultService(StudentRepository s,SubjectRepository sub,MarkRepository m){students=s;subjects=sub;marks=m;}
 @Transactional public Mark saveMark(String studentId,String subjectId,int internal,int exam){
  Student student=students.findById(studentId).orElseThrow(()->new IllegalArgumentException("Student not found"));
  Subject subject=subjects.findById(subjectId).orElseThrow(()->new IllegalArgumentException("Subject not found"));
  if(internal<0 || exam<0 || internal+exam>subject.getMaxMarks()) throw new IllegalArgumentException("Marks must be between 0 and "+subject.getMaxMarks());
  Mark mark=marks.findByStudentStudentId(studentId).stream().filter(x->x.getSubject().getSubjectId().equals(subjectId)).findFirst().orElse(new Mark());
  mark.setStudent(student); mark.setSubject(subject); mark.setInternalMarks(internal); mark.setExamMarks(exam);
  int total=internal+exam; double avg=(total*100.0)/subject.getMaxMarks();
  mark.setTotalMarks(total); mark.setAverage(Math.round(avg*100.0)/100.0);
  mark.setStatus(total>=subject.getPassMarks()?"PASS":"FAIL");
  mark.setGrade(avg>=90?"O":avg>=80?"A+":avg>=70?"A":avg>=60?"B+":avg>=50?"B":avg>=40?"C":"RA");
  return marks.save(mark);
 }
 public List<Mark> allMarks(){return marks.findAll();}
 public List<Mark> studentMarks(String id){return marks.findByStudentStudentId(id);}
 public List<Student> allStudents(){return students.findAll();}
 public List<Subject> allSubjects(){return subjects.findAll();}
}
