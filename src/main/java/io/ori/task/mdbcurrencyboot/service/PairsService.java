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
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
public class PairsService {

    @Autowired
    private Pairs pairs;
    private static final Logger logger = LoggerFactory.getLogger(PairsService.class);
    private final RestTemplate restTemplate;
    private final WebClient webClient;
    private URL getPairsHttpUrl;

    public PairsService(RestTemplateBuilder restTemplateBuilder, WebClient.Builder webClientBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.webClient = webClientBuilder.baseUrl("https://cex.io/api/").build();
    }

    public Pairs getPairsRestTemplate(String pair1, String pair2) {
        String url = "https://cex.io/api/ticker/{pair1}/{pair2}";
        String response = restTemplate.getForObject(url, String.class, pair1, pair2);
        try {
            pairs = new ObjectMapper().readValue(response, Pairs.class);
        } catch (JsonMappingException e) {
            logger.info("Json mapper exception: " + e.getMessage());
        } catch (JsonProcessingException e) {
            logger.info("Json processing exception" + e.getMessage());
        }
        logger.info("Pairs object: " + pairs.toString());
        return pairs;
    }

    public Pairs getPairsHttpUrlConnection(String pair1, String pair2) {
        try {
            getPairsHttpUrl = new URL("https://cex.io/api/ticker/" + pair1 + "/" + pair2);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) getPairsHttpUrl.openConnection();
            InputStream inputStream = connection.getInputStream();
            pairs = new ObjectMapper().readValue(inputStream, Pairs.class);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        logger.info("Pairs object: " + pairs);
        return pairs;
    }

    public Pairs getPairsHttpClientAsync(String pair1, String pair2) {
        var client = HttpClient.newHttpClient();

        var request = HttpRequest.newBuilder(
                URI.create("https://cex.io/api/ticker/" + pair1 + "/" + pair2))
                .header("accept", "application/json")
                .GET()
                .build();

        CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        try {
            String stringResponse = response.get().body();
            try {
                pairs = new ObjectMapper().readValue(stringResponse, Pairs.class);
            } catch (JsonMappingException e) {
                logger.info("Json mapper exception: " + e.getMessage());
            } catch (JsonProcessingException e) {
                logger.info("Json processing exception" + e.getMessage());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info("Pairs object: " + pairs);
        return pairs;
    }

    //trying to use WebClient to send HTTP request

//    public Pairs getMonoPairsWebClient(String pair1, String pair2) {
//        String url = "https://cex.io/api/ticker/{pair1}/{pair2}";
//        String result = " ";
//
//        Mono<String> monoPairs = this.webClient.get()
//                .uri("/ticker/{pair1}/{pair2}", pair1, pair2)
//                .retrieve().bodyToMono(String.class);
//
//        monoPairs.subscribe(
//                value -> result,
//                error -> error.printStackTrace(),
//                () -> logger.info("Completed without a value")
//        );
//
//        logger.info("Mono Pairs: " + monoPairs);
//
//        try {
//            pairs = new ObjectMapper().readValue(monoPairs, Pairs.class);
//        } catch (JsonMappingException e) {
//            logger.info("Json mapper exception: " + e.getMessage());
//        } catch (JsonProcessingException e) {
//            logger.info("Json processing exception" + e.getMessage());
//        }
//        return pairs;
//    }
}
