package com.codewithlei.vendora.product.controllers;

import com.codewithlei.vendora.product.dto.newProduct.RequestProductUpdateDTO;
import com.codewithlei.vendora.product.dto.newProduct.RequestUpdateStockDTO;
import com.codewithlei.vendora.product.dto.newProduct.RequestVariantDTO;
import com.codewithlei.vendora.product.dto.productData.ResponseProductDTO;
import com.codewithlei.vendora.product.services.SellerProductService;
import com.codewithlei.vendora.product.dto.newProduct.RequestNewProductDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/seller/product")
@RequiredArgsConstructor
@Validated
public class SellerProductController {
    private final SellerProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String , String>> addProduct(Authentication authentication ,
                                                           @RequestPart("dto") @Valid RequestNewProductDTO request,
                                                           @RequestPart("img") MultipartFile file)throws IOException {
        String email = authentication.getName();
        productService.addProduct(file , email , request);
        return ResponseEntity.ok(Map.of("message" , "Successfully added!"));
    }
    @GetMapping
    public Page<ResponseProductDTO> viewSellerProduct(Authentication authentication ,
                                                      @RequestParam(defaultValue = "10") int size ,
                                                      @RequestParam(defaultValue = "0") int page){
        Pageable pageable = PageRequest.of(page , size);
        String email = authentication.getName();
        return productService.viewSellerProduct(email , pageable);
    }
    @GetMapping("/{id}")
    public ResponseProductDTO viewSellerProductById(Authentication authentication ,
                                                    @PathVariable("id") Long id){
        String email = authentication.getName();
        return productService.viewSellerProductById(email , id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Map<String , String>> updateSellerProduct(Authentication authentication ,
                                                                    @PathVariable("id") Long id ,
                                                                    @RequestBody @Valid RequestProductUpdateDTO request){
        String email = authentication.getName();
        productService.updateSellerProduct(email , id , request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message" , "Successfully updated"));
    }
    @PatchMapping(value = "/{id}/image" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String , String>> updateProductImage(Authentication authentication ,
                                                                   @PathVariable("id") Long id ,
                                                                   @RequestPart("img") MultipartFile file) throws IOException{
        String email = authentication.getName();
        productService.updateImage(email , id , file);
        return ResponseEntity.ok(Map.of("message" , "Successfully change the product image."));
    }
    @PatchMapping("/{productId}/variants/{variantId}/stock")
    public ResponseEntity<Map<String , String>> updateStockById(Authentication authentication ,
                                                                @PathVariable("productId") Long productId ,
                                                                @PathVariable("variantId") Long variantId ,
                                                                @RequestBody @Valid RequestUpdateStockDTO request){
        String email = authentication.getName();
        productService.updateStockById(email , productId , variantId , request);
        return ResponseEntity.ok(Map.of("message" , "Variant successfully updated."));
    }
    @PostMapping("/{productId}/variants")
    public ResponseEntity<Map<String , String>> addProductVariant(Authentication authentication , @PathVariable("productId") Long productId ,
                                                                  @RequestBody @Valid RequestVariantDTO request){

        String email = authentication.getName();
        productService.addProductVariant(email , productId , request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message" , "Variant successfully added."));
    }
    @DeleteMapping("/{productId}/variants/{variantId}")
    public ResponseEntity<Void> deleteVariantSize(Authentication authentication , @PathVariable("productId") Long productId ,
                                                  @PathVariable("variantId") Long variantId){
        String email = authentication.getName();
        productService.deleteVariantSize(email , productId , variantId);
        return ResponseEntity.noContent().build();
    }
}
