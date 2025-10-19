package service;

import dao.UserDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;
import util.HashUtil;
import util.ValidationUtil;

/**
 * Service class for authentication logic.
 */
public class AuthService {
    private static final Logger LOGGER = Logger.getLogger(AuthService.class.getName());
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
            LOGGER.log(Level.SEVERE, "Registration failed", e);
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
            LOGGER.log(Level.SEVERE, "Login failed", e);
        }
        return null;
    }
}
