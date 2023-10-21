package com.codecool.gastro.controller;

import com.codecool.gastro.dto.promotedlocal.NewPromotedLocalDto;
import com.codecool.gastro.dto.promotedlocal.PromotedLocalDto;
import com.codecool.gastro.service.PromotedLocalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PromotedLocalController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PromotedLocalControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private PromotedLocalController promotedLocalController;

    @MockBean
    private PromotedLocalService promotedLocalService;
    private final static LocalTime START_TIME = LocalTime.of(10, 0);
    private final static LocalTime END_TIME = LocalTime.of(18, 0);

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(promotedLocalController).build();
    }

    @Test
    void testGetAllPromotedLocalsShouldReturnStatusOkAndListOfPromotedLocalDto() throws Exception {
        // given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        LocalTime now = LocalTime.now();

        List<PromotedLocalDto> promotedLocals = List.of(
                new PromotedLocalDto(id1, now, now.plusHours(1)),
                new PromotedLocalDto(id2, now, now.plusHours(2))
        );

        Mockito.when(promotedLocalService.getPromotedLocals()).thenReturn(promotedLocals);

        // when-then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/promoted-locals"))
                .andExpect(status().isOk())
                .andExpectAll(status().isOk());
    }

//    @Test
//    void testCreateNewPromotedLocalShouldReturnStatusCreatedAndPromotedLocalDto() throws Exception {
//        // given
//        UUID promotedLocalId = UUID.randomUUID();
//        LocalTime startTime = LocalTime.of(12, 0,0);
//        LocalTime endTime = LocalTime.of(14, 0,0);
//        PromotedLocalDto promotedLocalDto = new PromotedLocalDto(promotedLocalId, START_TIME, END_TIME);
//
//        String contentRequest = """
//        {
//            "startDate": "12:00",
//            "endDate": "14:00",
//            "restaurantId": "7b571224-8506-4947-a975-bc1fa0d5b743"
//        }
//        """;
//
//        Mockito.when(promotedLocalService.saveNewPromotedLocal(Mockito.any(NewPromotedLocalDto.class))).thenReturn(promotedLocalDto);
//
//        // when-then
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/promoted-locals")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(contentRequest))
//                .andExpect(status().isCreated());
//    }

//    @Test
//    void testUpdatePromotedLocalShouldReturnUpdatedPromotedLocal() throws Exception {
//        UUID idPromotedLocal = UUID.fromString("7b571224-8506-4947-a975-bc1fa0d5b743");
//        LocalTime startTime = LocalTime.of(12, 0, 0);
//        LocalTime endTime = LocalTime.of(14, 0, 0);
//        PromotedLocalDto promotedLocal = new PromotedLocalDto(idPromotedLocal, startTime, endTime);
//        NewPromotedLocalDto updatedPromotedLocalDto = new NewPromotedLocalDto(startTime, endTime, UUID.fromString("7b571224-8506-4947-a975-bc1fa0d5b743"));
//
//        Mockito.when(promotedLocalService.updatePromotedLocal(idPromotedLocal, updatedPromotedLocalDto))
//                .thenReturn(promotedLocal);
//
//        String requestContent = """
//        {
//            "startDate": "12:00",
//            "endDate": "14:00",
//            "restaurantId": "7b571224-8506-4947-a975-bc1fa0d5b743"
//        }
//        """;
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/promoted-locals/{id}", idPromotedLocal)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestContent))
//                .andExpect(status().isCreated());
//    }

    @Test
    void testDeletePromotedLocalShouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(promotedLocalService).deletePromotedLocal(UUID.fromString("7b571224-8506-4947-a975-bc1fa0d5b743"));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/promoted-locals/{id}", "7b571224-8506-4947-a975-bc1fa0d5b743"))
                .andExpect(status().isOk());
    }

}
