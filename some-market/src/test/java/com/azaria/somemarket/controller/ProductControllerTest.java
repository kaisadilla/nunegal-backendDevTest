package com.azaria.somemarket.controller;

import com.azaria.somemarket.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@WebFluxTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService productSvc;

    @Test
    void testGetSimilarProductIds_whenFound_returns200 () {
        Mockito.when(productSvc.fetchSimilarIds(1L))
            .thenReturn(Mono.just(List.of(2L, 3L)));

        webTestClient.get()
            .uri("/product/1/similar")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBodyList(Long.class)
            .isEqualTo(List.of(2L, 3L));
    }

    @Test
    void testGetSimilarProductIds_whenNotFound_returns404() {
        Mockito.when(productSvc.fetchSimilarIds(999L))
            .thenReturn(Mono.error(
                new WebClientResponseException(404, "Not Found", null, null, null)
            ));

        webTestClient.get()
            .uri("/product/999/similar")
            .exchange()
            .expectStatus()
            .isNotFound();
    }

    @Test
    void testGetSimilarProductIds_whenInternalError_returns500() {
        Mockito.when(productSvc.fetchSimilarIds(42L))
            .thenReturn(Mono.error(new RuntimeException("Oops")));

        webTestClient.get()
            .uri("/product/42/similar")
            .exchange()
            .expectStatus()
            .is5xxServerError();
    }
}
