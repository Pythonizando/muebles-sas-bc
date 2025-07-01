package com.muebles.stats.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.muebles.stats.model.stats.Stats;
import com.muebles.stats.usecase.processstats.ProcessStatsUseCase;

import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {
    private final ProcessStatsUseCase processStatsUseCase;

    public Mono<ServerResponse> handleProcessStats(ServerRequest request) {
        return request.bodyToMono(Stats.class)
            .flatMap(stats -> processStatsUseCase.processStats(stats))
            .then(ServerResponse.ok().bodyValue("Estadisticas procesadas correctamente"))
            .onErrorResume(e -> ServerResponse.badRequest().bodyValue("Error al procesar las estadisticas: " + e.getMessage()));
    }

    public Mono<ServerResponse> listenGETUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // useCase.logic();
        return ServerResponse.ok().bodyValue("");
    }
}
