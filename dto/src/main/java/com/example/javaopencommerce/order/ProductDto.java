package com.example.javaopencommerce.order;

import com.example.javaopencommerce.item.ItemDto;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private ItemDto item;
    private BigDecimal valueNett;
    private BigDecimal valueGross;
    private int amount;
}
