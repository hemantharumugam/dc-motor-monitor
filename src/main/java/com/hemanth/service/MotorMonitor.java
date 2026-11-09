package com.hemanth.service;

import com.hemanth.model.MotorData;
import java.util.EnumSet;
import java.util.Set;

/**
 * Business logic engine for evaluating DC motor readings against threshold parameters.
 */
public class MotorMonitor {

    // Default threshold limits (Illustrative values for demonstration)
    private final double minRpm;
    private final double maxRpm;
    private final double maxCurrent;
    private final double minVoltage;
    private final double maxVoltage;
    private final double maxTemperature;

    /**
     * Default constructor using standard operational limits.
     */
    public MotorMonitor() {
        this(500.0, 3000.0, 10.0, 10.0, 14.0, 80.0);
    }

    /**
     * Parameterized constructor for custom motor specifications.
     */
    public MotorMonitor(double minRpm, double maxRpm, double maxCurrent, 
                        double minVoltage, double maxVoltage, double maxTemperature) {
        this.minRpm = minRpm;
        this.maxRpm = maxRpm;
        this.maxCurrent = maxCurrent;
        this.minVoltage = minVoltage;
        this.maxVoltage = maxVoltage;
        this.maxTemperature = maxTemperature;
    }

    /**
     * Evaluates motor readings and returns a Set containing all detected statuses/faults.
     */
    public Set<MotorStatus> evaluateStatus(MotorData data) {
        Set<MotorStatus> statusSet = EnumSet.noneOf(MotorStatus.class);

        if (data == null) {
            statusSet.add(MotorStatus.INVALID_INPUT);
            return statusSet;
        }

        // 1. Validation check for negative physical values
        if (data.getRpm() < 0 || data.getCurrent() < 0 || data.getVoltage() < 0 || data.getTemperature() < -273.15) {
            statusSet.add(MotorStatus.INVALID_INPUT);
            return statusSet;
        }

        // 2. Evaluate individual fault conditions
        if (data.getCurrent() > maxCurrent) {
            statusSet.add(MotorStatus.OVERLOAD);
        }
        if (data.getTemperature() > maxTemperature) {
            statusSet.add(MotorStatus.OVERHEATING);
        }
        if (data.getRpm() < minRpm) {
            statusSet.add(MotorStatus.UNDER_SPEED);
        }
        if (data.getRpm() > maxRpm) {
            statusSet.add(MotorStatus.OVER_SPEED);
        }
        if (data.getVoltage() < minVoltage) {
            statusSet.add(MotorStatus.UNDER_VOLTAGE);
        }
        if (data.getVoltage() > maxVoltage) {
            statusSet.add(MotorStatus.OVER_VOLTAGE);
        }

        // 3. If no faults detected, motor status is NORMAL
        if (statusSet.isEmpty()) {
            statusSet.add(MotorStatus.NORMAL);
        }

        return statusSet;
    }
}