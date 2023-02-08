package ru.netology.transferservice.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {
    private final String cardFromNumber;
    private final String cardFromValidTill;
    private final String cardFromCVV;
    private long accountBalance;
}