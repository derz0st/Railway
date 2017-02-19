package epam.railway.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for inspection input data
 */
public class RegExp {

    private static final Pattern emailPattern = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    private static final Pattern namePattern = Pattern.compile("[a-zA-Z]+");

    private static final Pattern passwordPattern = Pattern.compile("[a-zA-Z0-9_!-?]+");

    private static Matcher matcher;


    public static boolean validateEmail(final String string) {
        matcher = emailPattern.matcher(string);
        return matcher.matches();
    }

    public static boolean validateNoSpecSymbols(final String string) {
        matcher = passwordPattern.matcher(string);
        return matcher.matches();
    }

    public static boolean validateOnlyLetters(final String string) {
        matcher = namePattern.matcher(string);
        return matcher.matches();
    }

}
