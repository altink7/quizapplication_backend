package at.technikum.springrestbackend.model;


import lombok.Getter;

import java.io.Serializable;

@Getter
public enum Role implements Serializable {
    USER(0),
    ADMIN(1);

    private final int value;

    Role(int value) {
        this.value = value;
    }
}
