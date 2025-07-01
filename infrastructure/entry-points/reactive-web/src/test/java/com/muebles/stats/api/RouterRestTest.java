package com.muebles.stats.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.muebles.stats.model.stats.Stats;
import com.muebles.stats.model.stats.gateways.HashProvider;
import com.muebles.stats.model.stats.gateways.StatsPublisher;
import com.muebles.stats.model.stats.gateways.StatsRepository;
import com.muebles.stats.usecase.processstats.ProcessStatsUseCase;

import reactor.core.publisher.Mono;

@WebFluxTest(RouterRest.class)
@Import({Handler.class, RouterRestTest.MockConfig.class})
@ContextConfiguration(classes = {
    RouterRest.class,
    Handler.class,
    RouterRestTest.MockConfig.class
})
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private StatsRepository repository;

    @Autowired
    private StatsPublisher publisher;

    @Autowired
    private HashProvider hashProvider;

    @Test
    void shouldAcceptValidStatsRequest(){
        Stats stats = Stats.builder()
                .totalContactoClientes(10)
                .motivoReclamo(1)
                .motivoGarantia(2)
                .motivoDuda(3)
                .motivoCompra(1)
                .motivoFelicitaciones(0)
                .motivoCambio(0)
                .build();
        String raw = "10,1,2,3,1,0,0";
        String hash = "3df583f1cdf20154044a7ef70610ed5d";
        stats.setHash(hash);

        when(hashProvider.generateHash(raw)).thenReturn(hash);
        when(repository.save(any())).thenReturn(Mono.empty());
        when(publisher.publish(any())).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/stats")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(stats)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void shouldRejectInvalidStatsRequest() {
        Stats stats = Stats.builder()
                .totalContactoClientes(1)
                .motivoReclamo(1)
                .motivoGarantia(1)
                .motivoDuda(1)
                .motivoCompra(1)
                .motivoFelicitaciones(1)
                .motivoCambio(1)
                .hash("wrong")
                .build();

        when(hashProvider.generateHash(any())).thenReturn("valid");

        webTestClient.post()
                .uri("/stats")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(stats)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void testListenGETUseCase() {
        webTestClient.get()
                .uri("/api/usecase/path")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse).isEmpty();
                        }
                );
    }

    @Test
    void testListenGETOtherUseCase() {
        webTestClient.get()
                .uri("/api/otherusercase/path")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse).isEmpty();
                        }
                );
    }

    @Test
    void testListenPOSTUseCase() {
        webTestClient.post()
                .uri("/api/usecase/otherpath")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue("")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse -> {
                            Assertions.assertThat(userResponse).isEmpty();
                        }
                );
    }

    @TestConfiguration
    static class MockConfig {

        @Bean
        StatsRepository statsRepository() {
            return Mockito.mock(StatsRepository.class);
        }

        @Bean
        StatsPublisher statsPublisher() {
            return Mockito.mock(StatsPublisher.class);
        }

        @Bean
        HashProvider hashProvider() {
            return Mockito.mock(HashProvider.class);
        }

        @Bean
        ProcessStatsUseCase processStatsUseCase(
                StatsRepository repository,
                StatsPublisher publisher,
                HashProvider hashProvider
        ) {
            return new ProcessStatsUseCase(repository, publisher, hashProvider);
        }
    }
}
