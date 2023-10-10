package com.codecool.gastro.service.mapper;

import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.dto.review.ReviewDto;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.entity.Review;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReviewMapperTest {
    private final ReviewMapper mapper = Mappers.getMapper(ReviewMapper.class);
    private final static UUID REVIEW_ID = UUID.randomUUID();
    private final static UUID CUSTOMER_ID = UUID.randomUUID();
    private final static UUID RESTAURANT_ID = UUID.randomUUID();
    private final static String COMMENT = "comment";
    private final static int GRADE = 7;
    private final static LocalDate LOCAL_DATE = LocalDate.of(2023, 9, 24);

    @Test
    void toDto() {
        //Given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);

        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);

        Review review = new Review();
        review.setId(REVIEW_ID);
        review.setRestaurant(restaurant);
        review.setCustomer(customer);
        review.setComment(COMMENT);
        review.setGrade(GRADE);
        review.setSubmissionTime(LOCAL_DATE);

        //When
        ReviewDto reviewDto = mapper.toDto(review);

        //Then
        assertEquals(reviewDto.id(), REVIEW_ID);
        assertEquals(reviewDto.comment(), review.getComment());
        assertEquals(reviewDto.grade(), review.getGrade());
        assertEquals(reviewDto.submissionTime(), review.getSubmissionTime());
    }

    @Test
    void dtoToReview() {
        //Given
        NewReviewDto newReviewDto = new NewReviewDto(COMMENT, GRADE, CUSTOMER_ID, RESTAURANT_ID);

        //When
        Review review = mapper.dtoToReview(newReviewDto);

        //Then
        assertEquals(review.getComment(), newReviewDto.comment());
        assertEquals(review.getGrade(), newReviewDto.grade());
        assertEquals(review.getRestaurant().getId(), newReviewDto.restaurantId());
        assertEquals(review.getCustomer().getId(), newReviewDto.customerId());
    }


    @Test
    void testDtoToReview1() {
        //When
        Review review = mapper.dtoToReview(REVIEW_ID);

        //Then
        assertEquals(review.getId(), REVIEW_ID);
    }
}