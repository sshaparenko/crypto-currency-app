package io.ori.task.mdbcurrencyboot.service;

import io.ori.task.mdbcurrencyboot.service.entity.Pairs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Test
    void testGetStringPairsHttpClientSync() {
    }

    @Test
    void mapPairsToJson() {
        List<Pairs> pairs = new ArrayList<>();
        pairs.add(new Pairs("1", "1", "1", "1", "BTC:USDT"));
        pairs.add(new Pairs("2", "2", "2", "2", "BTC:USDT"));
        pairs.add(new Pairs("3", "3", "3", "3", "BTC:USDT"));

        Optional<String> result = service.mapPairsToJson(pairs);

        System.out.println(result.get());
        assertEquals("[{\"id\":\"1\",\"low\":\"1\",\"high\":\"1\",\"last\":\"1\",\"pair\":\"BTC:USDT\"},{\"id\":\"2\",\"low\":\"2\",\"high\":\"2\",\"last\":\"2\",\"pair\":\"BTC:USDT\"},{\"id\":\"3\",\"low\":\"3\",\"high\":\"3\",\"last\":\"3\",\"pair\":\"BTC:USDT\"}]", result.get());
    }
}