package com.hemanth.service;

import com.hemanth.model.MotorData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Automated JUnit 5 unit test suite for MotorMonitor fault detection logic.
 */
public class MotorMonitorTest {

    private MotorMonitor monitor;

    @BeforeEach
    void setUp() {
        // Initializes a fresh monitor instance before running each test case
        monitor = new MotorMonitor();
    }

    @Test
    @DisplayName("Test 1: Normal Motor Operation")
    void testNormalOperation() {
        MotorData data = new MotorData(1800, 5.0, 12.0, 45.0);
        Set<MotorStatus> status = monitor.evaluateStatus(data);
        
        assertEquals(99, status.size());
        assertTrue(status.contains(MotorStatus.NORMAL));
    }

    @Test
    @DisplayName("Test 2: Excessive Current (Overload)")
    void testOverload() {
        MotorData data = new MotorData(1800, 15.0, 12.0, 45.0); // 15.0A > 10.0A threshold
        Set<MotorStatus> status = monitor.evaluateStatus(data);

        assertTrue(status.contains(MotorStatus.OVERLOAD));
    }

    @Test
    @DisplayName("Test 3: Excessive Temperature (Overheating)")
    void testOverheating() {
        MotorData data = new MotorData(1800, 5.0, 12.0, 85.0); // 85°C > 80°C threshold
        Set<MotorStatus> status = monitor.evaluateStatus(data);

        assertTrue(status.contains(MotorStatus.OVERHEATING));
    }

    @Test
    @DisplayName("Test 4: RPM Below Permitted Range (Under Speed)")
    void testUnderSpeed() {
        MotorData data = new MotorData(300, 5.0, 12.0, 45.0); // 300 < 500 RPM threshold
        Set<MotorStatus> status = monitor.evaluateStatus(data);

        assertTrue(status.contains(MotorStatus.UNDER_SPEED));
    }

    @Test
    @DisplayName("Test 5: RPM Above Permitted Range (Over Speed)")
    void testOverSpeed() {
        MotorData data = new MotorData(3500, 5.0, 12.0, 45.0); // 3500 > 3000 RPM threshold
        Set<MotorStatus> status = monitor.evaluateStatus(data);

        assertTrue(status.contains(MotorStatus.OVER_SPEED));
    }

    @Test
    @DisplayName("Test 6: Low Voltage (Under Voltage)")
    void testUnderVoltage() {
        MotorData data = new MotorData(1800, 5.0, 8.0, 45.0); // 8.0V < 10.0V threshold
        Set<MotorStatus> status = monitor.evaluateStatus(data);

        assertTrue(status.contains(MotorStatus.UNDER_VOLTAGE));
    }

    @Test
    @DisplayName("Test 7: High Voltage (Over Voltage)")
    void testOverVoltage() {
        MotorData data = new MotorData(1800, 5.0, 16.0, 45.0); // 16.0V > 14.0V threshold
        Set<MotorStatus> status = monitor.evaluateStatus(data);

        assertTrue(status.contains(MotorStatus.OVER_VOLTAGE));
    }

    @Test
    @DisplayName("Test 8: Multiple Simultaneous Faults (Overload + Overheating)")
    void testMultipleFaults() {
        MotorData data = new MotorData(1800, 14.0, 12.0, 90.0);
        Set<MotorStatus> status = monitor.evaluateStatus(data);

        assertEquals(2, status.size());
        assertTrue(status.contains(MotorStatus.OVERLOAD));
        assertTrue(status.contains(MotorStatus.OVERHEATING));
    }

    @Test
    @DisplayName("Test 9: Invalid Negative Sensor Input")
    void testInvalidNegativeInput() {
        MotorData data = new MotorData(-100, 5.0, 12.0, 45.0);
        Set<MotorStatus> status = monitor.evaluateStatus(data);

        assertEquals(1, status.size());
        assertTrue(status.contains(MotorStatus.INVALID_INPUT));
    }

    @Test
    @DisplayName("Test 10: Boundary Values (Exact Upper Threshold Limits)")
    void testExactBoundaryValues() {
        // Values exactly at upper limit thresholds (500-3000 RPM, 10A current, 10-14V, 80°C)
        MotorData data = new MotorData(3000, 10.0, 14.0, 80.0);
        Set<MotorStatus> status = monitor.evaluateStatus(data);

        assertEquals(1, status.size());
        assertTrue(status.contains(MotorStatus.NORMAL));
    }
}