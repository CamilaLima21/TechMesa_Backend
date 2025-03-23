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
import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.enums.StatusReserveEnum;
import com.fiap.techmesa.application.gateway.ReserveGateway;

@SpringBootTest(classes = TechmesaApplication.class)
@AutoConfigureMockMvc
public class ReserveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ReserveGateway reserveGateway;

    @InjectMocks
    private ReserveController reserveController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(reserveController).build();
    }

    @Test
    void createReserveTest() throws Exception {
        final var request = validReserveRequest();
        final var response = validReserveRequest();

        when(reserveGateway.save(any())).thenReturn(response);

        var result = mockMvc.perform(
                post("/techMesa/reserves")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    void getReserveByIdTest() throws Exception {
        final var response = validReserveRequest();

        when(reserveGateway.findById(1)).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/reserves/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void updateReserveTest() throws Exception {
        final var response = validReserveRequest();
        response.setId(1);

        when(reserveGateway.update(any(Reserve.class))).thenReturn(response);

        var result = mockMvc.perform(
                put("/techMesa/reserves/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(response)))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void deleteReserveTest() throws Exception {
        doNothing().when(reserveGateway).delete(1);

        var result = mockMvc.perform(
                delete("/techMesa/reserves/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(204, status);
    }
    
    public static Reserve validReserveRequest() {
        return Reserve.createReserve(
            1, // clientId
            1, // restaurantId
            1, // tableRestaurants
            2, // numberPeople
            LocalDate.now(), // dateReserve
            LocalDate.now(), // dateCreated
            LocalDate.now(), // startReserve
            15, // toleranceMinutes
            LocalDate.now().plusDays(1), // timeLimit
            StatusReserveEnum.CONFIRMED // statusReserve
        );
    }
    
}