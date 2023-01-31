package io.ori.task.mdbcurrencyboot.controller;


import io.ori.task.mdbcurrencyboot.service.MongoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest
public class PairsControllerTest {

    @Autowired
    private PairsController controller;
    @MockBean
    private MongoService mongoService;

    @Test
    void addPairsMongo() {
        //when(mongoService.addPairs("BTC", "USDT")).thenReturn(Document.parse("{data: \"data\"}"));
        String result = controller.addPairsMongo("BTC", "USDT").getBody();
        assertEquals("Data was recorded!", result);
    }

    @Test
    void addNullPairs() {
        String result = controller.addPairsMongo(null, null).getBody();
        assertEquals("Some of your uri variables are null!", result);
    }

    @Test
    void addUnsupportedPairs() {
        String expected = "Your crypto must by one of 3! BTC:USDT, ETH:USDT or XRP:USDT!";
        assertAll(
                () -> assertEquals(expected, controller.addPairsMongo("ADA",  "USDT").getBody()),
                () -> assertEquals(expected, controller.addPairsMongo("BTC", "DOG").getBody()),
                () -> assertEquals(expected, controller.addPairsMongo("any text", "here").getBody())
        );
    }

    @Test
    void getPairsMongo() {
    }

    @Test
    void getMinPrice() {
    }

    @Test
    void getMaxPrice() {
    }

    @Test
    void geListOfPrices() {
    }
}