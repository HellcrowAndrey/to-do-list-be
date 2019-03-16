package com.todo.app.user;

import com.todo.app.controller.model.user.UserModel;
import com.todo.app.dao.model.UserDaoModel;
import com.todo.app.password.IPasswords;
import com.todo.app.token.IToken;

public class CreateUser {

    private UserDaoModel daoModel;

    private CreateUser(UserBuilder userBuilder) {
        this.daoModel = userBuilder.daoModel;
    }

    public static class UserBuilder {

        private UserDaoModel daoModel;

        private IPasswords passwords;

        private IToken genToken;

        private byte[] salt;

        private byte[] hash;

        private String token;

        private UserModel user;

        public UserBuilder init(IPasswords passwords, IToken genToken) {
            this.passwords = passwords;
            this.genToken = genToken;
            return this;
        }

        public UserBuilder createParams(UserModel user) {
            this.user = user;
            this.salt = this.passwords.getSalt64();
            this.hash = this.passwords.hash(user.getPassword(), salt);
            this.token = this.genToken.nextToken();
            return this;
        }

        public UserBuilder createUser() {
            this.daoModel = new UserDaoModel();
            this.daoModel.setLogin(this.user.getLogin());
            this.daoModel.setEmail(this.user.getEmail());
            this.daoModel.setHash(this.hash);
            this.daoModel.setSalt(this.salt);
            this.daoModel.setToken(this.token);
            this.daoModel.setEnable(true);
            return this;
        }

        public CreateUser build() {
            return new CreateUser(this);
        }

    }

    public UserDaoModel getDaoModel() {
        return daoModel;
    }
}
