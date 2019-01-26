package com.todo.app.controller.model.user;

import java.util.Objects;

public class UserModel {

    private String login;

    private String email;

    private String password;

    /**
     * Default constructor
     */
    public UserModel() {
        // Use if need instance new object in loop
    }

    public UserModel(final String login, final String email, final String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        UserModel model = (UserModel) object;
        return Objects.equals(login, model.login) &&
                Objects.equals(email, model.email) &&
                Objects.equals(password, model.password);
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
