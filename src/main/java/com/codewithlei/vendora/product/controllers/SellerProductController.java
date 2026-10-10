package com.codewithlei.vendora.product.controllers;

import com.codewithlei.vendora.product.dto.productData.ResponseProductDTO;
import com.codewithlei.vendora.product.services.SellerProductService;
import com.codewithlei.vendora.product.dto.newProduct.RequestNewProductDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

}
