package com.example.javaopencommerce.item;

import java.util.List;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
class SearchItem {

    private final Long id;
    private final double valueGross;
    private final Long imageId;
    private final Long producerId;
    private final List<Long> categoryIds;
    private final List<SearchItemDetails> details;
}
