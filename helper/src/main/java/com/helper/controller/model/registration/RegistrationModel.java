package com.helper.controller.model.registration;

import java.util.Objects;

public class RegistrationModel {

    private long id;

    private String message;

    public RegistrationModel(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistrationModel model = (RegistrationModel) o;
        return id == model.id &&
                Objects.equals(message, model.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message);
    }

    @Override
    public String toString() {
        return "RegistrationModel{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
