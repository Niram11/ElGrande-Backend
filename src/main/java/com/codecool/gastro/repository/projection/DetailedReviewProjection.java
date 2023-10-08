package com.codecool.gastro.repository.projection;

import java.time.LocalDate;

public interface DetailedReviewProjection {
    String getComment();

    int getGrade();

    LocalDate getSubmissionTime();

    String getName();

    void setComment(String comment);

    void setGrade(int grade);

    void setSubmissionTime(LocalDate submissionTime);

    void setName(String name);
}
