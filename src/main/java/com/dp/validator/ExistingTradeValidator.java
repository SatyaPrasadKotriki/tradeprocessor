package com.dp.validator;

import com.dp.model.Trade;

public interface ExistingTradeValidator {
    void validate(Trade currentTrade, Trade existingTrade);
}
