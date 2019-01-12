package com.todo.app.controller.model.user;

import java.util.Objects;

public class UserModel {

    private long idUser;

    private String login;

    private String email;

    private String password;

    private static final String ROLE = "user";

    private String hashLoginPass;

    private String hashEmailPass;

    /**
     * Default constructor this class
     */
    public UserModel() {
        // Use for instance new object inside loops
    }

    public UserModel(final long idUser, final String login, final String email) {
        this.idUser = idUser;
        this.login = login;
        this.email = email;
    }

    public UserModel(final String login, final String email, final String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public UserModel(final long idUser, final String login,
                     final String email, final String password) {
        this.idUser = idUser;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public UserModel(final long idUser, final String login, final String email,
                     final String hashLoginPass, final String hashEmailPass) {
        this.idUser = idUser;
        this.login = login;
        this.email = email;
        this.hashLoginPass = hashLoginPass;
        this.hashEmailPass = hashEmailPass;
    }

    public void setIdUser(final long idUser) {
        this.idUser = idUser;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public long getIdUser() {
        return idUser;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return ROLE;
    }

    public String getHashEmailPass() {
        return hashEmailPass;
    }

    public void setHashEmailPass(final String hashEmailPass) {
        this.hashEmailPass = hashEmailPass;
    }

    public String getHashLoginPass() {
        return hashLoginPass;
    }

    public void setHashLoginPass(final String hashLoginPass) {
        this.hashLoginPass = hashLoginPass;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final UserModel model = (UserModel) object;
        return idUser == model.idUser &&
                Objects.equals(login, model.login) &&
                Objects.equals(email, model.email) &&
                Objects.equals(password, model.password) &&
                Objects.equals(ROLE, model.ROLE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, login, email, password, ROLE);
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + idUser +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + ROLE + '\'' +
                ", hashLoginPass='" + hashLoginPass + '\'' +
                ", hashEmailPass='" + hashEmailPass + '\'' +
                '}';
    }
}
