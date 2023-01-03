package io.ori.task.mdbcurrencyboot.service;


import io.ori.task.mdbcurrencyboot.service.entity.Pairs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;


@Service
public class PairsService {
    private String response;
    private final HttpClient httpClient;
    private static final Logger logger = LoggerFactory.getLogger(PairsService.class);

    public PairsService() {
        this.httpClient = HttpClient.newBuilder().build();
    }

    public String getStringPairsHttpClientSync(String pair1, String pair2) {
        try {
            URI uri = new URI("https://cex.io/api/ticker/" + pair1 + "/" + pair2);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .timeout(Duration.of(10, SECONDS))
                    .GET()
                    .build();
            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                this.response = response.body();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return response;
    }
}
