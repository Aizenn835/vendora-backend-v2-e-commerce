package com.codewithlei.vendora.seller.controllers;

import com.codewithlei.vendora.seller.dto.sellerData.ResponseSellerDataDTO;
import com.codewithlei.vendora.seller.enums.SellerStatus;
import com.codewithlei.vendora.seller.services.AdminSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminSellerController {
    private final AdminSellerService adminSellerService;

    @PatchMapping("/seller/{id}/approve")
    public ResponseEntity<Map<String , String>> approve(@PathVariable("id") Long id){
       adminSellerService.approve(id);
       return ResponseEntity.ok(Map.of("message" , "Successfully promoted to seller!"));
    }

    @GetMapping("/seller/{id}")
    public ResponseSellerDataDTO viewBySellerById(@PathVariable("id") Long id){
        return adminSellerService.viewBySellerById(id);
    }

    @GetMapping("/seller")
    public Page<ResponseSellerDataDTO> viewAllApplication(@RequestParam(defaultValue = "PENDING")SellerStatus status ,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page , size);
        return adminSellerService.viewAllApplication(pageable , status);
    }

    @PatchMapping("/seller/{id}/reject")
    public ResponseEntity<Map<String , String>> reject(@PathVariable("id") Long id){
        adminSellerService.reject(id);
        return ResponseEntity.ok(Map.of("message" , "Seller profile has been successfully rejected."));
    }

    @PatchMapping("/seller/{id}/suspend")
    public ResponseEntity<Map<String , String>> suspend(@PathVariable("id") Long id){
        adminSellerService.suspend(id);
        return ResponseEntity.ok(Map.of("message" , "Seller profile has been successfully suspended."));
    }

}
