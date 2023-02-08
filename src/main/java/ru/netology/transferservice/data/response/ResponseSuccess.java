package ru.netology.transferservice.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseSuccess {
    private final String operationId;
}