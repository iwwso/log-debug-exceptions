package com.bootcamp.smarthome.controller;

import com.bootcamp.smarthome.exception.InvalidCommandException;

/**
 * Parses full command strings into their component parts for use by
 * {@link HomeController}.
 *
 * Full command format: {@code "DEVICE_ID ACTION [VALUE]"}
 *
 * Examples:
 *   "LIGHT_01 SET_BRIGHTNESS 75"   → deviceId="LIGHT_01", action="SET_BRIGHTNESS", value="75"
 *   "THERMO_01 SET_TEMP 22.5"      → deviceId="THERMO_01", action="SET_TEMP", value="22.5"
 *   "LOCK_01 UNLOCK 1234"          → deviceId="LOCK_01", action="UNLOCK", value="1234"
 *   "LIGHT_01 TURN_ON"             → deviceId="LIGHT_01", action="TURN_ON", value=(none)
 */
public class CommandParser {

    /**
     * Extracts the device ID (first token) from the full command string.
     *
     * @param fullCommand the full command string
     * @return the device ID
     * @throws InvalidCommandException if the command is empty
     */
    public static String extractDeviceId(String fullCommand) throws InvalidCommandException {
        if (fullCommand == null || fullCommand.isBlank()) {
            throw new InvalidCommandException("Command cannot be empty");
        }
        return fullCommand.trim().split(" ")[0];
    }

    /**
     * Extracts the action and value from the full command string.
     *
     * The result is passed directly to {@link com.bootcamp.smarthome.device.Device#executeCommand(String)},
     * so it must contain both the action and the value when a value is present.
     *
     * Examples:
     *   "LIGHT_01 SET_BRIGHTNESS 75"  → should return "SET_BRIGHTNESS 75"
     *   "LIGHT_01 TURN_ON"            → should return "TURN_ON"
     */
    public static String extractCommand(String fullCommand) throws InvalidCommandException {
        // limit 2: split only at the first space, so "SET_BRIGHTNESS 75" stays together
        String[] parts = fullCommand.trim().split(" ", 2);
        if (parts.length < 2 || parts[1].isBlank()) {
            throw new InvalidCommandException("Command has no action: '" + fullCommand + "'");
        }
        return parts[1].trim();
    }
}