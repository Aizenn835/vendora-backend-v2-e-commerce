package com.codewithlei.vendora.product.dto.newProduct;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestVariantDTO {
    @NotBlank
    @Size(max = 50)
    private String size;

    @Min(0)
    private int stock;
}
