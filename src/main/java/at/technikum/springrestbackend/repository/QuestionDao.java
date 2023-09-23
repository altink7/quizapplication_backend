package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionDao extends JpaRepository<Question, Long> {
    Optional<Question> findByCategory(Category category);
}
