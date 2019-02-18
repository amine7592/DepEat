package org.elis.depeat;

public class Utils {


    private static final int LEN_PASSWORD = 6;
    private static final int USERNAME_LENGTH = 4;



    public static boolean verifyEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > LEN_PASSWORD;
    }

    public static boolean isUsernameValid(String phone) {
        return phone.length() > USERNAME_LENGTH;
    }





}
