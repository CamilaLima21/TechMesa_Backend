package com.fiap.techmesa.infrastructure.gateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.techmesa.application.domain.Address;
import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.domain.Reserve;
import com.fiap.techmesa.application.domain.Restaurant;
import com.fiap.techmesa.application.domain.TableRestaurant;
import com.fiap.techmesa.application.domain.pagination.Page;
import com.fiap.techmesa.application.domain.pagination.Pagination;
import com.fiap.techmesa.application.enums.TypeKitchenEnum;
import com.fiap.techmesa.application.gateway.RestaurantGateway;
import com.fiap.techmesa.application.usecase.exception.RestaurantNotFoundException;
import com.fiap.techmesa.infrastructure.persistence.entity.AddressEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.OpeningHoursEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.ReserveEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.RestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.TableRestaurantEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RestaurantGatewayImpl implements RestaurantGateway {

	private final RestaurantRepository restaurantRepository;

	@Override
	public Restaurant save(final Restaurant restaurant) {
		RestaurantEntity restaurantEntity = mapToEntity(restaurant);
		RestaurantEntity savedRestaurantEntity = restaurantRepository.save(restaurantEntity);
		return mapToDomain(savedRestaurantEntity);
	}

	@Override
	public Pagination<Restaurant> findAll(Page page) {
		var pageRequest = PageRequest.of(page.page(), page.size());
		var restaurantPage = restaurantRepository.findAll(pageRequest);
		var restaurants = restaurantPage.getContent().stream().map(this::mapToDomain).collect(Collectors.toList());

		return new Pagination<>(restaurantPage.getNumber(), restaurantPage.getSize(), restaurantPage.getTotalPages(),
				restaurantPage.getTotalElements(), restaurantPage.getNumberOfElements(), restaurants);
	}

	@Override
	public Optional<Restaurant> findById(final int id) {
		return restaurantRepository.findById(id).map(this::mapToDomain);
	}

	@Override
	public Optional<Restaurant> findByTypeKitchen(final TypeKitchenEnum typeKitchen) {
		return restaurantRepository.findByTypeKitchen(typeKitchen).flatMap(list -> list.stream().findFirst())
				.map(this::mapToDomain);
	}

	@Override
	public Optional<Restaurant> findByPartName(final String partName) {
		return restaurantRepository.findByNameContainsIgnoreCase(partName).flatMap(list -> list.stream().findFirst())
				.map(this::mapToDomain);
	}

	@Override
	public Optional<Restaurant> findByName(final String name) {
		return Optional.ofNullable(restaurantRepository.findByName(name)).map(this::mapToDomain);
	}

	@Override
	public Optional<Restaurant> findByEmail(final String email) {
		return Optional.ofNullable(restaurantRepository.findByEmail(email)).map(this::mapToDomain);
	}

	@Override
	public Optional<Restaurant> findByCity(final String city) {
		return restaurantRepository.findByCity(city).flatMap(list -> list.stream().findFirst()).map(this::mapToDomain);
	}

	@Override
	public Optional<Restaurant> findByCityAndNeighborhood(final String city, final String neighborhood) {
		return restaurantRepository.findByCityAndNeighborhood(city, neighborhood)
				.flatMap(list -> list.stream().findFirst()).map(this::mapToDomain);
	}

	@Transactional
	@Override
	public void delete(final int id) {
		restaurantRepository.deleteById(id);
	}

	@Override
	public Restaurant update(final Restaurant restaurant) {
	    final var restaurantFound = restaurantRepository.findById(restaurant.getId())
	            .orElseThrow(() -> new RestaurantNotFoundException(restaurant.getId()));

	    final var restaurantEntity = RestaurantEntity.builder()
	            .id(restaurantFound.getId())
	            .name(restaurant.getName())
	            .address(AddressEntity.builder().id(restaurant.getAddressId()).build())
	            .email(restaurant.getEmail())
	            .typeKitchen(restaurant.getTypeKitchen())
	            .capacity(restaurant.getCapacity())
	            .statusRestaurant(restaurant.getStatusRestaurant())
	            .registrationDate(restaurant.getRegistrationDate())
	            .openingHours(restaurant.getOpeningHours().stream()
	                .map(this::mapToEntity)
	                .collect(Collectors.toList()))
	            .reserve(restaurant.getReserves().stream()
	                .map(this::mapToEntity)
	                .collect(Collectors.toList()))
	            .tableRestaurant(restaurant.getTableRestaurants().stream()
	                .map(this::mapToEntity)
	                .collect(Collectors.toList()))
	            .build();

	    final var updated = restaurantRepository.save(restaurantEntity);

	    return mapToDomain(updated);
	}


	private RestaurantEntity mapToEntity(final Restaurant restaurant) {
	    return RestaurantEntity.builder()
	        .id(restaurant.getId())
	        .name(restaurant.getName())
	        .address(AddressEntity.builder().id(restaurant.getAddressId()).build())
	        .email(restaurant.getEmail())
	        .typeKitchen(restaurant.getTypeKitchen())
	        .capacity(restaurant.getCapacity())
	        .statusRestaurant(restaurant.getStatusRestaurant())
	        .registrationDate(restaurant.getRegistrationDate())
	        .openingHours(restaurant.getOpeningHours().stream()
	            .map(this::mapToEntity)
	            .collect(Collectors.toList()))
	        .reserve(restaurant.getReserves().stream()
	            .map(this::mapToEntity)
	            .collect(Collectors.toList()))
	        .tableRestaurant(restaurant.getTableRestaurants().stream()
	            .map(this::mapToEntity)
	            .collect(Collectors.toList()))
	        .build();
	}

	private Restaurant mapToDomain(final RestaurantEntity entity) {
	    return Restaurant.builder()
	        .id(entity.getId())
	        .name(entity.getName())
	        .addressId(entity.getAddress().getId())
	        .email(entity.getEmail())
	        .openingHours(entity.getOpeningHours().stream()
	            .map(this::mapToDomain)
	            .collect(Collectors.toList()))
	        .reserves(entity.getReserve().stream()
	            .map(this::mapToDomain)
	            .collect(Collectors.toList()))
	        .tableRestaurants(entity.getTableRestaurant().stream()
	            .map(this::mapToDomain)
	            .collect(Collectors.toList()))
	        .typeKitchen(entity.getTypeKitchen())
	        .capacity(entity.getCapacity())
	        .statusRestaurant(entity.getStatusRestaurant())
	        .registrationDate(entity.getRegistrationDate())
	        .build();
	}


	private AddressEntity mapToEntity(final Address address) {
		return AddressEntity.builder().id(address.getId()).street(address.getStreet()).number(address.getNumber())
				.neighborhood(address.getNeighborhood()).city(address.getCity()).state(address.getState())
				.country(address.getCountry()).cep(address.getCep()).build();
	}

	private Address mapToDomain(final AddressEntity entity) {
		return Address.builder().id(entity.getId()).street(entity.getStreet()).number(entity.getNumber())
				.neighborhood(entity.getNeighborhood()).city(entity.getCity()).state(entity.getState())
				.country(entity.getCountry()).cep(entity.getCep()).build();
	}

	private OpeningHoursEntity mapToEntity(final OpeningHours openingHours) {
	    return OpeningHoursEntity.builder()
	        .id(openingHours.getId())
	        .restaurant(RestaurantEntity.builder().id(openingHours.getRestaurantId()).build())
	        .name(openingHours.getName())
	        .turn(openingHours.getTurn())
	        .dayWeek(openingHours.getDayWeek())
	        .startTime(openingHours.getStartTime())
	        .endTime(openingHours.getEndTime())
	        .build();
	}


	private OpeningHours mapToDomain(final OpeningHoursEntity entity) {
	    return OpeningHours.builder()
	        .id(entity.getId())
	        .restaurantId(entity.getRestaurant().getId())
	        .name(entity.getName())
	        .turn(entity.getTurn())
	        .dayWeek(entity.getDayWeek())
	        .startTime(entity.getStartTime())
	        .endTime(entity.getEndTime())
	        .build();
	}


	private ReserveEntity mapToEntity(final Reserve reserve) {
		return ReserveEntity.builder()
				.id(reserve.getId())
				.client(ClientEntity.builder().id(reserve.getClientId()).build())
				.restaurant(RestaurantEntity.builder().id(reserve.getRestaurantId()).build())
				.tableRestaurant(
						reserve.getTableRestaurants().stream().map(this::mapToEntity).collect(Collectors.toList()))
				.numberPeople(reserve.getNumberPeople())
				.dateReserve(reserve.getDateReserve())
				.dateCreated(reserve.getDateCreated())
				.startReserve(reserve.getStartReserve())
				.toleranceMinutes(reserve.getToleranceMinutes())
				.timeLimit(reserve.getTimeLimit())
				.statusReserve(reserve.getStatusReserve())
				.build();
	}

	private Reserve mapToDomain(final ReserveEntity entity) {
		return Reserve.builder()
				.id(entity.getId())
				.clientId(entity.getClient().getId())
				.restaurantId(entity.getRestaurant().getId())
				.tableRestaurants(
						entity.getTableRestaurant().stream().map(this::mapToDomain).collect(Collectors.toList()))
				.numberPeople(entity.getNumberPeople())
				.dateReserve(entity.getDateReserve())
				.dateCreated(entity.getDateCreated())
				.startReserve(entity.getStartReserve())
				.toleranceMinutes(entity.getToleranceMinutes())
				.timeLimit(entity.getTimeLimit())
				.statusReserve(entity.getStatusReserve())
				.build();
	}

	private TableRestaurantEntity mapToEntity(final TableRestaurant tableRestaurant) {
		return TableRestaurantEntity.builder()
				.id(tableRestaurant.getId())
				.tableIdentification(tableRestaurant.getTableIdentification())
				.restaurant(RestaurantEntity.builder().id(tableRestaurant.getRestaurantId()).build())
				.reserve(ReserveEntity.builder().id(tableRestaurant.getReserveId()).build())
				.numberSeats(tableRestaurant.getNumberSeats())
				.statusTableOccupation(tableRestaurant.getStatusTableOccupation())
				.tablePosition(tableRestaurant.getTablePosition()).build();
	}

	private TableRestaurant mapToDomain(final TableRestaurantEntity tableRestaurantEntity) {
	    return TableRestaurant.builder()
	        .id(tableRestaurantEntity.getId())
	        .tableIdentification(tableRestaurantEntity.getTableIdentification())
	        .restaurantId(tableRestaurantEntity.getRestaurant().getId())
	        .reserveId(tableRestaurantEntity.getReserve() != null ? tableRestaurantEntity.getReserve().getId() : null)
	        .numberSeats(tableRestaurantEntity.getNumberSeats())
	        .statusTableOccupation(tableRestaurantEntity.getStatusTableOccupation())
	        .tablePosition(tableRestaurantEntity.getTablePosition())
	        .build();
	}

}