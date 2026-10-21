package com.bootcamp.smarthome.controller;

import com.bootcamp.smarthome.device.Device;
import com.bootcamp.smarthome.exception.DeviceNotFoundException;
import com.bootcamp.smarthome.exception.DeviceOfflineException;
import com.bootcamp.smarthome.exception.HomeAutomationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Central hub that manages all registered smart devices.
 *
 * Devices are stored in a fixed-size array (maximum {@value #MAX_DEVICES}).
 * The controller routes commands to devices by their ID.
 */
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public static final int MAX_DEVICES = 8;

    private final Device[] devices = new Device[MAX_DEVICES];
    private int deviceCount = 0;

    // -------------------------------------------------------------------------
    // Device registration
    // -------------------------------------------------------------------------

    /**
     * Registers a new device with the controller.
     * The controller accepts at most {@value #MAX_DEVICES} devices.
     *
     * @param device the device to register
     * @throws IllegalStateException if the device limit has been reached
     */
    public void addDevice(Device device) {
        if (deviceCount >= MAX_DEVICES) {
            throw new IllegalStateException(
                    "Cannot add device '" + device.getDeviceId() +
                            "': controller is at maximum capacity (" + MAX_DEVICES + ").");
        }
        devices[deviceCount] = device;
        deviceCount++;
        System.out.println("Device registered: " + device);
    }

    // -------------------------------------------------------------------------
    // Device lookup
    // -------------------------------------------------------------------------

    /**
     * Finds a registered device by its ID.
     *
     * Returns {@code null} when no matching device is found.
     */
    public Device findDevice(String deviceId) {
        for (int i = 0; i < deviceCount; i++) {
            if (devices[i] != null && devices[i].getDeviceId().equals(deviceId)) {
                return devices[i];
            }
        }
        return null;
    }

    /**
     * Same as {@link #findDevice(String)}, but never returns {@code null}.
     *
     * @throws DeviceNotFoundException if no device with this ID is registered
     */
    public Device getDevice(String deviceId) {
        Device device = findDevice(deviceId);
        if (device == null) {
            throw new DeviceNotFoundException(deviceId);
        }
        return device;
    }

    // -------------------------------------------------------------------------
    // Command routing
    // -------------------------------------------------------------------------

    /**
     * Parses {@code fullCommand}, resolves the target device, and delegates
     * execution to {@link Device#executeCommand(String)}.
     *
     * Full command format: {@code "DEVICE_ID ACTION [VALUE]"}
     * Example: {@code "LIGHT_01 SET_BRIGHTNESS 75"}
     *
     * @param fullCommand the full command string
     * @throws HomeAutomationException if the command fails (original exception is the cause)
     * @throws DeviceNotFoundException if the device ID is not registered
     */
    public void sendCommand(String fullCommand) throws HomeAutomationException {
        String deviceId = "unknown";
        try {
            deviceId = CommandParser.extractDeviceId(fullCommand);
            logger.debug("Command received for device '{}': '{}'", deviceId, fullCommand);
            String command = CommandParser.extractCommand(fullCommand);

            Device device = getDevice(deviceId);

            if (!device.isOnline()) {
                logger.warn("Device '{}' is offline - command '{}' skipped", deviceId, command);
                throw new DeviceOfflineException(deviceId);
            }

            device.executeCommand(command);
            logger.info("Command '{}' executed successfully on device '{}'", command, deviceId);
        } catch (HomeAutomationException e) {
            logger.error("Command '{}' failed for device '{}': {}", fullCommand, deviceId, e.getMessage());
            throw new HomeAutomationException(
                    "Command '" + fullCommand + "' failed for device '" + deviceId + "'", e);
        } catch (DeviceNotFoundException e) {
            logger.error("Command '{}' failed: {}", fullCommand, e.getMessage());
            throw e;
        } finally {
            System.out.println("Command processing ended for device [" + deviceId + "]");
        }
    }

    // -------------------------------------------------------------------------
    // Utility
    // -------------------------------------------------------------------------

    /** Prints the status of every registered device. */
    public void printAllDevices() {
        System.out.println("=== Registered Devices (" + deviceCount + "/" + MAX_DEVICES + ") ===");
        for (int i = 0; i < deviceCount; i++) {
            System.out.println("  " + devices[i]);
        }
    }

    public int getDeviceCount() {
        return deviceCount;
    }
}