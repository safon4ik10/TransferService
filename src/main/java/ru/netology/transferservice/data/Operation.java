package ru.netology.transferservice.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Operation {
    private final String operationId;
    private final String code;
}