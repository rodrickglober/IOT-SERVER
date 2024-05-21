package com.iot.server.iot.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sound.midi.Receiver;

@Configuration
public class RabbitConfiguration {

    static final String topicExchangeName = "sensor";

    static final String temperature_queue = "temperature_queue";


    static final String humidity_queue = "humidity_queue";

    @Bean
    Queue temperature_queue() {
        return new Queue(temperature_queue, false);
    }

    @Bean
    Queue humidity_queue() {
        return new Queue(humidity_queue, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("temperature_queue");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(temperature_queue,humidity_queue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveTemperatureMessage");
    }

    @Bean
    MessageListenerAdapter listenerAdapte2r(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveHumidityMessage");
    }
}
