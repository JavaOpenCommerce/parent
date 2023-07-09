package com.example.javaopencommerce.order.dtos;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ItemDto(Long id, String name, int stock, BigDecimal valueGross, String imageUri, double vat) {

}
