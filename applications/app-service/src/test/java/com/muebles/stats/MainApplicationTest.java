package com.muebles.stats;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class MainApplicationTest {
    
    @Test
    void testMainMethod() {
        assertDoesNotThrow(() -> MainApplication.main(new String[]{}));
    }
}
