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
import java.util.List;
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
import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.enums.StatusRestaurantEnum;
import com.fiap.techmesa.application.enums.TypeKitchenEnum;
import com.fiap.techmesa.application.gateway.RestaurantGateway;

@SpringBootTest(classes = TechmesaApplication.class)
@AutoConfigureMockMvc
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private RestaurantController restaurantController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    }

    @Test
    void createRestaurantTest() throws Exception {
        final var request = validRestaurantRequest();
        final var response = validRestaurantRequest();

        when(restaurantGateway.save(any())).thenReturn(response);

        var result = mockMvc.perform(
                post("/techMesa/restaurants")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    void getRestaurantByIdTest() throws Exception {
        final var response = validRestaurantRequest();

        when(restaurantGateway.findById(1)).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/restaurants/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void deleteRestaurantTest() throws Exception {
        doNothing().when(restaurantGateway).delete(1);

        var result = mockMvc.perform(
                delete("/techMesa/restaurants/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(204, status);
    }

    @Test
    void updateRestaurantTest() throws Exception {
        final var response = validRestaurantRequest();
        response.setId(1);

        when(restaurantGateway.update(any(Restaurant.class))).thenReturn(response);

        var result = mockMvc.perform(
                put("/techMesa/restaurants/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(response)))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(200, status);
    }
    
    @Test
    void getRestaurantByTypeKitchenTest() throws Exception {
        final var response = validRestaurantRequest();

        when(restaurantGateway.findByTypeKitchen(TypeKitchenEnum.ITALIAN)).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/restaurants/by-type-kitchen")
                        .param("typeKitchen", "ITALIAN")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void getRestaurantByPartNameTest() throws Exception {
        final var response = validRestaurantRequest();

        when(restaurantGateway.findByPartName("Valid")).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/restaurants/by-part-name")
                        .param("partName", "Valid")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void getRestaurantByNameTest() throws Exception {
        final var response = validRestaurantRequest();

        when(restaurantGateway.findByName("Valid Restaurant Name")).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/restaurants/by-name")
                        .param("name", "Valid Restaurant Name")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void getRestaurantByEmailTest() throws Exception {
        final var response = validRestaurantRequest();

        when(restaurantGateway.findByEmail("valid.email@example.com")).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/restaurants/by-email")
                        .param("email", "valid.email@example.com")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void getRestaurantByCityTest() throws Exception {
        final var response = validRestaurantRequest();

        when(restaurantGateway.findByCity("Valid City")).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/restaurants/by-city")
                        .param("city", "Valid City")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void getRestaurantByCityAndNeighborhoodTest() throws Exception {
        final var response = validRestaurantRequest();

        when(restaurantGateway.findByCityAndNeighborhood("Valid City", "Valid Neighborhood")).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/restaurants/by-city-and-neighborhood")
                        .param("city", "Valid City")
                        .param("neighborhood", "Valid Neighborhood")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status);
    }

    public static Restaurant validRestaurantRequest() {
        return Restaurant.builder()
                .name("Valid Restaurant Name")
                .addressId(1)
                .email("valid.email@example.com")
                .openingHours(List.of()) 
                .reserves(List.of()) 
                .tableRestaurants(List.of()) 
                .typeKitchen(TypeKitchenEnum.ITALIAN) 
                .capacity(100)
                .statusRestaurant(StatusRestaurantEnum.ACTIVE) 
                .registrationDate(LocalDate.now())
                .build();
    }
}