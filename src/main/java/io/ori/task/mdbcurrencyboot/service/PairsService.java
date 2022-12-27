package io.ori.task.mdbcurrencyboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.ori.task.mdbcurrencyboot.service.entity.Pairs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Service
public class PairsService {

    @Autowired
    private Pairs pairs;

    private static final Logger logger = LoggerFactory.getLogger(PairsService.class);

    private final RestTemplate restTemplate;

    public PairsService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    private static final class PairsMapper extends ObjectMapper {
    }

    public Pairs  getCurrencyTextJson(String pair1, String pair2) {
        String url = "https://cex.io/api/ticker/{pair1}/{pair2}";
        String response = restTemplate.getForObject(url, String.class, pair1, pair2);
        try {
            pairs = new ObjectMapper().readValue(response, Pairs.class);
        } catch (JsonMappingException e) {
            logger.info("Json mapper exception: " + e.getMessage());
        } catch (JsonProcessingException e) {
            logger.info("Json processing exception" + e.getMessage());
        }
        return pairs;
    }

    public ResponseEntity<Pairs>  getCurrencyPlainJson(String pair1, String pair2) {
        String url = "https://cex.io/api/ticker/{pair1}/{pair2}";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Pairs> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, Pairs.class, pair1, pair2);
//        return this.restTemplate.getForObject(url, Pairs.class, pair1, pair2);
    }
}
