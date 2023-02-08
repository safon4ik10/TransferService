package ru.netology.transferservice.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWithError {
    private final String message;
    private final int id;
}