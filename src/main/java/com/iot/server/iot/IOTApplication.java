package com.iot.server.iot;

import com.iot.server.iot.udp.server.UdpServer;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IOTApplication {

	private UdpServer udpServer;

	public IOTApplication(UdpServer udpServer) {
		this.udpServer = udpServer;
	}

	public static void main(String[] args) {
		SpringApplication.run(IOTApplication.class, args);
	}
	@PostConstruct
	public void startUdpServer() {
		udpServer.startServer();
	}
}
