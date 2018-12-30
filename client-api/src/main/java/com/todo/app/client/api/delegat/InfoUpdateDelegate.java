package com.todo.app.client.api.delegat;

public class InfoUpdateDelegate {

    public static boolean isParams(String email, String password) {
        if (email == null || email.equals("") ||
                password == null || email.equals("")) {
            return false;
        }
        return true;
    }


}
