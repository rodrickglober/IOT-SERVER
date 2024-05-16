package com.iot.server.iot.service.central;

import com.iot.server.iot.config.SensorConfiguration;
import com.iot.server.iot.model.Measurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CentralServiceTest {

    private CentralService centralService;
    private List<SensorConfiguration.Sensor> sensors;

    @BeforeEach
    void setUp() {
        sensors = Arrays.asList(
                createSensor("t1", "Temperature", "C°", -20, 40),
                createSensor("h1", "Humidity", "%", 0, 50)
        );
        centralService = new CentralService(sensors);
    }

    @Test
    void testCheckForThresHoldAndTriggerAlert_TemperatureOutsideThreshold() {
        Measurement measurement = new Measurement("t1", 45);
        String expectedAlertMessage = "WARNING ALERT: Sensor t1 value is outside the thresholds. Thresholds: -20.0C° - 40.0C°";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        try {
            centralService.checkForThresHoldAndTriggerAlert(measurement);
            String printedOutput = outputStream.toString().trim();
            assertEquals(expectedAlertMessage, printedOutput);
        } finally {
            System.setOut(originalPrintStream);
        }
    }

    @Test
    void testCheckForThresHoldAndTriggerAlert_TemperatureWithinThreshold() {
        Measurement measurement = new Measurement("t1", 25);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        try {
            centralService.checkForThresHoldAndTriggerAlert(measurement);
            String printedOutput = outputStream.toString().trim();
            assertTrue(printedOutput.isEmpty());
        } finally {
            System.setOut(originalPrintStream);
        }
    }

    @Test
    void testCheckForThresHoldAndTriggerAlert_HumidityOutsideThreshold() {
        Measurement measurement = new Measurement("h1", 60);
        String expectedAlertMessage = "WARNING ALERT: Sensor h1 value is outside the thresholds. Thresholds: 0.0% - 50.0%";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);
        try {
            centralService.checkForThresHoldAndTriggerAlert(measurement);
            String printedOutput = outputStream.toString().trim();
            assertEquals(expectedAlertMessage, printedOutput);
        } finally {
            System.setOut(originalPrintStream);
        }
    }

    @Test
    void testCheckForThresHoldAndTriggerAlert_HumidityWithinThreshold() {
        Measurement measurement = new Measurement("h1", 30);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalPrintStream = System.out;
        System.setOut(printStream);

        try {
            centralService.checkForThresHoldAndTriggerAlert(measurement);
            String printedOutput = outputStream.toString().trim();
            assertTrue(printedOutput.isEmpty());
        } finally {
            System.setOut(originalPrintStream);
        }
    }
    private SensorConfiguration.Sensor createSensor(String id, String type, String unit, int lowerThreshold, int upperThreshold) {
        SensorConfiguration.Sensor sensor = new SensorConfiguration.Sensor();
        sensor.setId(id);
        sensor.setType(type);
        sensor.setUnit(unit);
        sensor.setThresholds(Arrays.asList(lowerThreshold, upperThreshold));
        return sensor;
    }
}