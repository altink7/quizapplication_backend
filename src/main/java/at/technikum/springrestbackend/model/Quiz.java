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

    @ManyToMany
    @JoinTable(
            name = "quiz_participants",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    private List<User> participants;

    @Enumerated
    @Column(name = "category", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Question> questions;

    @ManyToMany
    @JoinTable(
            name = "quiz_statistics",
            joinColumns = @JoinColumn(name = "quiz_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @ToString.Exclude
    private List<UserStatistic> statistics;

    @Column(name = "start_date") //TODO: setzen der default werte in service
    private LocalDate startDate;

    @Column(name = "duration")
    private int duration;
}
