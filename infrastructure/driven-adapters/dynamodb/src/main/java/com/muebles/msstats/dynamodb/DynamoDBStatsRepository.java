
package com.muebles.msstats.dynamodb;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.muebles.msstats.model.stats.Stats;
import com.muebles.msstats.model.stats.gateways.StatsRepository;

import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

@Component
public class DynamoDBStatsRepository implements StatsRepository {

    private final DynamoDbAsyncClient dynamoDBClient;

    public DynamoDBStatsRepository(DynamoDbAsyncClient dynamoDBClient) {
        this.dynamoDBClient = dynamoDBClient;
    }

    @Override
    public Mono<Void> save(Stats stats) {
        String timestamp = Instant.now().toString() + "-" + UUID.randomUUID();

        Map<String, AttributeValue> item = Map.of(
            "timestamp", AttributeValue.builder().s(timestamp).build(),
            "totalContactoClientes", AttributeValue.builder().n(String.valueOf(stats.getTotalContactoClientes())).build(),
            "motivoReclamo", AttributeValue.builder().n(String.valueOf(stats.getMotivoReclamo())).build(),
            "motivoGarantia", AttributeValue.builder().n(String.valueOf(stats.getMotivoGarantia())).build(),
            "motivoDuda", AttributeValue.builder().n(String.valueOf(stats.getMotivoDuda())).build(),
            "motivoCompra", AttributeValue.builder().n(String.valueOf(stats.getMotivoCompra())).build(),
            "motivoFelicitaciones", AttributeValue.builder().n(String.valueOf(stats.getMotivoFelicitaciones())).build(),
            "motivoCambio", AttributeValue.builder().n(String.valueOf(stats.getMotivoCambio())).build(),
            "hash", AttributeValue.builder().s(stats.getHash()).build()
        );

        return Mono.fromFuture(() ->
            dynamoDBClient.putItem(PutItemRequest.builder()
                .tableName("Stats")
                .item(item)
                .build())
        ).then();
    }
}
