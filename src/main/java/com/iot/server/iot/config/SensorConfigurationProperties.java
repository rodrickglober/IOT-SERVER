package com.iot.server.iot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "sensors")
public class SensorConfigurationProperties {
    private List<SensorConfiguration.Sensor> sensors;

    public List<SensorConfiguration.Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorConfiguration.Sensor> sensors) {
        this.sensors = sensors;
    }
}