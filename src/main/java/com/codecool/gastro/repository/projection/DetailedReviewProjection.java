package com.codecool.gastro.repository.projection;

public interface DetailedReviewProjection {
    String getComment();

    String getGrade();

    String getSubmissionTime();

    String getName();

    void setComment(String comment);

    void setGrade(String grade);

    void setSubmissionTime(String submissionTime);

    void setName(String name);
}
