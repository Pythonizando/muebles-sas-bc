package com.muebles.stats.dynamodb;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.muebles.stats.model.stats.Stats;
import com.muebles.stats.model.stats.gateways.StatsRepository;

import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

@Repository
public class DynamoDbStatsRepository implements StatsRepository {

    private final DynamoDbAsyncClient dynamoDbClient;

    public DynamoDbStatsRepository(DynamoDbAsyncClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    private AttributeValue toNumberAttribute(int value) {
        return AttributeValue.builder().n(String.valueOf(value)).build();
    }
    @Override
    public Mono<Void> save(Stats stats) {
        String timestamp = Instant.now().toEpochMilli() + "-" + UUID.randomUUID();
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("timestamp", AttributeValue.builder().s(timestamp).build());
        item.put("totalContactoClientes", toNumberAttribute(stats.getTotalContactoClientes()));
        item.put("motivoReclamo", toNumberAttribute(stats.getMotivoReclamo()));
        item.put("motivoGarantia", toNumberAttribute(stats.getMotivoGarantia()));
        item.put("motivoDuda", toNumberAttribute(stats.getMotivoDuda()));
        item.put("motivoCompra", toNumberAttribute(stats.getMotivoCompra()));
        item.put("motivoFelicitaciones", toNumberAttribute(stats.getMotivoFelicitaciones()));
        item.put("motivoCambio", toNumberAttribute(stats.getMotivoCambio()));
        item.put("hash", AttributeValue.builder().s(stats.getHash()).build());
        
        return Mono.fromFuture(() -> 
        dynamoDbClient.putItem(PutItemRequest.builder()
                .tableName("Stats")
                .item(item)
                .build())
        ).then();
    }

}
