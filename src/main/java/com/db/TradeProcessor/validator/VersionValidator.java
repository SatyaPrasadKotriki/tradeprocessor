package com.db.TradeProcessor.validator;

import com.db.TradeProcessor.model.Trade;
import org.springframework.stereotype.Component;

@Component
public class VersionValidator implements ExistingTradeValidator {
    @Override
    public void validate(Trade currentTrade, Trade existingTrade) {
        if (currentTrade.getVersion() < existingTrade.getVersion()) {
            throw new RuntimeException("Trade version is obsolete");
        }
    }
}
