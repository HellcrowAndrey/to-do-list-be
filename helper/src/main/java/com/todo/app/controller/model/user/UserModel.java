package com.todo.app.controller.model.user;

import com.todo.app.validation.FieldMatch;

import javax.validation.constraints.AssertTrue;
import java.util.Objects;

@FieldMatch.List({
        @FieldMatch(first = "email", second = "password", message = "First 1")
})
public class UserModel {

    private long id;

    private String login;

    private String email;

    private String password;

    private String role = "user";

    private String hashLoginPass;

    private String hashEmailPass;

    @AssertTrue
    private Boolean terms;

    public UserModel(long id, String login, String email) {
        this.id = id;
        this.login = login;
        this.email = email;
    }

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

    public UserModel(long id, String login, String email,
                     String hashLoginPass, String hashEmailPass) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.password = password;
        this.hashLoginPass = hashLoginPass;
        this.hashEmailPass = hashEmailPass;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getHashEmailPass() {
        return hashEmailPass;
    }

    public void setHashEmailPass(String hashEmailPass) {
        this.hashEmailPass = hashEmailPass;
    }

    public String getHashLoginPass() {
        return hashLoginPass;
    }

    public void setHashLoginPass(String hashLoginPass) {
        this.hashLoginPass = hashLoginPass;
    }

    public Boolean getTerms() {
        return terms;
    }

    public void setTerms(Boolean terms) {
        this.terms = terms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel model = (UserModel) o;
        return id == model.id &&
                Objects.equals(login, model.login) &&
                Objects.equals(email, model.email) &&
                Objects.equals(password, model.password) &&
                Objects.equals(role, model.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, email, password, role);
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", hashLoginPass='" + hashLoginPass + '\'' +
                ", hashEmailPass='" + hashEmailPass + '\'' +
                '}';
    }
}
