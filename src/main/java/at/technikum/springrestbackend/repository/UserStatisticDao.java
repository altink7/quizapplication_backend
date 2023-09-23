package at.technikum.springrestbackend.repository;

import at.technikum.springrestbackend.model.UserStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserStatisticDao extends JpaRepository<UserStatistic, Long> {
    Optional<UserStatistic> findByUserId(Long userId);
}
