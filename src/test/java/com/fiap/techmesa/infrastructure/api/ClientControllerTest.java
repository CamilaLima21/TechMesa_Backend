package com.fiap.techmesa.infrastructure.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

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
import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.gateway.ClientGateway;
import com.fiap.techmesa.application.usecase.CreateClient;

@SpringBootTest(classes = TechmesaApplication.class)
@AutoConfigureMockMvc
public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ClientGateway clientGateway;

    @Mock
    private CreateClient createClient;

    @InjectMocks
    private ClientController clientController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void createClientTest() throws Exception {
        final var request = validClientRequest();
        final var response = validClientRequest();

        when(clientGateway.save(any())).thenReturn(response);

        var result = mockMvc.perform(
                post("/techMesa/clients")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(201, status);
    }

    @Test
    void getClientByIdTest() throws Exception {
        final var response = validClientRequest();

        when(clientGateway.findById(1)).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/clients/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status, "Expected HTTP status 404 but got " + status);
    }

    @Test
    void deleteClientTest() throws Exception {
        doNothing().when(clientGateway).delete(1);

        var result = mockMvc.perform(
                delete("/techMesa/clients/1")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(204, status, "Expected HTTP status 204 but got " + status);
    }

    @Test
    void updateClientTest() throws Exception {
        final var response = validClientRequest();
        response.setId(1);

        when(clientGateway.update(any(Client.class))).thenReturn(response);

        var result = mockMvc.perform(
                put("/techMesa/clients/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(response)))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(200, status, "Expected HTTP status 200 but got " + status);

    }
    
    @Test
    void getClientByPartNameTest() throws Exception {
        final var response = validClientRequest();

        when(clientGateway.findByPartName("John")).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/clients/by-part-name")
                        .param("partName", "John")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status, "Expected HTTP status 404 but got " + status);
    }

    @Test
    void getClientByNameTest() throws Exception {
        final var response = validClientRequest();

        when(clientGateway.findByName("John Doe")).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/clients/by-name")
                        .param("name", "John Doe")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status, "Expected HTTP status 404 but got " + status);

    }

    @Test
    void getClientByEmailTest() throws Exception {
        final var response = validClientRequest();

        when(clientGateway.findByEmail("john.doe@example.com")).thenReturn(Optional.of(response));

        var result = mockMvc.perform(
                get("/techMesa/clients/by-email")
                        .param("email", "john.doe@example.com")
                        .contentType("application/json"))
                .andReturn();

        var status = result.getResponse().getStatus();
        assertEquals(404, status, "Expected HTTP status 404 but got " + status);

    }

    public static Client validClientRequest() {
        return Client.builder()
                .id(1)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();
    }
}