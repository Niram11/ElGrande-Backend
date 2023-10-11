package com.codecool.gastro.controller;

import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.dto.review.ReviewDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.entity.Review;
import com.codecool.gastro.service.ReviewService;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReviewControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ReviewService reviewService;
    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    RestaurantRepository restaurantRepository;

    private UUID reviewId;
    private UUID customerId;
    private UUID restaurantId;
    private ReviewDto reviewDto;
    private NewReviewDto newReviewDto;
    private String contentRespond;

    @BeforeEach
    void setUp() {
        reviewId = UUID.fromString("7a3404cb-fcf3-4b8e-875e-d14767b2d397");
        customerId = UUID.fromString("3466582c-580b-4d87-aa1d-615350e9598c");
        restaurantId = UUID.fromString("7b571224-8506-4947-a975-bc1fa0d5b743");

        reviewDto = new ReviewDto(
                reviewId,
                "CommentTest",
                6,
                LocalDate.of(2012, 12, 12)
        );

        newReviewDto = new NewReviewDto(
                "Comment",
                5,
                customerId,
                restaurantId
        );

        contentRespond = """
                {
                    "id": "7a3404cb-fcf3-4b8e-875e-d14767b2d397",
                    "comment": "CommentTest",
                    "grade" : 6,
                    "submissionTime": "2012-12-12"
                }
                """;
    }


    @Test
    void testGetReviewById_ShouldReturnStatusOkAndReviewDto_WhenExist() throws Exception {
        // when
        Mockito.when(reviewService.getReviewById(reviewId)).thenReturn(reviewDto);

        // then
        mockMvc.perform(get("/api/v1/reviews/" + reviewId))
                .andExpectAll(status().isOk(),
                        content().json(contentRespond));
    }

    @Test
    void testGetReviewById_ShouldReturnStatusNotFoundAndErrorMessage_WhenNoReview() throws Exception {
        // when
        Mockito.when(reviewService.getReviewById(reviewId)).thenThrow(new ObjectNotFoundException(reviewId, Review.class));

        // then
        mockMvc.perform(get("/api/v1/reviews/" + reviewId))
                .andExpectAll(status().isNotFound(),
                        jsonPath("$.errorMessage")
                                .value("Object of class " + Review.class.getSimpleName() + " and id " + reviewId + " cannot be found")
                );
    }

    @Test
    void testGetReviewsByCustomerId_ShouldReturnStatusOkAndListOfReviewDto_WhenExist() throws Exception {
        // when
        Mockito.when(reviewService.getReviewByCustomerId(customerId)).thenReturn(List.of());

        // then
        mockMvc.perform(get("/api/v1/reviews")
                        .param("customerId", String.valueOf(customerId)))
                .andExpectAll(status().isOk(),
                        content().json("[]")
                );
    }

    @Test
    void testGetDetailedReviewsByRestaurantId_ShouldReturnStatusOkAndListOfDetailedReview_WhenExist() throws Exception {
        // when
        Mockito.when(reviewService.getDetailedReviewsByRestaurantId(restaurantId)).thenReturn(List.of());

        // then
        mockMvc.perform(get("/api/v1/reviews/details")
                        .param("restaurantId", "7b571224-8506-4947-a975-bc1fa0d5b743"))
                .andExpectAll(status().isOk(),
                        content().json("[]")
                );
    }

    @Test
    void testCreateNewReview_ShouldReturnStatusCreatedAndReviewDto_WhenValidValues() throws Exception {
        String contentRequest = """
                {
                    "comment": "Comment",
                    "grade" : 5,
                    "customerId": "3466582c-580b-4d87-aa1d-615350e9598c",
                    "restaurantId": "7b571224-8506-4947-a975-bc1fa0d5b743"
                }
                """;

        // when
        Mockito.when(reviewService.saveReview(newReviewDto)).thenReturn(reviewDto);
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Optional.of(new Customer()));
        Mockito.when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(new Restaurant()));

        // then
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentRequest))
                .andExpectAll(status().isCreated(),
                        content().json(contentRespond)
                );
    }


    private static Stream<Arguments> provideIntForIsBlank() {
        return Stream.of(
                Arguments.of("comment", -1, "Grade must be greater then or equal 1"),
                Arguments.of("comment", 11, "Grade must be less then or equal 10"),
                Arguments.of("", 5, "Comment cannot be empty")
        );
    }
}
