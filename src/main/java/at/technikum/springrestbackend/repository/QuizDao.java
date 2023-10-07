package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Long> {
    Optional<List<Quiz>> findByCategory(Category category);
}
