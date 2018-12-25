package com.helper.controller.model.user;

import java.util.Objects;

public class UserModel {

    private long id;

    private String login;

    private String email;

    private String password;

    public UserModel(final String login, final String email, final String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public UserModel(final long id, final String login,
                     final String email, final String password) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
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
        return Objects.equals(login, that.login) &&
                Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, email, password);
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
