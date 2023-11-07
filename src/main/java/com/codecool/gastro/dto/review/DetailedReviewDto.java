package com.codecool.gastro.dto.review;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record DetailedReviewDto(
        UUID id,
        String comment,
        int grade,
        LocalDate submissionTime,
        String name
) {
}
