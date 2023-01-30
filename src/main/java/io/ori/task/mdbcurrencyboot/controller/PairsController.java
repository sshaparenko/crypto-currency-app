package io.ori.task.mdbcurrencyboot.controller;

import io.ori.task.mdbcurrencyboot.service.MongoService;
import io.ori.task.mdbcurrencyboot.service.PairsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
@RequestMapping(("/api/v1"))
public class PairsController {
    @Autowired
    private MongoService mongoService;
    private final String NOT_ONE_OF_TYPE_PAIR_MESSAGE = "Your crypto must by one of 3! BTC:USDT, ETH:USDT or XRP:USDT!";
    private static final Logger logger = LoggerFactory.getLogger(PairsService.class);

    @PostMapping("/pairs/{pair1}/{pair2}")
    public ResponseEntity<String> addPairsMongo(@PathVariable String pair1, @PathVariable String pair2) {
        if (pair1 == null || pair2 == null) {
            return ResponseEntity.badRequest().body("Some of your uri variables are null!");
        }
        if (cryptoCheck(pair1) && pair2 == "USDT") {
            mongoService.addPairs(pair1, pair2);
            return ResponseEntity.ok("Data was recorded!");
        } else {
            return ResponseEntity.badRequest().body(NOT_ONE_OF_TYPE_PAIR_MESSAGE);
        }
    }

    @GetMapping("/pairs/{pair}")
    public ResponseEntity<String> getPairsMongo(@PathVariable String pair) {
        if (cryptoCheck(pair)) {
            String result = mongoService.getPairs(pair);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body(NOT_ONE_OF_TYPE_PAIR_MESSAGE);
        }
    }

    @GetMapping("/cryptocurrencies/minprice")
    public ResponseEntity<String> getMinPrice(@RequestParam String name) {
        if (cryptoCheck(name)) {
            String result = mongoService.getMinPrice(name);
            if (result != null) {
                return ResponseEntity.ok("Min price of " + name + ": " + result);
            } else {
                return ResponseEntity.ok("No data for your input :(");
            }
        } else {
            return ResponseEntity.badRequest().body(NOT_ONE_OF_TYPE_PAIR_MESSAGE);
        }
    }

    @GetMapping("/cryptocurrencies/maxprice")
    public ResponseEntity<String> getMaxPrice(@RequestParam String name) {
        if (cryptoCheck(name)) {
            String result = mongoService.getMaxPrice(name);
            if (result != null) {
                return ResponseEntity.ok("Max price of " + name + ": " + result);
            } else {
                return ResponseEntity.ok("No data for your input :(");
            }
        } else {
            return ResponseEntity.badRequest().body(NOT_ONE_OF_TYPE_PAIR_MESSAGE);
        }
    }
    @GetMapping("/cryptocurrencies")
    public ResponseEntity<String> geListOfPrices(@RequestParam String name,
                                                 @RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer size) {
        if (cryptoCheck(name)) {
            try {
                String result = mongoService.getListOfPrices(name, page, size);
                if (result != null) {
                    return ResponseEntity.ok("Result list for " + name +
                            " with page: " + page + " and size: " + size + " is" + ": " + result);
                } else {
                    return ResponseEntity.ok("No data for your input :(");
                }
            } catch (IOException e) {
                logger.info(e.toString());
                return ResponseEntity.badRequest().body("You should pass both page and size parameters on non of them!");
            }
        } else {
            return ResponseEntity.badRequest().body(NOT_ONE_OF_TYPE_PAIR_MESSAGE);
        }
    }

    private boolean cryptoCheck(String currency) {
        if (currency.equals("BTC") || currency.equals("ETH") || currency.equals("XRP")) {
            return true;
        } else {
            return false;
        }
    }
}
