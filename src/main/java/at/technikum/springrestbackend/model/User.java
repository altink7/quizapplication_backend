package at.technikum.springrestbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@ToString
@Entity(name = "user")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
@EntityListeners(AuditingEntityListener.class)
public class User extends AbstractEntity {

    @OneToMany(mappedBy = "creator")
    @ToString.Exclude
    private List<Quiz> createdQuizzes;

    @Enumerated
    @Column(name = "salutation", nullable = false)
    private Gender salutation;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "country")
    private String country;

    @Enumerated
    @Column(name = "role", nullable = false)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userStatistic_id")
    private UserStatistic userStatistic;

}
