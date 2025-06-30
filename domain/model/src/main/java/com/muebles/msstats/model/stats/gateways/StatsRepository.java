package com.muebles.msstats.model.stats.gateways;

import com.muebles.msstats.model.stats.Stats;

import reactor.core.publisher.Mono;

public interface StatsRepository {
    Mono<Void> save(Stats stats);
}
