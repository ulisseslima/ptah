package com.dvlcube.security;

/**
 *
 * @author Wonka
 */
public class SystemUnavailableException extends Exception {
    SystemUnavailableException(String message) {
        System.err.println(message);
    }
}
