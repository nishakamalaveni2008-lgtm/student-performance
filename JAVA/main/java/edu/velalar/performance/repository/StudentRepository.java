package edu.velalar.performance.repository;
import edu.velalar.performance.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentRepository extends JpaRepository<Student,String> {}
