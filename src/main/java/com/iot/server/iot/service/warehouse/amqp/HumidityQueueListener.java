package com.iot.server.iot.service.warehouse.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.server.iot.model.Measurement;
import com.iot.server.iot.service.warehouse.WareHouseService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HumidityQueueListener {

    private final WareHouseService wareHouseService;
    private final ObjectMapper objectMapper;
    @Autowired
    public HumidityQueueListener(WareHouseService wareHouseService ,ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.wareHouseService = wareHouseService;
    }

   @RabbitListener(queues = "humidity_queue")
    public void receiveHumidityMessage(String message) {
        try {
            Measurement measurement = objectMapper.readValue(message, Measurement.class);
            wareHouseService.collect(measurement);
            System.out.println("Received temperature message: " + measurement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}