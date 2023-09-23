package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.Category;
import at.technikum.springrestbackend.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Long> {
    Quiz findByCategory(Category category);
}
