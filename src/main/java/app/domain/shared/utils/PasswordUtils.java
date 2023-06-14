package app.domain.shared.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Class to generate random password for users of the system and send them to a file
 * @author João Wolff
 */
public class PasswordUtils {

    /**
     * Random for generating password
     */
    private static Random rnd = new Random();

    /**
     * Method for generating a random password
     * @return Generated Password
     */
    public static String generateRandomPassword(){
        StringBuilder salt = new StringBuilder();
        String saltChars = "abcdefghijklmnopkrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * saltChars.length());
            salt.append(saltChars.charAt(index));
        }
        return salt.toString();
    }

    /**
     * Method for writing a an email and a password into a file
     * @param generatedPassword Password to be written
     * @param email Email to be written
     * @return True if successfully writes and false otherwise
     * @throws IOException If cannot write into the file
     */
    public static boolean writePassword(String generatedPassword, String email) throws IOException {
        File file = new File("emailAndSMSMessages.txt");
        if(!file.exists())
            file.createNewFile();
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))){
            bw.write(String.format("%n%nEmployee email: %s%nEmplooye Password: %s", email, generatedPassword));
            return true;
        }
    }
}
