package com.iot.server.iot.udp.server;

import com.iot.server.iot.udp.sensor.SensorFactory;
import com.iot.server.iot.udp.sensor.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class UdpServer {
    private final SensorFactory sensorFactory;

    private static final int BUFFER_SIZE = 1024;

    @Autowired
    public UdpServer(SensorFactory sensorFactory) {
        this.sensorFactory = sensorFactory;
    }

    public void startServer() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CompletionService<Void> completionService = new ExecutorCompletionService<>(executorService);

        completionService.submit(() -> {
            startSensorServer(3344, "Temperature");
            return null;
        });

        completionService.submit(() -> {
            startSensorServer(3355, "Humidity");
            return null;
        });

        try {
            for (int i = 0; i < 2; i++) {
                completionService.take().get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        System.out.println("WAITING FOR NEW EVENTS");
    }

    void startSensorServer(int port, String sensorType) throws Exception {
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Received " + sensorType + " UDP message: " + message);
            Sensor sensor = sensorFactory.createSensorFromTraceAndProcessEvent(message.trim());
            if (sensor != null) {
                System.out.println("Event Executed for Sensor " + sensor);
            }
            // CLEAN UP BUFFER MAKING SURE IS EMPTY FOR NEXT MESSAGE
            buffer = new byte[BUFFER_SIZE];
            packet.setData(buffer);
        }
    }
}