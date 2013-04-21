package com.dvlcube.util;

import java.util.List;

/**
 *
 * @author Wonka
 */
public class CubeString {

    private StringBuilder builder = new StringBuilder();

    public CubeString() {
    }

    /**
     * Constructs a String from n Strings and stores it. Use getString() to retrieve it.
     * @param parts The Strings.
     */
    public CubeString(Object... parts) {
        for (Object part : parts) {
            builder.append(String.valueOf(part));
        }
    }

    /**
     * Appends the specified Strings to this builder.
     * @param parts The Strings.
     */
    public void append(Object... parts) {
        for (Object part : parts) {
            builder.append(String.valueOf(part));
        }
    }

    /**
     * Inserts the specified String at the specified index.
     * @param offset The builder index;
     * @param part The String.
     */
    public void insert(int offset, Object part) {
        builder.insert(offset, part);
    }

    @Override
    public String toString() {
        return builder.toString();
    }

    /**
     * Constructs a String from n Strings. This method should be used whenever the String needs to be
     * created and returned in the same line.
     * @param parts The Strings.
     * @return The built String.
     */
    public static String build(Object... parts) {
        StringBuilder builder = new StringBuilder();
        for (Object part : parts) {
            builder.append(String.valueOf(part));
        }
        return builder.toString();
    }

    /**
     * Creates a CSV String from a List of Objects.
     * @param list The List of Objects.
     * @return A CSV String.
     */
    public static String asCSV(List list) {
        CubeString builder = new CubeString();
        for (Object item : list) {
            builder.append(";", item);
        }
        return builder.toString().replaceFirst(";", "");
    }
}
