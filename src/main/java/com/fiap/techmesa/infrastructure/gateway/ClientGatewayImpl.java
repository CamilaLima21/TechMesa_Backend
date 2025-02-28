//package com.fiap.techmesa.infrastructure.gateway;
//
//import java.util.List;
//import java.util.Optional;
//import org.springframework.stereotype.Component;
//import com.fiap.techmesa.application.domain.Client;
//import com.fiap.techmesa.application.domain.Reserve;
//import com.fiap.techmesa.application.gateway.ClientGateway;
//import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;
//import com.fiap.techmesa.infrastructure.persistence.entity.ReserveEntity;
//import com.fiap.techmesa.infrastructure.persistence.repository.ClientRepository;
//
//import lombok.RequiredArgsConstructor;
//
//@Component
//@RequiredArgsConstructor
//public class ClientGatewayImpl implements ClientGateway {
//	
//	private final ClientRepository clientRepository;
//
//    @Override
//    public Client save(final Client request) {
//       final var entity = 
//    	ClientEntity.builder()
//    		.name(request.getName())
//    		.email(request.getEmail())
//    		.registrationDate(request.getRegistrationDate())
//    		.address(request.getAddress())
//    		.reserve(List.of())
//    		.build();
//    	
//       final var saved = clientRepository.save(entity);
//    		
//    	return this.toResponse(saved);
//    }
//
//    @Override
//    public Optional<Client> findByPartName(final String partName) {
//        return clientRepository.findByNameContainsIgnoreCase(partName).map(this::toResponse);
//    }
//
//    @Override
//    public Optional<Client> findByName(String name) {
//        // Implementar a busca de cliente por nome
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Client> findById(int id) {
//        // Implementar a busca de cliente por ID
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<Client> findByEmail(String email) {
//        // Implementar a busca de cliente por email
//        return Optional.empty();
//    }
//
//    @Override
//    public Client update(Client client) {
//        // Implementar a atualização do cliente
//        return null;
//    }
//
//    @Override
//    public void delete(int id) {
//        // Implementar a exclusão do cliente
//    }
//    
//    private Client toResponse(final ClientEntity entity) {
//    	return new Client(
//    			entity.getId(),
//    			entity.getName(),
//    			entity.getEmail(),
//    			entity.getRegistrationDate(),
//    			entity.getAddress().getId(),
//    			this.toReserveDomain(entity.getReserve()));
//    }
//    
//    private List<Reserve> toReserveDomain(final List<ReserveEntity> reserveEntities) {
//		return reserveEntities.stream()
//				.map(
//					reserveEntity ->
//						new Reserve(
//							reserveEntity.getId(),
//							reserveEntity.getClient().getId(),
//							reserveEntity.getRestaurant().getId(),
//							reserveEntity.getTableRestaurant(),
//							reserveEntity.getNumberPeople(),
//							reserveEntity.getDateReserve(),
//							reserveEntity.getDateCreated(),
//							reserveEntity.getStartReserve(),
//							reserveEntity.getToleranceMinutes(),
//							reserveEntity.getTimeLimit(),
//							reserveEntity.getStatusReserve()))
//				.toList();
//    	
//		
//    }
//    
//}