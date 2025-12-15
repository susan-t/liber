package liber;

import java.util.HashMap;
import java.util.Map;

public class UserStore {

    private static Map<String, User> users = new HashMap<>();

    public static User getOrCreateUser(String name) {
        return users.computeIfAbsent(name, User::new);
    }
}
