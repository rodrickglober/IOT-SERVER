package com.iot.server.iot.service.warehouse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.server.iot.model.Measurement;
import com.iot.server.iot.service.central.CentralService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WareHouseService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void collect(Measurement measurement) throws JsonProcessingException {
        String jsonMeasurement = objectMapper.writeValueAsString(measurement);
        rabbitTemplate.convertAndSend("central_queue", jsonMeasurement);
    }
}
