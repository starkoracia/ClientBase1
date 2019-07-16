package application.util;

import application.sql.entitys.authenticate.User;

public class UserSetter {

    static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserSetter.user = user;
    }
}
