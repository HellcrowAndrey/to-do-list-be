package com.todo.app.controller.model.response;

import java.util.Objects;

public class ResponseModel<T> {

    private final long idMessage;

    private final T response;

    public ResponseModel(final long idMessage, final T response) {
        this.idMessage = idMessage;
        this.response = response;
    }

    public long getIdMessage() {
        return idMessage;
    }

    public T getResponse() {
        return response;
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final ResponseModel<?> that = (ResponseModel<?>) object;
        return idMessage == that.idMessage &&
                Objects.equals(response, that.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMessage, response);
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "idMessage=" + idMessage +
                ", response=" + response +
                '}';
    }
}
