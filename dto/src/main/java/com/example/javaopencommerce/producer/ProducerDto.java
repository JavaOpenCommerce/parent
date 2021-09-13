package com.example.javaopencommerce.producer;

import com.example.javaopencommerce.image.ImageDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProducerDto {

    private Long id;
    private String name;
    private String description;
    private ImageDto image;
}
