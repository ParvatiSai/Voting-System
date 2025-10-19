package service;

import dao.UserDAO;
import model.User;
import util.HashUtil;
import util.ValidationUtil;

/**
 * Service class for authentication logic.
 */
public class AuthService {
    private UserDAO userDAO = new UserDAO();

    /**
     * Registers a new user.
     */
    public boolean register(User user) {
        if (!ValidationUtil.isValidEmail(user.getEmail()) || !ValidationUtil.isValidPassword(user.getPassword())) {
            return false;
        }
        try {
            userDAO.createUser(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Logs in a user.
     */
    public User login(String email, String password) {
        try {
            User user = userDAO.getUserByEmail(email);
            if (user != null && HashUtil.hash(password).equals(user.getPassword())) {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
