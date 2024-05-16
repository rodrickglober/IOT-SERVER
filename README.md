# DEMO IoT Server

Welcome to the DEMO IoT Server! This server simulates an Internet of Things (IoT) environment where temperature and humidity sensors send their measurements. The server uses RabbitMQ as a message broker to handle the incoming sensor data.

## Getting Started

To get started with the DEMO IoT Server, follow these steps:

### Step 1: Set up RabbitMQ

1. Make sure you have Docker installed on your machine.

2. Create a file named `docker-compose.yml` and add the following contents:

   ```yaml
   version: '3'
   services:
     rabbitmq:
       image: rabbitmq:3.8.17-management
       ports:
         - "5672:5672"
         - "15672:15672"
       volumes:
         - ./definitions.json:/etc/rabbitmq/definitions.json
       environment:
         - RABBITMQ_DEFAULT_USER=admin
         - RABBITMQ_DEFAULT_PASS=admin
       networks:
         - rabbitmq-network
   
   networks:
     rabbitmq-network:
       driver: bridge
   ```

3. Create a file named `definitions.json` and add the following contents:

   ```json
   {
     "queues": [
       {
         "name": "temperature_queue",
         "vhost": "/",
         "auto_delete": false
       },
       {
         "name": "humidity_queue",
         "vhost": "/",
         "auto_delete": false
       }
     ]
   }
   ```

4. Save both files (`docker-compose.yml` and `definitions.json`) in the same directory.

5. Open a terminal or command prompt, navigate to the directory where the files are located, and run the following command to start RabbitMQ:

   ```
   docker-compose up
   ```

   RabbitMQ will start with the management interface accessible at `http://localhost:15672` (username: `admin`, password: `admin`).

### Step 2: Run the DEMO IoT Server

1. Download and set up the DEMO IoT Server application on your machine.

2. Configure the RabbitMQ connection information in the application's configuration file.

3. Start the DEMO IoT Server application.

### Step 3: Simulate Sensor Data

The DEMO IoT Server application is now ready to receive sensor data. You can simulate sensor measurements by sending UDP packets using the netcat command or any other UDP client.

- To send temperature data, use the following command:

  ```
  echo "sensor_id=t1; value=25" | nc -u localhost 3344
  ```

  This command sends a temperature measurement with a value of 25 to the `temperature_queue`.

- To send humidity data, use the following command:

  ```
  echo "sensor_id=h1; value=40" | nc -u localhost 3355
  ```

  This command sends a humidity measurement with a value
