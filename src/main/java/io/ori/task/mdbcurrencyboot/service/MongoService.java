package io.ori.task.mdbcurrencyboot.service;


import io.ori.task.mdbcurrencyboot.repository.PairsRepository;
import io.ori.task.mdbcurrencyboot.service.entity.Pairs;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MongoService {
    @Autowired
    private PairsService pairsService;
    @Autowired
    private PairsRepository pairsRepository;
    private static final Logger logger = LoggerFactory.getLogger(MongoService.class);

    public MongoService() {
    }

    public Document addPairs(String pair1, String pair2) {
        return pairsRepository.insert(Document.parse(pairsService.getStringPairsHttpClientSync(pair1, pair2)));
    }

    public String getPairs(String currency) {
        List<Pairs> list = pairsRepository.findAllByPair(currency + ":USDT");
        return list.toString();
    }
    public String getMinPrice(String name) {
        List<Pairs> pairsList = pairsRepository.findMinPrice(name + ":USDT");
        return pairsList.get(pairsList.size() - 1).getLow();
    }

    public String getMaxPrice(String name) {
        List<Pairs> pairsList = pairsRepository.findMaxPrice(name + ":USDT");
        return pairsList.get(pairsList.size() - 1).getHigh();
    }

    public String getListOfPrices(String name, Integer page, Integer size) throws IOException {
        if (page == null && size == null){
            page = 0;
            size = 10;
        } else if (page == null || size == null) {
            throw new IOException();
        }
        final Pageable pageableRequest = PageRequest.of(page, size);
        Page<Pairs> pagePairs = pairsRepository.findByPair(name + ":USDT", pageableRequest);
        return pagePairs.get().toList().toString();
    }
}
