package com.codewithlei.vendora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VendoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(VendoraApplication.class, args);
	}

}
