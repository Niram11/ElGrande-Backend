package com.codecool.gastro.service;

import com.codecool.gastro.dto.review.DetailedReviewDto;
import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.repository.ReviewRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.entity.Review;
import com.codecool.gastro.repository.projection.DetailedReviewProjection;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.ReviewMapper;
import com.codecool.gastro.service.validation.CustomerValidation;
import com.codecool.gastro.service.validation.RestaurantValidation;
import com.codecool.gastro.service.validation.ReviewValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @InjectMocks
    ReviewService service;
    @Mock
    ReviewMapper mapper;
    @Mock
    ReviewRepository repository;
    @Mock
    ReviewValidation validation;
    @Mock
    RestaurantValidation restaurantValidation;
    @Mock
    CustomerValidation customerValidation;

    ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    DetailedReviewProjection projection = factory.createProjection(DetailedReviewProjection.class);
    private final static String COMMENT = "comment";
    private final static int GRADE = 4;
    private final static String NAME = "Name";
    private final static UUID REVIEW_ID = UUID.randomUUID();
    private final static UUID CUSTOMER_ID = UUID.randomUUID();
    private final static UUID RESTAURANT_ID = UUID.randomUUID();
    private final static LocalDate SUBMISSION_TIME = LocalDate.of(2023, 9, 24);

    @Test
    void testSaveReviewShouldReturnDetailedReviewDtoWhenAllIdExist() {
        //Given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);

        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);

        Review review = new Review();
        review.setId(REVIEW_ID);
        review.setCustomer(customer);
        review.setRestaurant(restaurant);
        review.setSubmissionTime(SUBMISSION_TIME);
        review.setComment(COMMENT);
        review.setGrade(GRADE);

        projection.setName(NAME);
        projection.setComment(COMMENT);
        projection.setGrade(GRADE);
        projection.setSubmissionTime(SUBMISSION_TIME);

        NewReviewDto newReviewDto = new NewReviewDto(COMMENT, GRADE, CUSTOMER_ID, RESTAURANT_ID);
        DetailedReviewDto detailedReviewDto = new DetailedReviewDto(
                REVIEW_ID,
                COMMENT,
                GRADE,
                SUBMISSION_TIME,
                NAME
        );


        //Mocking interactions
        Mockito.when(mapper.dtoToReview(newReviewDto)).thenReturn(review);
        Mockito.when(repository.save(any())).thenReturn(review);
        Mockito.when(repository.findDetailedReviewById(REVIEW_ID)).thenReturn(Optional.of(projection));
        Mockito.when(mapper.toDetailedDto(projection)).thenReturn(detailedReviewDto);

        //When
        DetailedReviewDto testedReview = service.saveReview(newReviewDto);

        //Test
        assertEquals(testedReview.comment(), COMMENT);
        assertEquals(testedReview.grade(), GRADE);
        assertEquals(testedReview.name(), NAME);
        assertEquals(testedReview.submissionTime(), SUBMISSION_TIME);
    }

    @Test
    void testSaveReviewThrowsObjectNotFoundExceptionWhenOnlyRestaurantIdExists() {
        // Given
        NewReviewDto newReviewDto = new NewReviewDto(COMMENT, GRADE, null, RESTAURANT_ID);

        // when
        Mockito.doThrow(ObjectNotFoundException.class).when(customerValidation).validateEntityById(any());

        // When
        assertThrows(ObjectNotFoundException.class, () -> service.saveReview(newReviewDto));
    }

    @Test
    void testSaveReviewThrowsObjectNotFoundExceptionWhenOnlyCustomerIdExists() {
        // Given
        NewReviewDto newReviewDto = new NewReviewDto(COMMENT, GRADE, CUSTOMER_ID, null);

        // when
        Mockito.doThrow(ObjectNotFoundException.class).when(restaurantValidation).validateEntityById(any());

        // When
        assertThrows(ObjectNotFoundException.class, () -> service.saveReview(newReviewDto));
    }
}