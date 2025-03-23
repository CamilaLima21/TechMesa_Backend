package com.fiap.techmesa.infrastructure.gateway;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.infrastructure.persistence.entity.OpeningHoursEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.RestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.OpeningHoursRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.RestaurantRepository;

public class OpeningHoursGatewayImplTest {

    @Mock
    private OpeningHoursRepository openingHoursRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private OpeningHoursGatewayImpl openingHoursGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {

        OpeningHours openingHours = new OpeningHours();
        openingHours.setName("Test Opening Hours");
        openingHours.setRestaurantId(1);

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1);

        OpeningHoursEntity openingHoursEntity = new OpeningHoursEntity();
        openingHoursEntity.setName("Test Opening Hours");
        openingHoursEntity.setRestaurant(restaurantEntity);

        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurantEntity));
        when(openingHoursRepository.save(any(OpeningHoursEntity.class))).thenReturn(openingHoursEntity);

        OpeningHours result = openingHoursGateway.save(openingHours);

        assertNotNull(result);
        assertEquals("Test Opening Hours", result.getName());
    }

    @Test
    void testFindById() {

    	RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1);
    	
        OpeningHoursEntity openingHoursEntity = new OpeningHoursEntity();
        openingHoursEntity.setId(1);
        openingHoursEntity.setName("Test Opening Hours");
        openingHoursEntity.setRestaurant(restaurantEntity);

        when(openingHoursRepository.findById(1)).thenReturn(Optional.of(openingHoursEntity));

        Optional<OpeningHours> result = openingHoursGateway.findById(1);

        assertTrue(result.isPresent());
        assertEquals("Test Opening Hours", result.get().getName());
    }

    @Test
    void testFindAll() {
    	
    	RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1);

        OpeningHoursEntity openingHoursEntity = new OpeningHoursEntity();
        openingHoursEntity.setId(1);
        openingHoursEntity.setName("Test Opening Hours");
        openingHoursEntity.setRestaurant(restaurantEntity);

        when(openingHoursRepository.findAll()).thenReturn(List.of(openingHoursEntity));

        List<OpeningHours> result = openingHoursGateway.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Opening Hours", result.get(0).getName());
    }

    @Test
    void testUpdate() {
        // Arrange
        OpeningHours openingHours = new OpeningHours();
        openingHours.setId(1);
        openingHours.setName("Updated Opening Hours");
        openingHours.setRestaurantId(1);

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setId(1);

        OpeningHoursEntity openingHoursEntity = new OpeningHoursEntity();
        openingHoursEntity.setId(1);
        openingHoursEntity.setName("Updated Opening Hours");
        openingHoursEntity.setRestaurant(restaurantEntity);

        when(openingHoursRepository.findById(1)).thenReturn(Optional.of(openingHoursEntity));
        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurantEntity));
        when(openingHoursRepository.save(any(OpeningHoursEntity.class))).thenReturn(openingHoursEntity);

        OpeningHours result = openingHoursGateway.update(openingHours);

        assertNotNull(result);
        assertEquals("Updated Opening Hours", result.getName());
    }

    @Test
    void testDelete() {
        openingHoursGateway.delete(1);
        verify(openingHoursRepository, times(1)).deleteById(1);
    }
}