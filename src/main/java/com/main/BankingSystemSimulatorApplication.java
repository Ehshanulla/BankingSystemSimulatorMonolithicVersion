package com.main;

import com.service.accountservice.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com")
@EnableMongoRepositories(basePackages = "com.repository")
public class BankingSystemSimulatorApplication {

    private static final Logger log = LoggerFactory.getLogger(BankingSystemSimulatorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BankingSystemSimulatorApplication.class, args);
        log.info("Bank server is up!!!!");
	}

}
