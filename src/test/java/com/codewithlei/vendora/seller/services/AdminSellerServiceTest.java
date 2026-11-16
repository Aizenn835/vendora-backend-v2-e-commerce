package com.codewithlei.vendora.seller.services;

import com.codewithlei.vendora.seller.SellerProfileEntity;
import com.codewithlei.vendora.seller.SellerRepository;
import com.codewithlei.vendora.seller.enums.SellerStatus;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdminSellerServiceTest {

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private AdminSellerService adminSellerService;

}
