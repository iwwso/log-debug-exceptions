package com.bootcamp.smarthome.device;

import com.bootcamp.smarthome.exception.InvalidCommandException;
import com.bootcamp.smarthome.exception.InvalidValueException;

/**
 * A dimmable smart light bulb.
 *
 * Supports brightness control in the range [0, 100] where
 * 0 = fully off (dimmed) and 100 = fully bright.
 */
public class SmartLight extends Device {

    /** Brightness level. Valid range: 0–100 inclusive. */
    private int brightness;

    public SmartLight(String deviceId, String name, boolean isOnline) {
        super(deviceId, name, isOnline);
        this.brightness = 50;
    }

    //
    // Device-specific behaviour
    //

    /**
     * Sets the brightness of this light.
     *
     * Valid range: 0–100 inclusive.
     *
     * @throws InvalidValueException if level is outside [0, 100]
     */
    public void setBrightness(int level) throws InvalidValueException {
        if (level < 0 || level > 100) {
            throw new InvalidValueException("brightness", level, "must be between 0 and 100");
        }
        this.brightness = level;
        System.out.println(getName() + " brightness set to " + level + "%");
    }

    @Override
    public void executeCommand(String command) throws InvalidCommandException, InvalidValueException {
        if (command.startsWith("SET_BRIGHTNESS")) {
            String[] parts = command.split(" ");
            if (parts.length < 2) {
                throw new InvalidCommandException("SET_BRIGHTNESS requires a value, e.g. 'SET_BRIGHTNESS 75'");
            }
            int level;
            try {
                level = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                throw new InvalidCommandException("Brightness must be a whole number, got: " + parts[1], e);
            }
            setBrightness(level);
        } else if (command.equals("TURN_ON")) {
            turnOn();
        } else if (command.equals("TURN_OFF")) {
            turnOff();
        } else {
            throw new InvalidCommandException("Unknown command for SmartLight '" + getName() + "': " + command);
        }
    }

    //
    // Getters
    //

    public int getBrightness() {
        return brightness;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | brightness=%3d%%", brightness);
    }
}