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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import com.fiap.techmesa.TechmesaApplication;
import com.fiap.techmesa.application.domain.Rating;
import com.fiap.techmesa.application.gateway.RatingGateway;
import com.fiap.techmesa.infrastructure.api.RatingController;

@SpringBootTest(classes = TechmesaApplication.class)
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RatingGateway ratingGateway;

    @InjectMocks
    private RatingController ratingController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void createRatingTest() throws Exception {
        final var request = validRatingRequest();
        final var response = validRatingRequest();

        when(ratingGateway.save(any())).thenReturn(response);

        var result = mockMvc.perform(
                post("/techMesa/ratings")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    void getRatingByIdTest() throws Exception {
        final var response = validRatingRequest();

        when(ratingGateway.findById(1)).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/ratings/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void updateRatingTest() throws Exception {
        final var response = validRatingRequest();
        response.setId(1);

        when(ratingGateway.update(any(Rating.class))).thenReturn(response);

        var result = mockMvc.perform(
                put("/techMesa/ratings/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(response)))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    void deleteRatingTest() throws Exception {
        doNothing().when(ratingGateway).delete(1);

        var result = mockMvc.perform(
                delete("/techMesa/ratings/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(204, status);
    }

    @Test
    void getAllRatingsTest() throws Exception {
        final var response = Collections.singletonList(validRatingRequest());

        when(ratingGateway.findAll()).thenReturn(response);

        var result = mockMvc.perform(
                get("/techMesa/ratings")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(200, status);
    }

    public static Rating validRatingRequest() {
        return createRating(1, "Excellent!", "Excellent service!", 5, LocalDate.now());
    }

    public static Rating createRating(
        final Integer clientId,
        final String title,
        final String text,
        final int note,
        final LocalDate dateRegistration) {

        return new Rating(null, clientId, title, text, note, dateRegistration);
    }
}