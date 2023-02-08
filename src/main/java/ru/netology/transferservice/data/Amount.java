package ru.netology.transferservice.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Data
@AllArgsConstructor
@Validated
public class Amount {
    @Min(1)
    private final Integer value;
    private final String currency;
}