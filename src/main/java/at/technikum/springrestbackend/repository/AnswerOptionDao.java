package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerOptionDao extends JpaRepository<AnswerOption, Long> {
}
