package com.iot.server.iot.service.central;

import com.iot.server.iot.config.SensorConfiguration;
import com.iot.server.iot.config.SensorConfigurationProperties;
import com.iot.server.iot.model.Measurement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentralService {
    @Value("${sensors}")
    private List<SensorConfiguration.Sensor> sensors;

    public CentralService(List<SensorConfiguration.Sensor> sensors) {
        this.sensors = sensors;
    }

    public void trackEvent(Measurement measurement) {
        String sensorId = measurement.getSensorId();
        double value = measurement.getValue();
        SensorConfiguration.Sensor sensorConfig = sensors.stream()
                .filter(sensor -> sensor.getId().equals(sensorId))
                .findFirst()
                .orElse(null);

        if (sensorConfig != null) {
            double lowerThreshold = sensorConfig.getThresholds().get(0);
            double upperThreshold = sensorConfig.getThresholds().get(1);
            String unit = sensorConfig.getUnit();

            if (value < lowerThreshold || value > upperThreshold) {
                System.out.println("WARNING ALERT: Sensor " + sensorId + " value is outside the thresholds. Thresholds: " +
                        lowerThreshold + unit + " - " + upperThreshold + unit);
            }
        }
    }
}
