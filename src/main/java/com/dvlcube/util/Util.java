package com.dvlcube.util;

import com.dvlcube.security.PasswordService;
import com.dvlcube.security.SystemUnavailableException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wonka
 */
public class Util {

    /**
     * Checks if a String is empty by calling trim() and isEmpty().
     * @param string The String to be checked.
     * @return Whether the String if empty or not.
     */
    public static boolean isEmpty(String string) {
        if (string == null) {
            return true;
        } else {
            if (string.trim().isEmpty()) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Checks if any one of the elements of a String array are empty.
     * @param fields The String array containing all fields to be checked.
     * @return Returns true if any of the elements is empty.
     */
    public static boolean areEmpty(String[] fields) {
        for (String field : fields) {
            if (isEmpty(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a <code>String</code> can be a valid E-Mail Address.
     * @param address The String.
     * @return A boolean.
     */
    public static boolean isValidMail(String address) {
        int atIndex = address.indexOf("@");
        int dotIndex = address.lastIndexOf(".");
        if (atIndex < 1 || dotIndex - atIndex < 2) {
            return false;
        } else {
            return true;
        }
    }

    public static int getRandomNumbers(int amount) {
        return (int) (Math.random() * amount);
    }

    public static String getRandomName() {
        return new NameFactory().getName();
    }

    public static String getRandomNames(int names) {
        return new NameFactory(names).getCompleteName();
    }

    /**
     * Transforms the initial letter of a String into a capital letter.
     * @param string
     * @return
     */
    public static String initCap(String string) {
        return new NameFactory().initCap(string);
    }

    /**
     * Redirects an unlogged User to the Login page, when the page in question requires authentication.
     * After login, the User is redirected to the page he tried to access.
     * @param request The request object;
     * @param response The response object.
     * @throws IOException if an I/O error occurs.
     */
    public static void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String lastLocation = setLastURL(request.getRequestURI(), request.getQueryString());
        request.getSession().setAttribute("lastLocation", lastLocation);
        response.sendRedirect("./Authenticate");
    }

    /**
     * Reconstructs de URL issued by the User before being asked to login, so that a redirect can be made after login.
     * @param requestURI The request URI;
     * @param queryString The request query String.
     * @return A URL ready to be used in a response.sendRedirect() method.
     */
    public static String setLastURL(String requestURI, String queryString) {
        StringBuilder builder = new StringBuilder();
        String url = "./" + requestURI.split("/")[2];
        builder.append(url);
        if (queryString != null) {
            builder.append("?").append(queryString);
        }
        return builder.toString();
    }

    /**
     * Creates a new ArrayList from a String Array.
     * @param stringArray The String Array.
     * @return An ArrayList.
     */
    public static List newList(String[] stringArray) {
        try {
            List newList = new ArrayList();
            newList.addAll(Arrays.asList(stringArray));
            return newList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converts a String Array into a Long List.
     * @param values The String values.
     * @return A Long List, <code>null</code> if an exception occurs.
     */
    public static List<Long> toLongList(String[] values) {
        try {
            List<Long> longList = new ArrayList<Long>();
            for (String value : values) {
                longList.add(Long.parseLong(value));
            }
            return longList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Transforms a list of values separated by semicolons in a String into an int Array.
     * @param values A String of values separated by semicolons.
     * @return An int Array, <code>null</code> if an exception occurs.
     */
    public static int[] toIntArray(String values) {
        if (values != null) {
            try {
                String[] array = values.split(",");
                int[] intArray = new int[array.length];
                for (int i = 0; i < array.length; i++) {
                    intArray[i] = Integer.parseInt(array[i]);
                }
                return intArray;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Sum all items in an int Array.
     * @param array The int Array.
     * @return The sum of all items.
     */
    public static int sum(int[] array) {
        if (array != null) {
            int sum = 0;
            for (int item : array) {
                sum += item;
            }
            return sum;
        }
        return 0;
    }

    /**
     * Formats a Date type to a String, e.g.:
     * "Monday, April 20, 2009 3:25 AM"
     * @param date The Date you want to modify.
     * @return A String representation of a Date Object.
     */
    public static String formatDate(Date date) {
        Locale locale = Locale.US;
        String newDate = DateFormat.getDateTimeInstance(
                DateFormat.FULL,
                DateFormat.SHORT,
                locale).format(date == null ? new Date() : date);

        return newDate;
    }

    /**
     * Gets a String, encrypts it and then encode for URL.
     * @param string The String to encrypt and encode.
     * @return An encoded encrypted string.
     * @throws SystemUnavailableException
     * @throws UnsupportedEncodingException
     */
    public static String encryptAndEncode(String string)
            throws SystemUnavailableException, UnsupportedEncodingException {
        return URLEncoder.encode(PasswordService.getInstance().encrypt(string), "UTF-8");
    }

    /**
     * Converts the first letter in a String to lower case.
     * @param string The String.
     * @return The converted String.
     */
    public static String initLower(String string) {
        String mod = Character.toLowerCase(string.charAt(0))
                + string.substring(1);
        return mod;
    }

    /**
     * @param objects The object array.
     * @return an object array as a readable String.
     */
    public static String toString(Object[] objects) {
        StringBuilder builder = new StringBuilder();
        for (Object object : objects) {
            builder.append(", ".concat(object.toString()));
        }
        return builder.toString().replaceFirst(", ", "");
    }

    /**
     * Safely splits a CSV string.
     * Note that this method does not add duplicate elements, and all elements are trimmed.
     * @param csv The CSV string.
     * @return an array of Strings.
     */
    public static List split(String csv) {
        if (csv != null) {
            List values = new ArrayList();
            csv = csv.replace(";", ",");
            for (String item : csv.split(",")) {
                if (!values.contains(item.trim())) {
                    values.add(item.trim());
                }
            }
            return values;
        }
        return null;
    }

    /**
     * @param parameter The String representation of a number.
     * @return The long value represented by the parameter string; 0 if a number cannot be extracted from the String.
     */
    public static long getLong(String parameter) {
        if (parameter == null) {
            return 0;
        }
        try {
            return new Long(parameter);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Creates random names.
     */
    public static class NameFactory {

        /**
         * Creates a random name.
         */
        public NameFactory() {
            setNameLength();
            setRandomName();
        }

        /**
         * Creates the specified number of random names.
         * @param names The desired number of random names.
         */
        public NameFactory(int names) {
            for (int i = 0; i < names; i++) {
                setNameLength();
                setRandomName();
            }
        }
        public static final int CONSONANTS = 0;
        public static final int VOWELS = 1;
        private String name;
        private int nameLength;
        private final String[] letters = {"bcdfghjklmnprstvwyz", "aeiou"};
        private final String[] consonantBridges = {"bl;br", "cc;ck;ch;cl;cr", "dl;dh;dr;dw", "fl;fr", "gh;gl;gr", "hl;hr", "kl;kr;kn;ks;ky", "ll;lh;lly", "pl;pr;ph", "qu", "rr;rh", "sl;sp;ss;sw;st;sy", "tl;tr;ts;th;tw", "ul", "vl", "wr;wh"};
        private final String[] initialBridges = {"bj", "mc"};
        private final String[] neverInitials = {"tt", "rr"};
        private StringBuilder names = new StringBuilder();

        /**
         * Gets the last created name.
         * @return The last name generated by this instance.
         */
        public String getName() {
            return name;
        }

        public String getCompleteName() {
            return names.toString().replaceFirst(" ", "");
        }

        public int getNameLength() {
            return nameLength;
        }

        public String[] getLetters() {
            return letters;
        }

        public String[] getConsonantBridges() {
            return consonantBridges;
        }

        public String getConsonants() {
            return letters[0];
        }

        public int getConsonantsLength() {
            return letters[0].length();
        }

        public String getVowels() {
            return letters[1];
        }

        public int getVowelsLength() {
            return letters[1].length();
        }

        public int getLength(int kind) {
            return letters[kind].length();
        }

        public final void setNameLength() {
            this.nameLength = (int) (Math.random() * 4) + 3;
            if (nameLength % 2 == 1) {
                nameLength++;
            }
        }

        /**
         * Builds a random name with an initial upper case letter.
         */
        public final void setRandomName() {
            StringBuilder nameBuilder = new StringBuilder();
            for (int i = getRandomNumbers(2); i < getNameLength(); i++) {
                if (i % 2 == 0) {
                    if (getRandomNumbers(10) < 4) {
                        nameBuilder.append(getBridge());
                    } else {
                        nameBuilder.append(newConsonant());
                    }
                } else {
                    nameBuilder.append(newVowel());
                }
            }
            this.name = initCap(nameBuilder.toString());
            names.append(" ").append(name);
        }

        /**
         * Builds a random name with an initial upper case letter.
         * @return
         */
        public void setRandomName2() {
            StringBuilder nameBuilder = new StringBuilder();

            for (int i = 0; i < getNameLength(); i++) {
                nameBuilder.append(letters[i % 2].charAt((int) (Math.random() * getLength(i % 2))));
            }
            this.name = initCap(nameBuilder.toString());
        }

        /* Helpers */
        public String getBridge() {
            String randomBridge = consonantBridges[(int) (Math.random() * consonantBridges.length)];
            String[] bridgeItems = randomBridge.split(";");
            int randomItem = (int) (Math.random() * bridgeItems.length);
            String bridge = bridgeItems[randomItem];
            return bridge;
        }

        public String initCap(String string) {
            String initialLetter = string.substring(0, 1);
            String newString = initialLetter.toUpperCase() + string.substring(1);
            return newString;
        }

        public char newConsonant() {
            return getConsonants().charAt((int) (Math.random() * getConsonantsLength()));
        }

        public char newVowel() {
            return getVowels().charAt((int) (Math.random() * getVowelsLength()));
        }
    }
}
