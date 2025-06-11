package com.azaria.somemarket.dto;

import java.math.BigDecimal;

public record ProductResponse(
    String id,
    String name,
    BigDecimal price,
    boolean availability
) {}