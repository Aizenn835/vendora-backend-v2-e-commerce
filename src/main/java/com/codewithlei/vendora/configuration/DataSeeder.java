package com.codewithlei.vendora.configuration;

import com.codewithlei.vendora.seller.SellerProfileEntity;
import com.codewithlei.vendora.seller.SellerRepository;
import com.codewithlei.vendora.seller.enums.SellerStatus;
import com.codewithlei.vendora.users.UserEntity;
import com.codewithlei.vendora.users.UserRepository;
import com.codewithlei.vendora.users.enums.Role;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Component
@Profile("dev")
public class DataSeeder implements CommandLineRunner {
    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String DEFAULT_PASSWORD = "Password123!";
    private final String defaultImg;

    private static final String ADMIN_EMAIL = "admin@example.com";
    private static final String ADMIN_PASSWORD = "Admin123!";

    public DataSeeder(@Value("${app.default.pfp}") String defaultImg, SellerRepository sellerRepository,
                      UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.defaultImg = defaultImg;
        this.sellerRepository = sellerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private static final List<String> PICTURES = List.of(
            "/uploads/seller-profile/default-profile.png",
            "/uploads/seller-profile/default-profile.png",
            "/uploads/seller-profile/default-profile.png",
            "/uploads/seller-profile/default-profile.png",
            "/uploads/seller-profile/default-profile.png",
            "/uploads/seller-profile/default-profile.png",
            "/uploads/seller-profile/default-profile.png",
            "/uploads/seller-profile/default-profile.png"
    );

    private static final List<String> FIRST_NAMES = List.of(
            "Juan", "Maria", "Jose", "Ana", "Miguel", "Sofia",
            "Carlo", "Bianca", "Rafael", "Isabel", "Paolo", "Camille"
    );

    private static final List<String> LAST_NAMES = List.of(
            "Dela Cruz", "Santos", "Reyes", "Garcia", "Mendoza", "Villanueva",
            "Bautista", "Ramos", "Aquino", "Castillo", "Navarro", "Torres"
    );

    private record SeedShop(String name, String description, SellerStatus status) {
    }

    private static final List<SeedShop> SHOPS = List.of(
            new SeedShop("Tech Haven PH", "Gadgets, accessories, and computer peripherals at fair prices.", SellerStatus.PENDING),
            new SeedShop("Barako Beans Co.", "Locally roasted Batangas barako coffee, whole bean and ground.", SellerStatus.APPROVED),
            new SeedShop("Manila Sneaker Vault", "Authentic sneakers and streetwear shipped within Metro Manila.", SellerStatus.PENDING),
            new SeedShop("Pinoy Pet Supplies", "Food, toys, and grooming essentials for dogs and cats.", SellerStatus.SUSPENDED),
            new SeedShop("Kusina Essentials", "Cookware, utensils, and kitchen tools for the Filipino home.", SellerStatus.PENDING),
            new SeedShop("Island Threads", "Handwoven bags and shirts made by local artisans.", SellerStatus.APPROVED),
            new SeedShop("Gadget Galore", "Phone cases, chargers, and power banks at wholesale prices.", SellerStatus.BANNED),
            new SeedShop("Bookworm Corner", "Secondhand novels, textbooks, and comics in good condition.", SellerStatus.PENDING),
            new SeedShop("Fresh Harvest Direct", "Farm-fresh vegetables and fruits delivered from Antipolo.", SellerStatus.APPROVED),
            new SeedShop("Glow Up Beauty", "Skincare and cosmetics from trusted local brands.", SellerStatus.PENDING),
            new SeedShop("Game On Collectibles", "Board games, trading cards, and action figures.", SellerStatus.SUSPENDED),
            new SeedShop("Home Nest Decor", "Affordable furniture and home decor for small spaces.", SellerStatus.PENDING)
    );

    @Override
    @Transactional
    public void run(String... args) {
        seedAdmin();
        seedSellers();
    }

    private void seedAdmin() {
        if (userRepository.existsByEmail(ADMIN_EMAIL)) {
            log.info("Admin user already exists, skipping admin seeding");
            return;
        }

        UserEntity admin = UserEntity.builder()
                .pfpUrl(defaultImg)
                .username("admin")
                .firstname("System")
                .lastname("Admin")
                .email(ADMIN_EMAIL)
                .password(passwordEncoder.encode(ADMIN_PASSWORD))
                .role(Role.ADMIN)
                .build();

        userRepository.save(admin);
        log.info("Seeded admin user {}", ADMIN_EMAIL);
    }

    private void seedSellers() {
        if (sellerRepository.count() > 0) {
            log.info("Seller profiles already exist, skipping seller seeding");
            return;
        }

        String encodedPassword = passwordEncoder.encode(DEFAULT_PASSWORD);

        List<UserEntity> users = userRepository.saveAll(
                IntStream.range(0, SHOPS.size())
                        .mapToObj(i -> buildUser(i, SHOPS.get(i).status(), encodedPassword))
                        .toList()
        );

        List<SellerProfileEntity> sellers = IntStream.range(0, SHOPS.size())
                .mapToObj(i -> buildSeller(users.get(i), SHOPS.get(i), PICTURES.get(i % PICTURES.size())))
                .toList();

        sellerRepository.saveAll(sellers);
        log.info("Seeded {} users and {} seller profiles", users.size(), sellers.size());
    }

    private UserEntity buildUser(int index, SellerStatus status, String encodedPassword) {
        int number = index + 1;
        Role role = status == SellerStatus.PENDING ? Role.CUSTOMER : Role.SELLER;

        return UserEntity.builder()
                .pfpUrl(defaultImg)
                .username("seller" + number)
                .firstname(FIRST_NAMES.get(index % FIRST_NAMES.size()))
                .lastname(LAST_NAMES.get(index % LAST_NAMES.size()))
                .email("seller" + number + "@example.com")
                .password(encodedPassword)
                .role(role)
                .build();
    }

    private SellerProfileEntity buildSeller(UserEntity user, SeedShop shop, String picture) {
        return SellerProfileEntity.builder()
                .user(user)
                .shopPfp(picture)
                .shopName(shop.name())
                .description(shop.description())
                .sellerStatus(shop.status())
                .build();
    }
}