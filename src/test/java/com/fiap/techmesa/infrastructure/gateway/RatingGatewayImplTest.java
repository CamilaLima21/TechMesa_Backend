package com.fiap.techmesa.infrastructure.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fiap.techmesa.application.domain.Rating;
import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.RatingEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.ClientRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.RatingRepository;

public class RatingGatewayImplTest {

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private RatingGatewayImpl ratingGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {

        Rating rating = new Rating();
        rating.setClientId(1);
        rating.setTitle("Test Title");
        rating.setText("Test Text");
        rating.setNote(5);
        rating.setDateRegistration(LocalDate.now());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);

        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setClient(clientEntity);
        ratingEntity.setTitle("Test Title");
        ratingEntity.setText("Test Text");
        ratingEntity.setNote(5);
        ratingEntity.setDateRegistration(LocalDate.now());

        when(clientRepository.findById(1)).thenReturn(Optional.of(clientEntity));
        when(ratingRepository.save(any(RatingEntity.class))).thenReturn(ratingEntity);

        Rating result = ratingGateway.save(rating);

        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
    }

    @Test
    void testFindById() {

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);

        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setId(1);
        ratingEntity.setClient(clientEntity);
        ratingEntity.setTitle("Test Title");

        when(ratingRepository.findById(1)).thenReturn(Optional.of(ratingEntity));

        Optional<Rating> result = ratingGateway.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Test Title", result.get().getTitle());
    }

    @Test
    void testFindAll() {

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);

        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setId(1);
        ratingEntity.setClient(clientEntity);
        ratingEntity.setTitle("Test Title");

        when(ratingRepository.findAll()).thenReturn(List.of(ratingEntity));

        List<Rating> result = ratingGateway.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Title", result.get(0).getTitle());
    }

    @Test
    void testUpdate() {
        
        Rating rating = new Rating();
        rating.setId(1);
        rating.setTitle("Updated Title");
        rating.setText("Updated Text");
        rating.setNote(4);
        rating.setDateRegistration(LocalDate.now());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);

        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setId(1);
        ratingEntity.setClient(clientEntity);
        ratingEntity.setTitle("Updated Title");
        ratingEntity.setText("Updated Text");
        ratingEntity.setNote(4);
        ratingEntity.setDateRegistration(LocalDate.now());

        when(ratingRepository.findById(1)).thenReturn(Optional.of(ratingEntity));
        when(ratingRepository.save(any(RatingEntity.class))).thenReturn(ratingEntity);

        Rating result = ratingGateway.update(rating);

        assertNotNull(result);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void testDelete() {
        ratingGateway.delete(1);
        verify(ratingRepository, times(1)).deleteById(1);
    }
}