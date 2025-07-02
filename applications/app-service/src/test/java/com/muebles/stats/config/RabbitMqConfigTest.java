package com.muebles.stats.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;

public class RabbitMqConfigTest {
    private final RabbitMqConfig config = new RabbitMqConfig();

    @Test
    void testMessageConverterBean() {
        Jackson2JsonMessageConverter converter = config.messageConverter();
        assertNotNull(converter);
    }

    @Test
    void testRabbitTemplateBean() {
        ConnectionFactory connectionFactory = Mockito.mock(ConnectionFactory.class);
        Jackson2JsonMessageConverter converter = config.messageConverter();
        RabbitTemplate template = config.rabbitTemplate(connectionFactory, converter);
        assertNotNull(template);
        assertEquals(converter, template.getMessageConverter());
    }

    @Test
    void testQueueBean() {
        Queue queue = config.queue();
        assertNotNull(queue);
        assertEquals("event.stats.validated", queue.getName());
        assertTrue(queue.isDurable());
    }
}
