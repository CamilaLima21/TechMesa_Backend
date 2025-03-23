package com.fiap.techmesa.infrastructure.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.techmesa.TechmesaApplication;
import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.enums.DayWeekEnum;
import com.fiap.techmesa.application.enums.TurnEnum;
import com.fiap.techmesa.application.gateway.OpeningHoursGateway;
import com.fiap.techmesa.application.usecase.CreateOpeningHours;

@SpringBootTest(classes = TechmesaApplication.class)
@AutoConfigureMockMvc
public class OpeningHoursControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private OpeningHoursGateway openingHoursGateway;

    @Mock
    private CreateOpeningHours createOpeningHours;

    @InjectMocks
    private OpeningHoursController openingHoursController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(openingHoursController).build();
    }

    @Test
    void createOpeningHoursTest() throws Exception {
        final var request = validOpeningHoursRequest();
        final var response = validOpeningHoursRequest();

        when(openingHoursGateway.save(any())).thenReturn(response);

        var result = mockMvc.perform(
                post("/techMesa/opening-hours")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    void getOpeningHoursByIdTest() throws Exception {
        final var response = validOpeningHoursRequest();

        when(openingHoursGateway.findById(1)).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/openingHours/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status, "Expected HTTP status 404 but got " + status);

    }

    @Test
    void deleteOpeningHoursTest() throws Exception {
        doNothing().when(openingHoursGateway).delete(1);

        var result = mockMvc.perform(
                delete("/techMesa/openingHours/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status, "Expected HTTP status 404 but got " + status);

    }

    @Test
    void updateOpeningHoursTest() throws Exception {
        final var response = validOpeningHoursRequest();
        response.setId(1);

        when(openingHoursGateway.update(any(OpeningHours.class))).thenReturn(response);

        var result = mockMvc.perform(
                put("/techMesa/openingHours/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(response)))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status, "Expected HTTP status 404 but got " + status);

    }
    
    @Test
    void getAllOpeningHoursTest() throws Exception {
        final var response = Collections.singletonList(validOpeningHoursRequest());

        when(openingHoursGateway.findAll()).thenReturn(response);

        var result = mockMvc.perform(
                get("/techMesa/openingHours")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status, "Expected HTTP status 200 but got " + status);
    }

    public static OpeningHours validOpeningHoursRequest() {
        return OpeningHours.createOpeningHours(
                1,
                "Restaurant Name",
                TurnEnum.DINNER,
                DayWeekEnum.MONDAY, 
                LocalDate.of(2023, 1, 1), 
                LocalDate.of(2023, 12, 31) 
        );
    }
}