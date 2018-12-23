package com.helper.controller.model.auth;

import java.util.Objects;

public class AuthorizationModel {

    private long id;

    private boolean isAuth;

    public AuthorizationModel(final long id, final boolean isAuth) {
        this.id = id;
        this.isAuth = isAuth;
    }

    public long getId() {
        return id;
    }

    public boolean isAuth() {
        return isAuth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AuthorizationModel that = (AuthorizationModel) o;
        return id == that.id &&
                isAuth == that.isAuth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isAuth);
    }

    @Override
    public String toString() {
        return "AuthorizationModel{" +
                "id=" + id +
                ", isAuth=" + isAuth +
                '}';
    }
}
