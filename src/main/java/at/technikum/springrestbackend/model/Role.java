package at.technikum.springrestbackend.model;


import java.io.Serializable;

public enum Role implements Serializable {
    USER(0),
    ADMIN(1);

    Role(int i) {
    }
}
