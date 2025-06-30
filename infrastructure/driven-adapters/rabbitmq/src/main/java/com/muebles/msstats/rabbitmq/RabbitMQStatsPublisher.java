package com.muebles.msstats.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.muebles.msstats.model.stats.Stats;
import com.muebles.msstats.model.stats.gateways.StatsPublisher;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RabbitMQStatsPublisher implements StatsPublisher{
    private final RabbitTemplate rabbitTemplate;

    @Override
    public Mono<Void> publishStats(Stats stats) {
        rabbitTemplate.convertAndSend("events.stats.validated", stats);
        return Mono.empty();
    }
    
}
