package com.codewithlei.vendora.product.dto.newProduct;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateStockDTO {
    @Min(0)
    private int stock;
}
