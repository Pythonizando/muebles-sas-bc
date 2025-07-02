package com.muebles.stats.model.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StatsTest {

    @Test
    void testBuilderAndGetters() {
        Stats stats = Stats.builder()
                .totalContactoClientes(10)
                .motivoReclamo(1)
                .motivoGarantia(2)
                .motivoDuda(3)
                .motivoCompra(4)
                .motivoFelicitaciones(5)
                .motivoCambio(6)
                .hash("abc123")
                .build();

        assertEquals(10, stats.getTotalContactoClientes());
        assertEquals(1, stats.getMotivoReclamo());
        assertEquals(2, stats.getMotivoGarantia());
        assertEquals(3, stats.getMotivoDuda());
        assertEquals(4, stats.getMotivoCompra());
        assertEquals(5, stats.getMotivoFelicitaciones());
        assertEquals(6, stats.getMotivoCambio());
        assertEquals("abc123", stats.getHash());
    }

    @Test
    void testToBuilder() {
        Stats stats = Stats.builder().totalContactoClientes(5).hash("hash").build();
        Stats copy = stats.toBuilder().motivoReclamo(100).build();
        assertEquals(5, copy.getTotalContactoClientes());
        assertEquals(100, copy.getMotivoReclamo());
        assertEquals("hash", copy.getHash());
    }
}
