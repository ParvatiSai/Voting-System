package util;

/**
 * Utility class for input validation.
 */
public class ValidationUtil {

    /**
     * Validates email format.
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    /**
     * Validates password strength (at least 6 characters).
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}
