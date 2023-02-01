package io.ori.task.mdbcurrencyboot.service;

import io.ori.task.mdbcurrencyboot.repository.PairsRepository;
import io.ori.task.mdbcurrencyboot.service.entity.Pairs;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MongoServiceTest {
    @Autowired
    MongoService mongoService;
    @MockBean
    PairsService pairsService;
    @MockBean
    PairsRepository pairsRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addPairs() {
    }

    @Test
    void getPairs() {
        List<Pairs> pairs = new ArrayList<>();
        pairs.add(new Pairs("1", "1", "1", "1", "BTC"));
        pairs.add(new Pairs("2", "2", "2", "2", "BTC:USDT"));
        pairs.add(new Pairs("3", "3", "3", "3", "BTC:USDT"));

        String expected = "[{\"id\":\"1\",\"low\":\"1\",\"high\":\"1\",\"last\":\"1\",\"pair\":\"BTC:USDT\"},{\"id\":\"2\",\"low\":\"2\",\"high\":\"2\",\"last\":\"2\",\"pair\":\"BTC:USDT\"},{\"id\":\"3\",\"low\":\"3\",\"high\":\"3\",\"last\":\"3\",\"pair\":\"BTC:USDT\"}]";
        Optional<String> optional = Optional.of("[{\"id\":\"1\",\"low\":\"1\",\"high\":\"1\",\"last\":\"1\",\"pair\":\"BTC:USDT\"},{\"id\":\"2\",\"low\":\"2\",\"high\":\"2\",\"last\":\"2\",\"pair\":\"BTC:USDT\"},{\"id\":\"3\",\"low\":\"3\",\"high\":\"3\",\"last\":\"3\",\"pair\":\"BTC:USDT\"}]");

        Mockito.when(pairsRepository.findAllByPair("BTC:USDT")).thenReturn(pairs);
        Mockito.when(pairsService.mapPairsToJson(pairs)).thenReturn(optional);
        String result = mongoService.getPairs("BTC");
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    void getMinPriceNoConnection() {
    }

    @Test
    void getMaxPriceNoConnection() {
    }

    @Test
    void getListOfPrices() {
    }
}