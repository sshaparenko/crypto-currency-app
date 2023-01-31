package io.ori.task.mdbcurrencyboot.controller;


import io.ori.task.mdbcurrencyboot.service.MongoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@WebMvcTest
public class PairsControllerTest {

    @Autowired
    private PairsController controller;
    @MockBean
    private MongoService mongoService;

    private final String NULL_PARAMETER_RESPONSE = "Some of your uri variables are null!";
    private final String NOT_ONE_OF_TYPE_PAIR_MESSAGE = "Your crypto must by one of 3! BTC:USDT, ETH:USDT or XRP:USDT!";

    @Test
    void addPairsMongo() {
        //when(mongoService.addPairs("BTC", "USDT")).thenReturn(Document.parse("{data: \"data\"}"));
        String result = controller.addPairsMongo("BTC", "USDT").getBody();
        assertEquals("Data was recorded!", result);
    }

    @Test
    void addNullPairsMongo() {
        String result = controller.addPairsMongo(null, null).getBody();
        assertEquals(NULL_PARAMETER_RESPONSE, result);
    }

    @Test
    void addUnsupportedPairsMongo() {
        assertAll(
                () -> assertEquals(NOT_ONE_OF_TYPE_PAIR_MESSAGE, controller.addPairsMongo("ADA",  "USDT").getBody()),
                () -> assertEquals(NOT_ONE_OF_TYPE_PAIR_MESSAGE, controller.addPairsMongo("BTC", "DOG").getBody()),
                () -> assertEquals(NOT_ONE_OF_TYPE_PAIR_MESSAGE, controller.addPairsMongo("any text", "here").getBody())
        );
    }

    @Test
    void getPairsMongo() {
        when(mongoService.getPairs("BTC")).thenReturn("some result");
        String result = controller.getPairsMongo("BTC").getBody();
        assertEquals(result, "some result");
    }

    @Test
    void getUnsupportedPairsMongo() {
        assertAll(
                () -> assertEquals(NOT_ONE_OF_TYPE_PAIR_MESSAGE, controller.getPairsMongo("ADA").getBody()),
                () -> assertEquals(NOT_ONE_OF_TYPE_PAIR_MESSAGE, controller.getPairsMongo("any text").getBody())
        );
    }

    @Test
    void getNullPairsMongo() {
        String result = controller.getPairsMongo(null).getBody();
        assertEquals(NULL_PARAMETER_RESPONSE, result);
    }

    @Test
    void getMinPrice() {
        when(mongoService.getMinPrice("BTC")).thenReturn("22640");
        String result = controller.getMinPrice("BTC").getBody();
        assertEquals("Min price of BTC: 22640", result);
    }

    @Test
    void getUnsupportedPairMinPrice() {
        ResponseEntity<String> response = controller.getMinPrice("ADA");
        assertEquals(NOT_ONE_OF_TYPE_PAIR_MESSAGE, response.getBody());
        assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
    }

    @Test
    void getMaxPrice() {
    }

    @Test
    void geListOfPrices() {
    }
}