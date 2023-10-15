package at.technikum.springrestbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "question")
@JsonIgnoreProperties({"quiz"})
@AttributeOverride(name = "id", column = @Column(name = "question_id"))
public class Question extends AbstractEntity {

    @Column(name = "question", nullable = false, unique = true)
    private String question;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<AnswerOption> answerOptions;

    @Column(name = "file")
    private File file;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;
}
