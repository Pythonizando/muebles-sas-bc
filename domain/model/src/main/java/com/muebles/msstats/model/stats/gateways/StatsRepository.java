package com.muebles.msstats.model.stats.gateways;

public interface StatsRepository {
    Mono<Void> save(Stats stats);
}
