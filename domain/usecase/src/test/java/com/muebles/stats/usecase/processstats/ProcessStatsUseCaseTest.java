package com.muebles.stats.usecase.processstats;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.muebles.stats.model.stats.Stats;
import com.muebles.stats.model.stats.gateways.HashProvider;
import com.muebles.stats.model.stats.gateways.StatsPublisher;
import com.muebles.stats.model.stats.gateways.StatsRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ProcessStatsUseCaseTest {
    
    @Mock
    private StatsRepository repository;
    @Mock
    private StatsPublisher publisher;
    @Mock
    private HashProvider hashProvider;
    @InjectMocks
    private ProcessStatsUseCase processStatsUseCase;

    @Test
    void shouldProcesswhenHashIsValid() {
        Stats stats = Stats.builder()
                .totalContactoClientes(10)
                .motivoReclamo(1)
                .motivoGarantia(2)
                .motivoDuda(3)
                .motivoCompra(1)
                .motivoFelicitaciones(0)
                .motivoCambio(0)
                .hash("validhash")
                .build();

        String raw = "10,1,2,3,1,0,0";

        when(hashProvider.generateHash(raw)).thenReturn("validhash");
        when(repository.save(stats)).thenReturn(Mono.empty());
        when(publisher.publish(stats)).thenReturn(Mono.empty());

        StepVerifier.create(processStatsUseCase.processStats(stats))
                .verifyComplete();

        verify(repository).save(stats);
        verify(publisher).publish(stats);
    }

    @Test
    void shouldFailWhenHashIsInvalid() {
        Stats stats = Stats.builder()
                .totalContactoClientes(10)
                .motivoReclamo(1)
                .motivoGarantia(2)
                .motivoDuda(3)
                .motivoCompra(1)
                .motivoFelicitaciones(0)
                .motivoCambio(0)
                .hash("invalid")
                .build();

        when(hashProvider.generateHash(anyString())).thenReturn("valid");

        StepVerifier.create(processStatsUseCase.processStats(stats))
                .expectErrorMatches(e -> e instanceof IllegalArgumentException &&
                        e.getMessage().equals("Hash inválido"))
                .verify();
    }
}
