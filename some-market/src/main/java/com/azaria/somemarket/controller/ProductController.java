package com.azaria.somemarket.controller;

import com.azaria.somemarket.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productSvc;

    public ProductController (ProductService productSvc) {
        this.productSvc = productSvc;
    }

    /**
     * Returns a list of ids corresponding to products similar to the one with
     * the id given.
     * @param id The id of the product to check.
     */
    @GetMapping("/{id}/similar")
    public Mono<ResponseEntity<List<Long>>> getSimilarProductIds (@PathVariable long id) {
        return productSvc.fetchSimilarIds(id)
            .map(ResponseEntity::ok)
            .onErrorResume(ex -> {
                if (
                    ex instanceof WebClientResponseException webEx
                    && webEx.getStatusCode() == HttpStatus.NOT_FOUND
                ) {
                    return Mono.just(ResponseEntity.notFound().build());
                }

                System.err.println(ex);
                return Mono.just(ResponseEntity.internalServerError().build());
            });
    }
}
