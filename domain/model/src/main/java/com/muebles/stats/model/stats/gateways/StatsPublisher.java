package com.muebles.stats.model.stats.gateways;

import com.muebles.stats.model.stats.Stats;

import reactor.core.publisher.Mono;

public interface StatsPublisher {
    Mono<Void> publish(Stats stats);
}
