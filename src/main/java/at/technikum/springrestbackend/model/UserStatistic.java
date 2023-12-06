package at.technikum.springrestbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "user_statistic")
@AttributeOverride(name = "id", column = @Column(name = "userStatistic_id"))
@EntityListeners(AuditingEntityListener.class)
public class UserStatistic extends AbstractEntity {

    @Column(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "userStatistic", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Quiz> quizList;

    @Column(name = "points", nullable = false)
    private int points;

    public List<Quiz> getQuizList() {
        if (quizList == null) {
            return new ArrayList<>();
        }
        return quizList;
    }
}
