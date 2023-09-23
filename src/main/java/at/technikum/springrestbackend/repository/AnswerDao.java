package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerDao extends JpaRepository<Answer, Long> {
}
