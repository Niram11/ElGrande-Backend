package com.codecool.gastro.service;

import com.codecool.gastro.dto.review.NewReviewDto;
import com.codecool.gastro.dto.review.ReviewDto;
import com.codecool.gastro.repository.CustomerRepository;
import com.codecool.gastro.repository.RestaurantRepository;
import com.codecool.gastro.repository.ReviewRepository;
import com.codecool.gastro.repository.entity.Customer;
import com.codecool.gastro.repository.entity.Restaurant;
import com.codecool.gastro.repository.entity.Review;
import com.codecool.gastro.service.exception.ObjectNotFoundException;
import com.codecool.gastro.service.mapper.ReviewMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    //TODO: change tests names

    @InjectMocks
    private ReviewService service;
    @Mock
    private ReviewMapper mapper;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ReviewRepository repository;

    private final static String COMMENT = "comment";
    private final static int GRADE = 6;
    private final static UUID REVIEW_ID = UUID.randomUUID();
    private final static UUID CUSTOMER_ID = UUID.randomUUID();
    private final static UUID RESTAURANT_ID = UUID.randomUUID();
    private final static LocalDate LOCAL_DATE = LocalDate.of(2023, 9, 24);
    public static final Pageable PAGEABLE = PageRequest.of(0, 100000);

    @Test
    void getReviewBy() {
        //Given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);

        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);

        Review review = new Review();
        review.setId(REVIEW_ID);
        review.setCustomer(customer);
        review.setRestaurant(restaurant);
        review.setSubmissionTime(LOCAL_DATE);
        review.setComment(COMMENT);
        review.setGrade(GRADE);

        ReviewDto reviewDto = new ReviewDto(REVIEW_ID, COMMENT, GRADE, LOCAL_DATE);


        //When
        Mockito.when(repository.findById(REVIEW_ID)).thenReturn(Optional.of(review));
        Mockito.when(mapper.toDto(review)).thenReturn(reviewDto);
        ReviewDto testedReview = service.getReviewById(REVIEW_ID);

        //Then
        assertEquals(testedReview, reviewDto);
    }

    @Test
    void testGetReviewsByShouldThrowObjectNotFoundExceptionWhenReviewNotExists() {
        assertThrows(ObjectNotFoundException.class, () -> service.getReviewById(REVIEW_ID));
    }

    @Test
    void getReviewsByRestaurant() {
        //Given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);

        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);

        Review review = new Review();
        review.setId(REVIEW_ID);
        review.setCustomer(customer);
        review.setRestaurant(restaurant);
        review.setSubmissionTime(LOCAL_DATE);
        review.setComment(COMMENT);
        review.setGrade(GRADE);

        ReviewDto reviewDto = new ReviewDto(REVIEW_ID, COMMENT, GRADE, LOCAL_DATE);

        //When
        Mockito.when(repository.getReviewsByRestaurant(RESTAURANT_ID, PAGEABLE)).thenReturn(List.of(review));
        Mockito.when(mapper.toDto(review)).thenReturn(reviewDto);
        List<ReviewDto> reviews = service.getReviewsByRestaurant(RESTAURANT_ID, PAGEABLE);

        //Test
        assertEquals(List.of(reviewDto), reviews);

    }

    @Test
    void saveReview() {
        //Given
        Restaurant restaurant = new Restaurant();
        restaurant.setId(RESTAURANT_ID);

        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);

        Review review = new Review();
        review.setId(REVIEW_ID);
        review.setCustomer(customer);
        review.setRestaurant(restaurant);
        review.setSubmissionTime(LOCAL_DATE);
        review.setComment(COMMENT);
        review.setGrade(GRADE);

        NewReviewDto newReviewDto = new NewReviewDto(COMMENT, GRADE, CUSTOMER_ID, RESTAURANT_ID);
        ReviewDto reviewDto = new ReviewDto(REVIEW_ID, COMMENT, GRADE, LOCAL_DATE);

        //Mocking interactions
        //TODO:inject validation
        Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(Optional.of(restaurant));
        Mockito.when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        Mockito.when(repository.save(Mockito.any())).thenReturn(review);
        Mockito.when(mapper.dtoToReview(newReviewDto)).thenReturn(review);
        Mockito.when(mapper.toDto(review)).thenReturn(reviewDto);

        //When
        ReviewDto testedReview = service.saveReview(newReviewDto);

        //Test
        assertEquals(reviewDto, testedReview);
    }

    //TODO: improve tests to catch errors
    @Test
    void saveReviewReturnsNullWhenNeitherRestaurantIdNorCustomerIdExists() {
        // Given
        NewReviewDto newReviewDto = new NewReviewDto(COMMENT, GRADE, null, null);

        // When
        ReviewDto result = service.saveReview(newReviewDto);

        // Then
        assertNull(result);
    }

    @Test
    void saveReviewReturnsNullWhenOnlyRestaurantIdExists() {
        // Given
        NewReviewDto newReviewDto = new NewReviewDto(COMMENT, GRADE, null, RESTAURANT_ID);

        // When
        ReviewDto result = service.saveReview(newReviewDto);

        // Then
        assertNull(result);
    }

    @Test
    void saveReviewReturnsNullWhenOnlyCustomerIdExists() {
        // Given
        NewReviewDto newReviewDto = new NewReviewDto(COMMENT, GRADE, CUSTOMER_ID, null);

        // When
        ReviewDto result = service.saveReview(newReviewDto);

        // Then
        assertEquals(result, null);
    }
}