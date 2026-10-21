package com.bootcamp.smarthome.exception;

/**
 * Unchecked: thrown when no device with the given ID is registered.
 *
 * @author Karyna Litvinova
 */
public class DeviceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DeviceNotFoundException(String deviceId) {
        super("Device not found: " + deviceId);
    }
}