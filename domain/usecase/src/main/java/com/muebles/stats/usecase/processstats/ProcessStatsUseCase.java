package com.muebles.stats.usecase.processstats;

import com.muebles.stats.model.stats.Stats;
import com.muebles.stats.model.stats.gateways.HashProvider;
import com.muebles.stats.model.stats.gateways.StatsPublisher;
import com.muebles.stats.model.stats.gateways.StatsRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProcessStatsUseCase {
    private final StatsRepository repository;
    private final StatsPublisher publisher;
    private final HashProvider hashProvider;

    public Mono<Void> processStats(Stats stats) {
        String input = String.format("%d,%d,%d,%d,%d,%d,%d",
                stats.getTotalContactoClientes(),
                stats.getMotivoReclamo(),
                stats.getMotivoGarantia(),
                stats.getMotivoDuda(),
                stats.getMotivoCompra(),
                stats.getMotivoFelicitaciones(),
                stats.getMotivoCambio());
        
        if (!hashProvider.generateHash(input).equals(stats.getHash())) {
            return Mono.error(new IllegalArgumentException("Hash inválido"));
        }

        return repository.save(stats)
            .then(publisher.publish(stats));
    }
}
