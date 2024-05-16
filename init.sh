#!/bin/bash

sleep 10 # Add a delay of 10 seconds

# Create the virtual host
curl -i -u guest:guest -H "Content-Type: application/json" -X PUT -d '{"name":"/"}' http://localhost:15672/api/vhosts/%2f

# Import the queue definitions
rabbitmqadmin import /etc/rabbitmq/definitions.json