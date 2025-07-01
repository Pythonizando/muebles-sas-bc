package com.muebles.stats.model.stats.gateways;

import com.muebles.stats.model.stats.Stats;

import reactor.core.publisher.Mono;

public interface StatsRepository {
    Mono<Void> save(Stats stats);
}
