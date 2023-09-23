package at.technikum.springrestbackend.model;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum Gender implements Serializable {
    MALE(1),
    FEMALE(2),
    OTHER(3);

    private final int value;

    Gender(int value) {
        this.value = value;
    }
}
