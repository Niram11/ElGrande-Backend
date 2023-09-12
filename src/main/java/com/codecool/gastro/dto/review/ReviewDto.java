package com.codecool.gastro.dto.review;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReviewDto(
        UUID id,
        String comment,
        BigDecimal grade,
        LocalDate submissionTime
) {
}