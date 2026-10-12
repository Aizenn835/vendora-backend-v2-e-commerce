package com.codewithlei.vendora.product.dto.newProduct;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestProductUpdateDTO {
    @NotBlank(message = "Product name cannot be blank.")
    @Size(max = 255, message = "Product name cannot exceed 255 characters.")
    private String name;

    @Size(max = 2000, message = "Description cannot be longer than 1000 characters.")
    private String description;

    @NotNull(message = "Price is required.")
    @Min(value = 0, message = "Price must be greater than or equal to 0.")
    private BigDecimal price;
}
