package com.codewithlei.vendora.seller.controllers;

import com.codewithlei.vendora.seller.dto.newSeller.RequestNewSellerDTO;
import com.codewithlei.vendora.seller.dto.sellerData.ResponseSellerDataDTO;
import com.codewithlei.vendora.seller.services.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/seller")
@RequiredArgsConstructor
public class SellerController {
    private final SellerService sellerService;

    @PostMapping(value = "/apply" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String , String>> apply(Authentication authentication ,
                                                      @RequestPart("dto") @Valid RequestNewSellerDTO request,
                                                      @RequestPart("img") MultipartFile file) throws IOException {
        String email = authentication.getName();
        sellerService.apply(email , request , file);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message" , "Your application was successful and is waiting for approval."));
    }

    @GetMapping("/application")
    public ResponseSellerDataDTO application(Authentication authentication){
        String email = authentication.getName();
        return sellerService.applicationStatus(email);
    }
}
