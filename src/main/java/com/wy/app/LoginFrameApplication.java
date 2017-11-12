package com.wy.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class LoginFrameApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginFrameApplication.class, args);
	}
}
