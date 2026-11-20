package edu.velalar.performance.repository;
import edu.velalar.performance.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SubjectRepository extends JpaRepository<Subject,String> {}
