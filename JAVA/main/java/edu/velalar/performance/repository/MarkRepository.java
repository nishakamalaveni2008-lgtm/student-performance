package edu.velalar.performance.repository;
import edu.velalar.performance.model.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MarkRepository extends JpaRepository<Mark,Long> {
 List<Mark> findByStudentStudentId(String studentId);
 List<Mark> findBySubjectSubjectId(String subjectId);
}
