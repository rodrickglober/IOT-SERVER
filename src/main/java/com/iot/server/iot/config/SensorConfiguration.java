package com.iot.server.iot.config;

import java.util.List;

public class SensorConfiguration {
    private List<Sensor> sensors;

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public static class Sensor {
        private String type;
        private String id;
        private String unit;
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

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public List<Integer> getThresholds() {
            return thresholds;
        }

        public void setThresholds(List<Integer> thresholds) {
            this.thresholds = thresholds;
        }
    }
}