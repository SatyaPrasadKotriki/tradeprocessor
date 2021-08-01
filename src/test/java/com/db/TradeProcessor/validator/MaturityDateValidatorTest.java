package com.db.TradeProcessor.validator;

import com.db.TradeProcessor.model.Trade;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class MaturityDateValidatorTest {

    private final MaturityDateValidator maturityDateValidator = new MaturityDateValidator();

    @Test
    public void testValidate() {
        Trade incomingTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDateTime.now(), "N");
        maturityDateValidator.validate(incomingTrade);
    }

    @Test
    public void testValidate_throwingException() {
        Trade incomingTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().minusDays(1), LocalDateTime.now(), "N");
        try {
            maturityDateValidator.validate(incomingTrade);
            fail("Test failed");
        } catch (RuntimeException exception) {
            Assertions.assertThat(exception.getMessage()).isEqualTo("Maturity should be greater than today's date");
        }
    }
}