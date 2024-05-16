package com.iot.server.iot.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "sensors")
public class SensorProperties {
    private List<SensorConfig> sensors;

    public List<SensorConfig> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorConfig> sensors) {
        this.sensors = sensors;
    }

    public static class SensorConfig {
        private String type;
        private String id;
        private List<Integer> thresholds;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Integer> getThresholds() {
            return thresholds;
        }

        public void setThresholds(List<Integer> thresholds) {
            this.thresholds = thresholds;
        }
    }
}