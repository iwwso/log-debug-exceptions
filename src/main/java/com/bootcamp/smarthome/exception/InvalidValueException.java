package com.bootcamp.smarthome.exception;

/**
 * Thrown when a value is outside its allowed range.
 *
 * @author Karyna Litvinova
 */
public class InvalidValueException extends HomeAutomationException {

    private static final long serialVersionUID = 1L;

    private final String field;
    private final Object value;
    private final String constraint;

    public InvalidValueException(String field, Object value, String constraint) {
        super("Invalid value for '" + field + "': " + value + " (" + constraint + ")");
        this.field = field;
        this.value = value;
        this.constraint = constraint;
    }

    public String getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public String getConstraint() {
        return constraint;
    }
}