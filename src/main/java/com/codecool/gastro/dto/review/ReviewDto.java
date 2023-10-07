package com.codecool.gastro.dto.review;


import java.time.LocalDate;
import java.util.UUID;

public record ReviewDto(
        UUID id,
        String comment,
        int grade,
        LocalDate submissionTime
) {
}