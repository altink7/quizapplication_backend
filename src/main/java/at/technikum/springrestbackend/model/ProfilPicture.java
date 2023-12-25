package at.technikum.springrestbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProfilPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private long id;

    @OneToOne(mappedBy = "profilPicture")
    private User user;

    private String externalId;

    private String name;

    private String contentType;

}
