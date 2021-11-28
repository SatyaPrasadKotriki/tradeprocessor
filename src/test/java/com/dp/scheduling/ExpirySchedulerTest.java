package com.dp.scheduling;

import com.dp.model.Trade;
import com.dp.repository.TradeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpirySchedulerTest {
    @Mock
    private TradeRepository tradeRepository;
    @InjectMocks
    private ExpiryScheduler expiryScheduler;

    @Test
    public void testExpireOneMatchingTrade() {
        Trade trade1 = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().minusDays(1), LocalDateTime.now(), "N");
        Trade trade2 = new Trade("T2", 1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDateTime.now(), "N");
        Trade expectedTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().minusDays(1), LocalDateTime.now(), "Y");
        when(tradeRepository.findAll()).thenReturn(asList(trade1, trade2));
        expiryScheduler.expireTrade();
        verify(tradeRepository, times(1)).save(expectedTrade);
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    public void testNoTradeToExpireScenario() {
        Trade trade1 = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDateTime.now(), "N");
        Trade trade2 = new Trade("T2", 1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDateTime.now(), "N");
        Trade expectedTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().minusDays(1), LocalDateTime.now(), "Y");
        when(tradeRepository.findAll()).thenReturn(asList(trade1, trade2));
        expiryScheduler.expireTrade();
        verify(tradeRepository, times(0)).save(expectedTrade);
        verify(tradeRepository, times(1)).findAll();
    }
}