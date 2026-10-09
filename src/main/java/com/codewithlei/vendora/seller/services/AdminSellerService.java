package com.codewithlei.vendora.seller.services;

import com.codewithlei.vendora.seller.SellerProfileEntity;
import com.codewithlei.vendora.seller.SellerRepository;
import com.codewithlei.vendora.seller.dto.sellerData.ResponseSellerDataDTO;
import com.codewithlei.vendora.seller.enums.SellerStatus;
import com.codewithlei.vendora.seller.exception.InvalidSellerStatusException;
import com.codewithlei.vendora.seller.exception.SellerProfileNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminSellerService {
    private final SellerRepository sellerRepository;


    @Transactional(rollbackFor = Exception.class)
    public void approve(Long id){
        SellerProfileEntity seller = sellerRepository.findById(id)
                .orElseThrow(SellerProfileNotFound::new);

        if(seller.getSellerStatus() != SellerStatus.PENDING){
            throw new InvalidSellerStatusException();
        }
        seller.approve();
        seller.getUser().promoteToSeller();
    }

    public ResponseSellerDataDTO viewBySellerById(Long id){
        return sellerRepository.findById(id)
                .map(data -> ResponseSellerDataDTO.builder()
                        .shopPfp(data.getShopPfp())
                        .shopName(data.getShopName())
                        .description(data.getDescription())
                        .sellerStatus(data.getSellerStatus())
                        .createdAt(data.getCreatedAt())
                        .build())
                .orElseThrow(SellerProfileNotFound::new);
    }

    public Page<ResponseSellerDataDTO> viewAllApplication(Pageable pageable , SellerStatus status){
        return sellerRepository.findBySellerStatusOrderByCreatedAtDesc(status , pageable)
                .map(data -> ResponseSellerDataDTO
                        .builder()
                        .shopPfp(data.getShopPfp())
                        .shopName(data.getShopName())
                        .description(data.getDescription())
                        .sellerStatus(data.getSellerStatus())
                        .createdAt(data.getCreatedAt())
                        .build()
                );
    }

    @Transactional(rollbackFor = Exception.class)
    public void reject(Long id){
        SellerProfileEntity seller = sellerRepository.findById(id)
                .orElseThrow(SellerProfileNotFound::new);

        if(seller.getSellerStatus() != SellerStatus.PENDING){
            throw new InvalidSellerStatusException("Only customers that are have PENDING status can be rejected.");
        }
        seller.rejected();
    }

    @Transactional(rollbackFor = Exception.class)
    public void suspend(Long id){
        SellerProfileEntity seller = sellerRepository.findById(id)
                .orElseThrow(SellerProfileNotFound::new);

        if(seller.getSellerStatus() != SellerStatus.APPROVED){
            throw new InvalidSellerStatusException("Only customers that are have APPROVED status can be suspended.");
        }
        seller.suspend();
    }
}
