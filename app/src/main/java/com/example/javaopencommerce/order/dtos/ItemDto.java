package com.example.javaopencommerce.order.dtos;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ItemDto {

    Long id;
    String name;
    int stock;
    BigDecimal valueGross;
    String imageUri;
    double vat;
}