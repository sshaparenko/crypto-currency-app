package io.ori.task.mdbcurrencyboot.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.http.HttpClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PairsServiceTest {
    PairsService service;

    @BeforeEach
    void setUp() {
        service = new PairsService();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getStringPairsHttpClientSync() {
        String response = service.getStringPairsHttpClientSync("BTC", "USDT");
        assertNotNull(response);
    }
}