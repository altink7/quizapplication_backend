package at.technikum.springrestbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "quiz")
@AttributeOverride(name = "id", column = @Column(name = "quiz_id"))
@JsonIgnoreProperties({"userStatistic"})
public class Quiz extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id") //FIXME: nullable?
    private User creator;

    @OneToMany(mappedBy = "quiz")
    @ToString.Exclude
    private List<Participant> participants;

    @Enumerated
    @Column(name = "category", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Question> questions;

    @ManyToOne
    @JoinColumn(name = "user_statistic_id")
    private UserStatistic userStatistic;

    @Column(name = "start_date") //TODO: setzen der default werte in service
    private LocalDate startDate;

    @Column(name = "duration")
    private int duration;
}
