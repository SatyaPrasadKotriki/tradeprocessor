package com.db.TradeProcessor.validator;

import com.db.TradeProcessor.model.Trade;

public interface CurrentTradeValidator {
    void validate(Trade currentTrade);
}
