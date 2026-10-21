package com.bootcamp.smarthome.exception;

/**
 * Thrown when a command is sent to a device that is offline.
 *
 * @author Karyna Litvinova
 */
public class DeviceOfflineException extends HomeAutomationException {

    private static final long serialVersionUID = 1L;

    public DeviceOfflineException(String deviceId) {
        super("Device '" + deviceId + "' is offline");
    }
}