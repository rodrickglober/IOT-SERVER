package com.iot.server.iot.udp.sensor.model;

import com.iot.server.iot.model.Measurement;

public abstract class Sensor {
    protected abstract  String getSensorId();
    protected SensorType type;
    protected  abstract SensorType getSensorType();
    public abstract void process(Measurement measurement);
}
