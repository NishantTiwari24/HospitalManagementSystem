package com.hospital.utility;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash the password (to store in DB)
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    // Check password (during login)
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
