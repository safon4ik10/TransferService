package ru.netology.transferservice.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransferServiceTest {

    @Mock
    TransferService transferService;
    @Test
    void validateCardDate() {
        final String cardFromValidTill = "11/25";
        final int currentMonth = 2;
        final int currentYear = 2022;

        assertThrows(NullPointerException.class, () -> transferService.validateCardDate(cardFromValidTill, currentMonth, currentYear));
    }

    @Test
    void validateCardDateOldYear() {
        final String cardFromValidTill = "11/21";
        final int currentMonth = 2;
        final int currentYear = 2022;

        assertThrows(RuntimeException.class, () -> transferService.validateCardDate(cardFromValidTill, currentMonth, currentYear));
    }

    @Test
    void validateCardDateOldMonth() {
        final String cardFromValidTill = "01/22";
        final int currentMonth = 2;
        final int currentYear = 2022;

        assertThrows(RuntimeException.class, () -> transferService.validateCardDate(cardFromValidTill, currentMonth, currentYear));
    }
}
