package com.muebles.stats.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.muebles.stats.model.stats.Stats;
import com.muebles.stats.model.stats.gateways.StatsPublisher;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RabbitMQStatsPublisher implements StatsPublisher{
    private final RabbitTemplate rabbitTemplate;

    @Override
    public Mono<Void> publish(Stats stats) {
        rabbitTemplate.convertAndSend("event.stats.validated", stats);
        return Mono.empty();
    }
    
}
