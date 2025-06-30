package com.muebles.msstats.usecase.processstats;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import com.muebles.msstats.model.stats.Stats;
import com.muebles.msstats.model.stats.gateways.HashProvider;
import com.muebles.msstats.model.stats.gateways.StatsPublisher;
import com.muebles.msstats.model.stats.gateways.StatsRepository;

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
                .then(publisher.publishStats(stats));
    }
}
