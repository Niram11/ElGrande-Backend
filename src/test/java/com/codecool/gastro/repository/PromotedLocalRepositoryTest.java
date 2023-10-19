package com.codecool.gastro.repository;
import com.codecool.gastro.repository.entity.PromotedLocal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
class PromotedLocalRepositoryTest {

    @Autowired
    private PromotedLocalRepository promotedLocalRepository;
    private final static LocalTime START_TIME = LocalTime.of(10, 0);
    private final static LocalTime END_TIME = LocalTime.of(18, 0);


//    @Test
//    void testFindAll_ShouldReturnAllPromotedLocals_WhenExist() {
//        // Given
//        PromotedLocal promotedLocal1 = new PromotedLocal();
//        promotedLocal1.setStartDate(LocalTime.of(8, 0));
//        promotedLocal1.setEndDate(LocalTime.of(12, 0));
//        promotedLocalRepository.save(promotedLocal1);
//
//        PromotedLocal promotedLocal2 = new PromotedLocal();
//        promotedLocal2.setStartDate(LocalTime.of(13, 0));
//        promotedLocal2.setEndDate(LocalTime.of(17, 0));
//        promotedLocalRepository.save(promotedLocal2);
//
//        // When
//        List<PromotedLocal> promotedLocals = promotedLocalRepository.findAll();
//
//        // Then
//        assertThat(promotedLocals).isNotEmpty().hasSize(2).contains(promotedLocal1, promotedLocal2);
//    }
}