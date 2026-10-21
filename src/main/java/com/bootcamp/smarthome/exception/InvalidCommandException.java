package com.bootcamp.smarthome.exception;

/**
 * Thrown when a command is missing, unknown or cannot be executed.
 *
 * @author Karyna Litvinova
 */
public class InvalidCommandException extends HomeAutomationException {

    private static final long serialVersionUID = 1L;

    public InvalidCommandException(String message) {
        super(message);
    }

    public InvalidCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}