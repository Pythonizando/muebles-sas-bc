package com.muebles.msstats.model.stats.gateways;

import com.muebles.msstats.model.stats.Stats;

import reactor.core.publisher.Mono;

public interface StatsPublisher {
    Mono<Void> publishStats(Stats stats);
}
