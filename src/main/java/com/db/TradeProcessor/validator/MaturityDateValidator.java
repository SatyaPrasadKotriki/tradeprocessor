package com.db.TradeProcessor.validator;

import com.db.TradeProcessor.model.Trade;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MaturityDateValidator implements CurrentTradeValidator {

    @Override
    public void validate(Trade currentTrade) {
        if (currentTrade.getMaturityDate().compareTo(LocalDate.now()) < 0) {
            throw new RuntimeException("Maturity should be greater than today's date");
        }
    }
}
