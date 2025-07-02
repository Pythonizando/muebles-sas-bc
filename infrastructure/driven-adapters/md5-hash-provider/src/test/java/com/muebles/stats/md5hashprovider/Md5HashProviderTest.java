package com.muebles.stats.md5hashprovider;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Md5HashProviderTest {
    @Test
    void testGenerateHash() {
        Md5HashProvider provider = new Md5HashProvider();
        String hash = provider.generateHash("test");
        assertEquals("098f6bcd4621d373cade4e832627b4f6", hash); // hash MD5 de "test"
    }

    @Test
    void testGenerateHashEmpty() {
        Md5HashProvider provider = new Md5HashProvider();
        String hash = provider.generateHash("");
        assertEquals("d41d8cd98f00b204e9800998ecf8427e", hash); // hash MD5 de ""
    }
}
