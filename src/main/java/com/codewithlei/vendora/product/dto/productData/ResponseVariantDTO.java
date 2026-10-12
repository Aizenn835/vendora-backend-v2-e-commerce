package com.codewithlei.vendora.product.dto.productData;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseVariantDTO {
    private final Long id;
    private final String size;
    private final int stock;
}
