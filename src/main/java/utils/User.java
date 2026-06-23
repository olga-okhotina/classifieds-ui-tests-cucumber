package utils;

import java.util.UUID;

public class User {
    private final String email;
    private final String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static User random() {
        String suffix = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return new User(
                "test_" + suffix + "@test.com",
                "Pass_" + suffix + "!1"
        );
    }

    public String getEmail()    { return email; }
    public String getPassword() { return password; }
}
