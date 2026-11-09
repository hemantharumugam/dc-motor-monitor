package com.hemanth.model;

/**
 * Stores sensor readings from a DC motor.
 */
public class MotorData {

    private final double rpm;         // Rotational Speed (RPM)
    private final double current;     // Current in Amperes (A)
    private final double voltage;     // Voltage in Volts (V)
    private final double temperature; // Temperature in degrees Celsius (°C)

    public MotorData(double rpm, double current, double voltage, double temperature) {
        this.rpm = rpm;
        this.current = current;
        this.voltage = voltage;
        this.temperature = temperature;
    }

    public double getRpm() {
        return rpm;
    }

    public double getCurrent() {
        return current;
    }

    public double getVoltage() {
        return voltage;
    }

    public double getTemperature() {
        return temperature;
    }

    @Override
    public String toString() {
        return String.format("MotorData [RPM=%.1f, Current=%.2f A, Voltage=%.2f V, Temp=%.1f °C]",
                rpm, current, voltage, temperature);
    }
}