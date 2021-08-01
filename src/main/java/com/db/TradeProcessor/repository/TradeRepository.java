package com.db.TradeProcessor.repository;

import com.db.TradeProcessor.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, String> {
}
