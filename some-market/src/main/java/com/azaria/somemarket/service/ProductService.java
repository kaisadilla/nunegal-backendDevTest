package com.azaria.somemarket.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductService {
    private final WebClient webClient;

    public ProductService (
        WebClient.Builder builder,
        @Value("${external.api.base-url}") String baseUrl
    ) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    /**
     * Retrieves a list of ids corresponding to products similar to the one with
     * the id given.
     * @param id The id of the product to check.
     */
    public Mono<List<Long>> fetchSimilarIds (long id) {
        return webClient.get()
            .uri("/product/{id}/similarids", id)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}
