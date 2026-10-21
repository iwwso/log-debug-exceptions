package com.bootcamp.smarthome.exception;

/**
 * Base checked exception of the smart home application.
 *
 * @author Karyna Litvinova
 */
public class HomeAutomationException extends Exception {

    private static final long serialVersionUID = 1L;

    public HomeAutomationException(String message) {
        super(message);
    }

    public HomeAutomationException(String message, Throwable cause) {
        super(message, cause);
    }
}