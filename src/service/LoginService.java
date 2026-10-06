package service;
import model.User;

public class LoginService {
    private User admin;

    public LoginService() {
        admin = new User("admin", "admin123");
    }

    public boolean login(String username, String password) {

        return admin.getUsername().equals(username)
                && admin.getPassword().equals(password);
    }
}
