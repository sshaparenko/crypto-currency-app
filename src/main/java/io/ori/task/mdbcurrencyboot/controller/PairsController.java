package io.ori.task.mdbcurrencyboot.controller;

import io.ori.task.mdbcurrencyboot.service.PairsService;
import io.ori.task.mdbcurrencyboot.service.entity.Pairs;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@Controller
public class PairsController {
    @Autowired
    private PairsService pairsService;
    //private static final Logger logger = LoggerFactory.getLogger(PairsController.class);
    @GetMapping("/pairs/{pair1}/{pair2}")
    public Pairs getPairsCurrency(@PathVariable String pair1, @PathVariable String pair2) {
        return pairsService.getCurrencyTextJson(pair1, pair2);
    }
}
