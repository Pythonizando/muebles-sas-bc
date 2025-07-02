package com.muebles.stats.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;


class DynamoDbConfigTest {
    @Test
    void testDynamoDbAsyncClientBeanCreation() {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DynamoDbConfig.class)) {
            DynamoDbAsyncClient client = context.getBean(DynamoDbAsyncClient.class);
            assertNotNull(client);
            assertEquals("http://localhost:8000", client.serviceClientConfiguration().endpointOverride().get().toString());
            assertEquals("us-east-1", client.serviceClientConfiguration().region().id());
        }
    }
}