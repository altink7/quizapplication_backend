package at.technikum.springrestbackend.model;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "answer")
@AttributeOverride(name = "id", column = @Column(name = "answer_id"))
public class Answer extends AbstractEntity {

    @Column(name = "answer", nullable = false)
    private String answer;
}
