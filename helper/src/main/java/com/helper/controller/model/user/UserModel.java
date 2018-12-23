package com.helper.controller.model.user;

import java.util.Objects;

public class UserModel {
    private long id;
    private String user;
    private String email;
    private String password;

    public UserModel(final String user, final String email, final String password) {
        this.user = user;
        this.email = email;
        this.password = password;
    }

    public UserModel(final long id, final String user,
                     final String email, final String password) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final UserModel that = (UserModel) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, email, password);
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "user='" + user + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
