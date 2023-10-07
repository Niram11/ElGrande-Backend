package com.codecool.gastro.repository;

import com.codecool.gastro.repository.entity.Review;
import com.codecool.gastro.repository.projection.DetailedReviewProjection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    private ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    @Test
    void testFindById_ShouldReturnOneReview_WhenExist() {
        // given
        UUID reviewId = UUID.fromString("b4157a16-61c7-41fd-8c6a-f3c9e8e592cd");
        // when
        Optional<Review> actual = reviewRepository.findById(reviewId);
        // then
        assertThat(actual).isPresent();
    }

    @Test
    void testGetReviewsByRestaurant_ShouldReturnListOfReviewsAppliedToRestaurant_WhenCallingMethod() {
        // given
        UUID restaurantId = UUID.fromString("4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5");
        PageRequest pageable = PageRequest.of(0, 2, Sort.by("grade"));
        // when
        List<Review> actual = reviewRepository.getReviewsByRestaurant(restaurantId, pageable);
        // then
        assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    void testGetReviewsByCustomerId_ShouldReturnListOfReviewsAppliedToCustomer_WhenCallingMethod() {
        // given
        UUID customerId = UUID.fromString("3662bb1f-4804-4f37-b0ac-9417e4ec385b");
        // when
        List<Review> actual = reviewRepository.getReviewsByCustomerId(customerId);
        // then
        assertThat(actual.size()).isEqualTo(2);
    }

    @Test
    void testFindDetailedReviewsByRestaurantId_ShouldReturnListOfDetailedDataOfReviewAppliedToRestaurant_WhenCallingMethod() {
        // given
        UUID restaurantId = UUID.fromString("4e99a0c4-d1bb-48c6-95f3-d202b84d1dc5");
        // when
        List<DetailedReviewProjection> actual = reviewRepository.findDetailedReviewsByRestaurantId(restaurantId);
        // then
        DetailedReviewProjection projectionOne = factory.createProjection(DetailedReviewProjection.class);
        projectionOne.setComment("new comment");
        projectionOne.setGrade(6);
        projectionOne.setSubmissionTime(LocalDate.of(2023,10,7));
        projectionOne.setName("Tomek");

        DetailedReviewProjection projectionTwo = factory.createProjection(DetailedReviewProjection.class);
        projectionTwo.setComment("this igrwgwld comment");
        projectionTwo.setGrade(10);
        projectionTwo.setSubmissionTime(LocalDate.of(2022,5,7));
        projectionTwo.setName("Tomek");

        List<DetailedReviewProjection> expected = List.of(projectionOne, projectionTwo);
        assertThat(actual).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .comparingOnlyFields("comment")
                .isEqualTo(expected);
//        expected.get(0).equals(null);
    }
}
//assertThat(projectionOne).usingRecursiveComparison().isEqualTo(projectionTwo)
