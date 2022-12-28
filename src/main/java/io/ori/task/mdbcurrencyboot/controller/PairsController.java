package io.ori.task.mdbcurrencyboot.controller;

import io.ori.task.mdbcurrencyboot.service.PairsService;
import io.ori.task.mdbcurrencyboot.service.entity.Pairs;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;


@Controller
public class PairsController {
    @Autowired
    private PairsService pairsService;

    //private static final Logger logger = LoggerFactory.getLogger(PairsController.class);
    @GetMapping("/pairs/{pair1}/{pair2}")
    public Pairs getPairsRestTemplate(@PathVariable String pair1, @PathVariable String pair2) {
        return pairsService.getPairsRestTemplate(pair1, pair2);
    }

    @GetMapping("pairs/httpurlconnection/{pair1}/{pair2}")
    public Pairs getPairsHttpUrlConnection(@PathVariable String pair1, @PathVariable String pair2) {
        return pairsService.getPairsHttpUrlConnection(pair1, pair2);
    }

    @GetMapping("/pairs/httpclient/async/{pair1}/{pair2}")
    public Pairs getPairsHttpClientAsync(@PathVariable String pair1, @PathVariable String pair2) {
        return pairsService.getPairsHttpClientAsync(pair1, pair2);
    }

//    @GetMapping("/pairs/webclient/{pair1}/{pair2}")
//    public Pairs getMonoPairsWebClient(@PathVariable String pair1, @PathVariable String pair2) {
//        return pairsService.getMonoPairsWebClient(pair1, pair2);
//    }
}
