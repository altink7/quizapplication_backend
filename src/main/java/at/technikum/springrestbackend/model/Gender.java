package at.technikum.springrestbackend.model;

import java.io.Serializable;

public enum Gender implements Serializable {
    MALE(1),
    FEMALE(2),
    OTHER(3);

    Gender(int i) {
    }
}
