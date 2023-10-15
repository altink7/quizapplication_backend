package at.technikum.springrestbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "answer_option")
@AttributeOverride(name = "id", column = @Column(name = "answer_id"))
@JsonIgnoreProperties({"question"})
public class AnswerOption extends AbstractEntity {

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    @Column(name = "correct", nullable = false)
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}
