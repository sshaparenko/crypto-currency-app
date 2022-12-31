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



@Controller
public class PairsController {
    @Autowired
    private MongoService mongoService;

    @Autowired
    private PairsService pairsService;

    private static final Logger logger = LoggerFactory.getLogger(PairsService.class);

    //private static final Logger logger = LoggerFactory.getLogger(PairsController.class);
    @GetMapping("/addPairs/{pair1}/{pair2}")
    public ResponseEntity<String> addPairsMongo(@PathVariable String pair1, @PathVariable String pair2) {
        mongoService.addPairs(pair1, pair2);
        return ResponseEntity.ok("Data was recorded!");
    }

    @GetMapping("/getPairs/{pair}")
    public ResponseEntity<String> getPairsMongo(@PathVariable String pair) {
        String res = mongoService.getPairs(pair);
        System.out.println(res);
        return ResponseEntity.ok(res);
    }

//    @GetMapping("pairs/httpurlconnection/{pair1}/{pair2}")
//    public Pairs getPairsHttpUrlConnection(@PathVariable String pair1, @PathVariable String pair2) {
//        return pairsService.getPairsHttpUrlConnection(pair1, pair2);
//    }
//
//    @GetMapping("/pairs/httpclient/async/{pair1}/{pair2}")
//    public Pairs getPairsHttpClientAsync(@PathVariable String pair1, @PathVariable String pair2) {
//        return pairsService.getPairsHttpClientAsync(pair1, pair2);
//    }
//
//    @GetMapping("/pairs/httpclient/sync/{pair1}/{pair2}")
//    public Pairs getPairsHttpClientSync(@PathVariable String pair1, @PathVariable String pair2) {
//        return pairsService.getPairsHttpClientSync(pair1, pair2);
//    }

//    @GetMapping("/pairs/webclient/{pair1}/{pair2}")
//    public Pairs getMonoPairsWebClient(@PathVariable String pair1, @PathVariable String pair2) {
//        return pairsService.getMonoPairsWebClient(pair1, pair2);
//    }
}
