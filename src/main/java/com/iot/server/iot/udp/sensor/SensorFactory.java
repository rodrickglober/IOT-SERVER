package com.iot.server.iot.udp.sensor;

import com.iot.server.iot.model.Measurement;
import com.iot.server.iot.udp.sensor.impl.HumiditySensor;
import com.iot.server.iot.udp.sensor.impl.TemperatureSensor;
import com.iot.server.iot.udp.sensor.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SensorFactory {

    private final HumiditySensor humiditySensor;
    private final TemperatureSensor temperatureSensor;

    @Autowired
    public SensorFactory(HumiditySensor humiditySensor, TemperatureSensor temperatureSensor) {
        this.humiditySensor = humiditySensor;
        this.temperatureSensor = temperatureSensor;
    }

    public Sensor createSensorFromTraceAndProcessEvent(String input) {
        if (!traceIsValidFormat(input)) {
            System.out.println("Invalid temperature message format: " + input);
            return null;
        }

        String[] parts = input.split("; ");
        String sensorId = parts[0].substring(parts[0].indexOf('=') + 1);
        double value = Double.parseDouble(parts[1].substring(parts[1].indexOf('=') + 1));

        Sensor sensor;

        if (sensorId.startsWith("t")) {
            sensor = temperatureSensor;
        } else if (sensorId.startsWith("h")) {
            sensor = humiditySensor;
        } else {
            throw new IllegalArgumentException("Invalid sensor ID: " + sensorId);
        }

        sensor.process(new Measurement(sensorId, value));

        return sensor;
    }

    private boolean traceIsValidFormat(String message) {
        String regex = "sensor_id=[a-zA-Z0-9]+; value=[0-9]+(\\.[0-9]+)?";
        return message.matches(regex);
    }
}