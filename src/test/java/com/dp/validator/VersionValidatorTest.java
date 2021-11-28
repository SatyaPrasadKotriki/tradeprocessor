package com.dp.validator;

import com.dp.model.Trade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class VersionValidatorTest {

    private final VersionValidator versionValidator = new VersionValidator();

    @Test
    public void testValidate_ExceptionScenario() {
        Trade incomingTrade = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDateTime.now(), "N");
        Trade existingTrade = new Trade("T1", 2, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDateTime.now(), "N");

        try {
            versionValidator.validate(incomingTrade, existingTrade);
        } catch (RuntimeException runtimeException) {
            assertThat(runtimeException.getMessage()).isEqualTo("Trade version is obsolete");
        }
    }

    @Test
    public void testValidate() {
        Trade incomingTrade = new Trade("T1", 3, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDateTime.now(), "N");
        Trade existingTrade = new Trade("T1", 2, "CP-1", "B1", LocalDate.now().plusDays(10), LocalDateTime.now(), "N");
        versionValidator.validate(incomingTrade, existingTrade);
    }

}