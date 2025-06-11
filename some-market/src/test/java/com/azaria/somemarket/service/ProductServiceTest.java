package com.azaria.somemarket.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

public class ProductServiceTest {
    private WebClient.Builder builder;
    private WebClient webClient;
    private ProductService productSvc;

    @BeforeEach
    void setUp () {
        builder = Mockito.mock(WebClient.Builder.class);
        webClient = Mockito.mock(WebClient.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(builder.baseUrl("http://test")).thenReturn(builder);
        Mockito.when(builder.build()).thenReturn(webClient);

        productSvc = new ProductService(builder, "http://test");
    }

    @Test
    void fetchSimilarIds_returnsList_whenApiRespondsOk () {
        List<Long> mockIds = List.of(1L, 2L, 3L);

        var reqHeadersUriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        var reqHeadersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        var respSpec = Mockito.mock(WebClient.ResponseSpec.class);

        Mockito.when(webClient.get()).thenReturn(reqHeadersUriSpec);
        Mockito.when(reqHeadersUriSpec.uri("/product/{id}/similarids", 42L))
            .thenReturn(reqHeadersSpec);
        Mockito.when(reqHeadersSpec.retrieve()).thenReturn(respSpec);
        Mockito.when(respSpec.bodyToMono(any(ParameterizedTypeReference.class)))
            .thenReturn(Mono.just(mockIds));

        Mono<List<Long>> result = productSvc.fetchSimilarIds(42L);

        StepVerifier.create(result)
            .expectNext(mockIds)
            .verifyComplete();
    }
}
