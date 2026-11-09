package com.hemanth.service;

/**
 * Enumeration of all possible DC Motor operational states and fault conditions.
 */
public enum MotorStatus {
    NORMAL,
    OVERLOAD,
    OVERHEATING,
    UNDER_SPEED,
    OVER_SPEED,
    UNDER_VOLTAGE,
    OVER_VOLTAGE,
    INVALID_INPUT
}