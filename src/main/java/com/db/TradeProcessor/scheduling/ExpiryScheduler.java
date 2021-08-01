package com.db.TradeProcessor.scheduling;

import com.db.TradeProcessor.repository.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class ExpiryScheduler {

    private final TradeRepository tradeRepository;

    public ExpiryScheduler(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    //@Scheduled("0 0 * * *")
    public void expireTrade() {
        tradeRepository.findAll().forEach(trade -> {
            if (trade.getMaturityDate().compareTo(LocalDate.now()) < 0) {
                trade.setIsExpired("Y");
                tradeRepository.save(trade);
                log.info("Expiring trade: " + trade.getTradeId());
            }
        });
    }
}
