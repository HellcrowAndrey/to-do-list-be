package com.todo.app.dao.model;


import java.util.Objects;

public class UserDaoModel {

    private long idUser;

    private String login;

    private String email;

    private String passwordHash;

    private String salt;

    private String token;

    private boolean enable;

    /**
     * This is default constructor this class
     */
    public UserDaoModel() {
        // Use if need instance new object in loop
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDaoModel that = (UserDaoModel) o;
        return idUser == that.idUser &&
                enable == that.enable &&
                Objects.equals(login, that.login) &&
                Objects.equals(email, that.email) &&
                Objects.equals(passwordHash, that.passwordHash) &&
                Objects.equals(salt, that.salt) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, login, email, passwordHash, salt, token, enable);
    }

    @Override
    public String toString() {
        return "UserDaoModel{" +
                "idUser=" + idUser +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", salt='" + salt + '\'' +
                ", token='" + token + '\'' +
                ", enable=" + enable +
                '}';
    }
}
