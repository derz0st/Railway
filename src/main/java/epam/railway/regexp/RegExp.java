package epam.railway.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by denis on 06.01.17.
 */
public class RegExp {

    private static final Pattern emailPattern = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    private static Matcher matcher;

    public static boolean validateEmail(final String email) {
        matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

}