package at.technikum.springrestbackend.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "user_statistic")
@AttributeOverride(name = "id", column = @Column(name = "userStatistic_id"))
@EntityListeners(AuditingEntityListener.class)
public class UserStatistic extends AbstractEntity {

    @Column(name = "points")
    private int points;

    @Column(name = "start_time")
    private LocalDateTime start;

    @Column(name = "duration")
    private Duration duration;
}
