package com.muebles.msstats.usecase.processstats;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
public class ProcessStatsUseCase {
    StatsRepository repository;
    StatsPublisher publisher;
    HashProvider hashProvider;

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
