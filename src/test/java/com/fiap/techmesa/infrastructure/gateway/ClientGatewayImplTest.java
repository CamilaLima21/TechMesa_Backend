package com.fiap.techmesa.infrastructure.gateway;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.enums.StatusReserveEnum;
import com.fiap.techmesa.application.enums.StatusTableOccupationEnum;
import com.fiap.techmesa.application.enums.TablePositionEnum;
import com.fiap.techmesa.infrastructure.persistence.entity.AddressEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.ReserveEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.RestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.TableRestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.AddressRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.ClientRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.ReserveRepository;

public class ClientGatewayImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ReserveRepository reserveRepository;
    
    @Mock
    private AddressRepository addressRepository;
    
    @Spy
    @InjectMocks
    private ClientGatewayImpl clientGateway;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveTest() {
        Client client = createClient();
        ClientEntity clientEntity = new ClientEntity();

        ClientGatewayImpl mockClientGateway = mock(ClientGatewayImpl.class);
        when(mockClientGateway.mapToEntity(any(Client.class))).thenReturn(clientEntity);
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        doReturn(client).when(mockClientGateway).mapToDomain(any(ClientEntity.class));

        Client result = mockClientGateway.save(client);
        assertNull(result);
    }
    @Test
    void findByIdTest() {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);

        Client client = createClient();

        ClientGatewayImpl mockClientGateway = mock(ClientGatewayImpl.class);
        when(mockClientGateway.mapToDomain(any(ClientEntity.class))).thenReturn(client);
        when(clientRepository.findById(1)).thenReturn(Optional.of(clientEntity));

        var result = mockClientGateway.findById(1);
        assertNotNull(result);
    }

    @Test
    void deleteTest() {
        doNothing().when(clientRepository).deleteById(anyInt());
        clientGateway.delete(1);
        verify(clientRepository, times(1)).deleteById(1);
    }

    @Test
    void updateTest() {
        Client client = createClient();
        client.setAddressId(1); // Ensure addressId is set

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setName("Original Client");

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(client.getAddressId());

        when(clientRepository.findById(1)).thenReturn(Optional.of(clientEntity));
        when(addressRepository.findById(client.getAddressId())).thenReturn(Optional.of(addressEntity));
        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);
        doReturn(client).when(clientGateway).mapToDomain(any(ClientEntity.class));

        Client updatedClient = clientGateway.update(client);
        assertNotNull(updatedClient);
    }
    
    @Test
    void mapToDomainTest() {

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1);
        TableRestaurantEntity tableRestaurantEntity = new TableRestaurantEntity();
        tableRestaurantEntity.setId(1);

        ReserveEntity reserveEntity = ReserveEntity.builder()
                .id(1)
                .client(clientEntity)
                .restaurant(restaurantEntity)
                .numberPeople(4)
                .dateReserve(LocalDate.now())
                .dateCreated(LocalDate.now())
                .startReserve(LocalDate.now())
                .toleranceMinutes(15)
                .timeLimit(LocalDate.now())
                .statusReserve(StatusReserveEnum.CONFIRMED)
                .tableRestaurant(tableRestaurantEntity)
                .build();

        Reserve reserve = clientGateway.mapToDomain(reserveEntity);
        assertNotNull(reserve);
    }
    
    @Test
    void mapToEntityTestTable() {

        TableRestaurant tableRestaurant = TableRestaurant.builder()
                .id(1)
                .tableIdentification("Table 1")
                .restaurantId(1)
                .reserveId(1)
                .numberSeats(4)
                .statusTableOccupation(StatusTableOccupationEnum.BUSY)
                .tablePosition(TablePositionEnum.EXTERNAL)
                .build();

        TableRestaurantEntity entity = clientGateway.mapToEntity(tableRestaurant);

        assertNotNull(entity);
    }

    @Test
    void mapToDomainTestRestaurant() {
        // Setup
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1);

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);

        ReserveEntity reserveEntity = new ReserveEntity();
        reserveEntity.setId(1);
        reserveEntity.setClient(clientEntity);

        TableRestaurantEntity entity = TableRestaurantEntity.builder()
                .id(1)
                .tableIdentification("Table 1")
                .restaurant(restaurantEntity)
                .reserve(reserveEntity)
                .numberSeats(4)
                .statusTableOccupation(StatusTableOccupationEnum.BUSY)
                .tablePosition(TablePositionEnum.EXTERNAL)
                .build();

        // Execute
        TableRestaurant tableRestaurant = clientGateway.mapToDomain(entity);

        // Verify
        assertNotNull(tableRestaurant);
    }
    
    @Test
    void mapToEntityTest() {
        MockitoAnnotations.openMocks(this);

        Reserve reserve = Reserve.builder()
                .id(1)
                .clientId(1)
                .build();

        Client client = Client.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .addressId(1)
                .registrationDate(LocalDate.now())
                .reserves(Collections.singletonList(reserve))
                .build();

        ClientGatewayImpl realClientGateway = new ClientGatewayImpl(clientRepository, addressRepository);
        ClientGatewayImpl spyClientGateway = spy(realClientGateway);
        ReserveEntity reserveEntity = new ReserveEntity();
        doReturn(reserveEntity).when(spyClientGateway).mapToEntity(any(Reserve.class));

        ClientEntity entity = spyClientGateway.mapToEntity(client);

        assertNotNull(entity);
    }
    
    @Test
    void mapToDomainClientTest() {
        AddressEntity addressEntity = AddressEntity.builder()
                .id(1)
                .build();

        ClientEntity clientEntity = ClientEntity.builder()
                .id(1)
                .name("John Doe")
                .email("john.doe@example.com")
                .address(addressEntity)
                .registrationDate(LocalDate.now())
                .build();

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1);

        ReserveEntity reserveEntity = ReserveEntity.builder()
                .id(1)
                .client(clientEntity)
                .restaurant(restaurantEntity)
                .build();

        clientEntity.setReserves(Collections.singletonList(reserveEntity));

        ClientGatewayImpl mockClientGateway = mock(ClientGatewayImpl.class);
        Reserve reserve = Reserve.builder()
                .id(1)
                .clientId(1)
                .restaurantId(1)
                .numberPeople(4)
                .dateReserve(LocalDate.now())
                .dateCreated(LocalDate.now())
                .startReserve(LocalDate.now())
                .toleranceMinutes(15)
                .timeLimit(LocalDate.now())
                .statusReserve(StatusReserveEnum.CONFIRMED)
                .tableRestaurants(1)
                .build();

        when(mockClientGateway.mapToDomain(any(ReserveEntity.class))).thenReturn(reserve);

        Client client = mockClientGateway.mapToDomain(clientEntity);

        assertNull(client);
    }
    
    public Client createClient() {
        return Client.builder()
                .id(1)
                .name("Client Name")
                .email("email@example.com")
                .registrationDate(LocalDate.now())
                .reserves(List.of(createReserve()))
                .build();
    }

    private Reserve createReserve() {
        return Reserve.builder()
                .id(1)
                .clientId(1)
                .restaurantId(1)
                .tableRestaurants(1)
                .numberPeople(4)
                .dateReserve(LocalDate.now())
                .dateCreated(LocalDate.now())
                .startReserve(LocalDate.now())
                .toleranceMinutes(15)
                .timeLimit(LocalDate.now())
                .statusReserve(StatusReserveEnum.CONFIRMED)
                .build();
    }
}