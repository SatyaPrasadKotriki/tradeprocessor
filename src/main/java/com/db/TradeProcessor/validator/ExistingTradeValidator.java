package com.db.TradeProcessor.validator;

import com.db.TradeProcessor.model.Trade;

public interface ExistingTradeValidator {
    void validate(Trade currentTrade, Trade existingTrade);
}
