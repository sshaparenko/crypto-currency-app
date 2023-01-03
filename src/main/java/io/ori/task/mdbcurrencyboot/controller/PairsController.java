package io.ori.task.mdbcurrencyboot.controller;

import io.ori.task.mdbcurrencyboot.service.MongoService;
import io.ori.task.mdbcurrencyboot.service.PairsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PairsController {
    @Autowired
    private MongoService mongoService;

    private static final Logger logger = LoggerFactory.getLogger(PairsService.class);

    @GetMapping("/addPairs/{pair1}/{pair2}")
    public ResponseEntity<String> addPairsMongo(@PathVariable String pair1, @PathVariable String pair2) {
        if (cryptoCheck(pair1)){
            mongoService.addPairs(pair1, pair2);
            return ResponseEntity.ok("Data was recorded!");
        } else {
            return ResponseEntity.badRequest().body("Your crypto must by one of 3! BTC, ETH or XRP!");
        }
    }

    @GetMapping("/getPairs/{pair}")
    public ResponseEntity<String> getPairsMongo(@PathVariable String pair) {
        if (cryptoCheck(pair)){
            String result = mongoService.getPairs(pair);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.badRequest().body("Your crypto must by one of 3! BTC, ETH or XRP!");
        }
    }

    @GetMapping("/cryptocurrencies/minprice")
    public ResponseEntity<String> getMinPrice(@RequestParam String name) {
        if (cryptoCheck(name)) {
            String result = mongoService.getMinPrice(name);
            if (result != null) {
                return ResponseEntity.ok("Min price of " + name +": " +result);
            } else {
                return ResponseEntity.ok("No data for your input :(");
            }
        } else {
            return ResponseEntity.badRequest().body("Your crypto must by one of 3! BTC, ETH or XRP!");
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
