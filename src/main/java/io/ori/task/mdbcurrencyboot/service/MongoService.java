package io.ori.task.mdbcurrencyboot.service;


import io.ori.task.mdbcurrencyboot.repository.PairsRepository;
import io.ori.task.mdbcurrencyboot.service.entity.Pairs;
import lombok.NoArgsConstructor;
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
import java.util.Optional;


@Service
@NoArgsConstructor
public class MongoService {
    @Autowired
    private PairsService pairsService;
    @Autowired
    private PairsRepository pairsRepository;
    private static final Logger logger = LoggerFactory.getLogger(MongoService.class);

    public Document addPairs(String pair1, String pair2) {
        //check null
        return pairsRepository.insert(Document.parse(pairsService.getStringPairsHttpClientSync(pair1, pair2)));
    }

    public String getPairs(String currency) {
        List<Pairs> list = pairsRepository.findAllByPair(currency + ":USDT");
        Optional<String> result = pairsService.mapPairsToJson(list);
        return result.orElse("There is no data for your crypto :(");
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
