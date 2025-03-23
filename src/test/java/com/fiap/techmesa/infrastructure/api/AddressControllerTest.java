package com.fiap.techmesa.infrastructure.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.application.gateway.AddressGateway;
import com.fiap.techmesa.application.usecase.CreateAddress;
import com.fiap.techmesa.infrastructure.api.AddressController;

@SpringBootTest(classes = TechmesaApplication.class) 
@AutoConfigureMockMvc
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Mock
    private AddressGateway addressGateway;

    @Mock
    private CreateAddress createAddress;

    @InjectMocks
    private AddressController addressController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    }

    @Test
    void createAddressTest() throws Exception {
        final var request = validAddressRequest();
        final var response = validAddressRequest();

        when(addressGateway.save(any())).thenReturn(response);

        var result = mockMvc.perform(
                post("/techMesa/addresses")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();
        
        var status = result.getResponse().getStatus();
        assertEquals(201, status);
        
    }
    
    @Test
    void getAddressByIdTest() throws Exception {
        final var response = validAddressRequest();

        when(addressGateway.findById(1)).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/addresses/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status, "Expected HTTP status 404 but got " + status);

    }
    
    @Test
    void deleteAddressTest() throws Exception {
        
        doNothing().when(addressGateway).delete(1);

        var result = mockMvc.perform(
                delete("/techMesa/addresses/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(204, status, "Expected HTTP status 204 but got " + status);

    }
    
    @Test
    void updateAddressTest() throws Exception {
        final var response = validAddressRequest();
        response.setId(1);

        // Mock the AddressGateway to return a valid address when update is called with ID 1
        when(addressGateway.update(any(Address.class))).thenReturn(response);

        var result = mockMvc.perform(
                put("/techMesa/addresses/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(response)))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(200, status, "Expected HTTP status 200 but got " + status);

    }

    public static Address validAddressRequest() {
        return Address.builder()
                .id(1)
                .street("Avenida Interlagos")
                .number(501)
                .neighborhood("Vila Ariete")
                .city("São Paulo")
                .state("São Paulo")
                .country("Brasil")
                .cep("04821-270")
                .build();
    }
}