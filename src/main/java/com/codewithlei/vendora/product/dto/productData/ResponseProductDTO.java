package com.codewithlei.vendora.product.dto.productData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class ResponseProductDTO {
    private Long id;
    private String productImgUrl;
    private String productName;
    private String description;
    private BigDecimal price;
    private int stock;
}
