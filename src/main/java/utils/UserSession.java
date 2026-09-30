package utils;

public class UserSession {
    private final User user;
    private final String token;

    public UserSession(User user, String token) {
        this.user  = user;
        this.token = token;
    }

    public User   getUser()  { return user; }
    public String getToken() { return token; }
}
