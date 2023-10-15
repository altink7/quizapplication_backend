package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDao extends JpaRepository<Question, Long> {
}
