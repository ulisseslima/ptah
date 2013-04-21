package com.dvlcube.i18n;

import com.dvlcube.util.CubeString;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Wonka
 */
public class I18n {
    public static ResourceBundle getBundle(Locale locale){
        return ResourceBundle.getBundle("com.dvlcube.i18n.dvlcube", locale);
    }

    /**
     * Gets a string from the resource bundle.
     * @param locale The desired locale;
     * @param key The key to be searched.
     * @return A String message localized accordingly.
     */
    public static String getString(Locale locale, String key) {
        ResourceBundle bundle = getBundle(locale);
        String string = bundle.getString(key);
        return string;
    }

    /**
     * Gets all the Strings from an array of keys.
     * @param locale The desired Locale;
     * @param keys The desired keys;
     * @return The Strings are returned as a combination of the last part of the key plus the message, concatenated by an @ character: eg:
     * <ul><li>We request the key key.package.keyName;</li>
     * <li>This method will first get the keyName part of the key and then get the key value;</li>
     * <li>The returned String will look like "keyName@Message value;".</li>
     */
    public static String getStrings(Locale locale, String[] keys) {
        ResourceBundle bundle = getBundle(locale);
        CubeString builder = new CubeString();
        for(String key : keys){
            String name = key.split("\\.")[key.split("\\.").length - 1];
            builder.append(name, "@", bundle.getString(key), ";");
        }
        return builder.toString();
    }

    /**
     * Gets an array of localized messages.
     * @param locale The desired locale for the messages;
     * @param keys The message keys;
     * @return An array containing all the localized messages.
     */
    public static String[] getStringsAsArray(Locale locale, String[] keys){
        ResourceBundle bundle = getBundle(locale);
        StringBuilder builder = new StringBuilder();
        int LAST_INDEX = keys.length - 1;
        String[] messages;
        for(int i = 0; i < keys.length; i++){
            builder.append(bundle.getString(keys[i]));
            if(i < LAST_INDEX){
                builder.append(";");
            }
        }
        messages = builder.toString().split(";");
        return messages;
    }
}
