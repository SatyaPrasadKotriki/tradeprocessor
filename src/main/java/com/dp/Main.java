package com.dp;

import com.dp.model.Trade;
import com.dp.repository.TradeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.util.Arrays.asList;

@SpringBootApplication
@EnableScheduling
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(TradeRepository repository) {
        return args -> {
            Trade trade1 = new Trade("T1", 1, "CP-1", "B1", LocalDate.parse("2020-09-20"), LocalDateTime.now(), "N");
            Trade trade2 = new Trade("T2", 2, "CP-1", "B1", LocalDate.parse("2022-09-20"), LocalDateTime.now(), "N");
            repository.saveAll(asList(trade1, trade2));
        };
    }
}
