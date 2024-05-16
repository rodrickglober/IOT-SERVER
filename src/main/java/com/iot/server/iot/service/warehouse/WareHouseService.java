package com.iot.server.iot.service.warehouse;

import com.iot.server.iot.model.Measurement;
import com.iot.server.iot.service.central.CentralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WareHouseService {
    @Autowired
    private CentralService centralService;

    public void collect(Measurement measurement){
       //Add other logic before collect as audit
        centralService.trackEvent(measurement);
    }
}
