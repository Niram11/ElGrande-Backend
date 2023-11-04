package com.codecool.gastro.repository.projection;

import java.time.LocalDate;
import java.util.UUID;

public interface DetailedReviewProjection {
    UUID getId();

    String getComment();

    int getGrade();

    LocalDate getSubmissionTime();

    String getName();

    void setId(UUID id);

    void setComment(String comment);

    void setGrade(int grade);

    void setSubmissionTime(LocalDate submissionTime);

    void setName(String name);
}
