package com.codewithlei.vendora.product.dto.newProduct;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class RequestNewProductDTO {

    @NotBlank(message = "Product name cannot be blank.")
    @Size(max = 255, message = "Product name cannot exceed 255 characters.")
    private String productName;

    @Size(max = 2000, message = "Description cannot be longer than 1000 characters.")
    private String description;

    @NotNull(message = "Price is required.")
    @Min(value = 0, message = "Price must be greater than or equal to 0.")
    private BigDecimal price;

    @Min(value = 0, message = "Stock cannot be negative.")
    private int stock;
}
