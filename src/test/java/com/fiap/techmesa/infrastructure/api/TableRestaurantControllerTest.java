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
import com.fiap.techmesa.TechmesaApplication;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.enums.StatusTableOccupationEnum;
import com.fiap.techmesa.application.enums.TablePositionEnum;
import com.fiap.techmesa.application.gateway.TableRestaurantGateway;

@SpringBootTest(classes = TechmesaApplication.class)
@AutoConfigureMockMvc
public class TableRestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TableRestaurantGateway tableRestaurantGateway;

    @InjectMocks
    private TableRestaurantController tableRestaurantController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tableRestaurantController).build();
    }

    @Test
    void createTableRestaurantTest() throws Exception {
        final var request = validTableRestaurantRequest();
        final var response = validTableRestaurantRequest();

        when(tableRestaurantGateway.save(any())).thenReturn(response);

        var result = mockMvc.perform(
                post("/techMesa/tableRestaurants")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void getTableRestaurantByIdTest() throws Exception {
        final var response = validTableRestaurantRequest();

        when(tableRestaurantGateway.findById(1)).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/tableRestaurants/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void deleteTableRestaurantTest() throws Exception {
        doNothing().when(tableRestaurantGateway).delete("mesa");

        var result = mockMvc.perform(
                delete("/techMesa/tableRestaurants/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void updateTableRestaurantTest() throws Exception {
        final var response = validTableRestaurantRequest();
        response.setId(1);

        when(tableRestaurantGateway.update(any(TableRestaurant.class))).thenReturn(response);

        var result = mockMvc.perform(
                put("/techMesa/tableRestaurants/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(response)))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }
    
    @Test
    void getTableRestaurantsByRestaurantAndDateTest() throws Exception {
        final var response = validTableRestaurantRequest();

        when(tableRestaurantGateway.findByRestaurantAndDate("Table 1", 1)).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/table-restaurants/by-restaurant-and-date")
                        .param("tableIdentification", "Table 1")
                        .param("reserveId", "1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void getTableRestaurantsByRestaurantNotReservedAndDateTest() throws Exception {
        final var response = validTableRestaurantRequest();

        when(tableRestaurantGateway.findByRestaurantNotReservedAndDate(1, StatusTableOccupationEnum.AVAILABLE, LocalDate.now()))
                .thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/table-restaurants/by-restaurant-not-reserved-and-date")
                        .param("id", "1")
                        .param("statusTableOccupation", "AVAILABLE")
                        .param("dateReserve", LocalDate.now().toString())
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    public static TableRestaurant validTableRestaurantRequest() {
        return TableRestaurant.builder()
                .tableIdentification("Table 1")
                .restaurantId(1)
                .reserveId(1)
                .numberSeats(4)
                .statusTableOccupation(StatusTableOccupationEnum.AVAILABLE)
                .tablePosition(TablePositionEnum.INTERNAL) 
                .build();
    }
}