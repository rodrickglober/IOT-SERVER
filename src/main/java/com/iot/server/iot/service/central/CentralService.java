package com.iot.server.iot.service.central;

import com.iot.server.iot.config.SensorProperties;
import com.iot.server.iot.model.Measurement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentralService {
    @Value("${sensors}")
    private List<SensorProperties.SensorConfig> sensors;

    public CentralService(List<SensorProperties.SensorConfig> sensors) {
        this.sensors = sensors;
    }

    public void trackEvent(Measurement measurement) {
        String sensorId = measurement.getSensorId();
        double value = measurement.getValue();
        SensorProperties.SensorConfig sensorConfig = sensors.stream()
                .filter(sensor -> sensor.getId().equals(sensorId))
                .findFirst()
                .orElse(null);

        if (sensorConfig != null) {
            double lowerThreshold = sensorConfig.getThresholds().get(0);
            double upperThreshold = sensorConfig.getThresholds().get(1);
            if (value < lowerThreshold || value > upperThreshold) {
                System.out.println("WARNING ALERT : Sensor " + sensorId + " value is outside the thresholds.");
            }
        }
    }
}
