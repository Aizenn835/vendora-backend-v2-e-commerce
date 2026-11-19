package com.codewithlei.vendora.product.dto.newProduct;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestProductImageUrl {
    @NotBlank(message = "Product image is required")
    private String imageLink;
}
