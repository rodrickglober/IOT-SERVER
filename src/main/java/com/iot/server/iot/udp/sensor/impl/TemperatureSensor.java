package com.iot.server.iot.udp.sensor.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.server.iot.config.SensorConfiguration;
import com.iot.server.iot.config.SensorConfigurationProperties;
import com.iot.server.iot.model.Measurement;
import com.iot.server.iot.udp.sensor.model.Sensor;
import com.iot.server.iot.udp.sensor.model.SensorType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TemperatureSensor extends Sensor {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final SensorConfigurationProperties sensorProperties;

    @Autowired
    public TemperatureSensor(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, SensorConfigurationProperties sensorProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.sensorProperties = sensorProperties;
    }


    @Override
    protected String getSensorId() {
        return sensorProperties.getSensors().stream()
                .filter(sensor -> sensor.getType().equalsIgnoreCase("temperature"))
                .findFirst()
                .map(SensorConfiguration.Sensor::getId)
                .orElseThrow(() -> new IllegalArgumentException("Temperature sensor configuration not found"));
    }

    @Override
    protected SensorType getSensorType() {
        return SensorType.TEMPERATURE;
    }


    @Override
    public void process(Measurement measurement) {
        try {
            String jsonMeasurement = objectMapper.writeValueAsString(measurement);
            rabbitTemplate.convertAndSend("temperature_queue", jsonMeasurement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
