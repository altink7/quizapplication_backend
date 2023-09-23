package at.technikum.springrestbackend.model;


import java.io.Serializable;

public enum Role implements Serializable {
    USER(0),
    ADMIN(1);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
