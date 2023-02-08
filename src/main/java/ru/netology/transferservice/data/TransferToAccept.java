package ru.netology.transferservice.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferToAccept {
    private final Transfer transfer;
    private final String operationCode;
}