package com.helper.controller.model;

import java.util.Objects;

public class ResponseModel<T> {

    private long id;

    private T resoinse;

    public ResponseModel(long id, T resoinse) {
        this.id = id;
        this.resoinse = resoinse;
    }

    public long getId() {
        return id;
    }

    public T getResoinse() {
        return resoinse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseModel<?> that = (ResponseModel<?>) o;
        return id == that.id &&
                Objects.equals(resoinse, that.resoinse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, resoinse);
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "id=" + id +
                ", resoinse=" + resoinse +
                '}';
    }
}
