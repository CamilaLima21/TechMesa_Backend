package com.fiap.techmesa.infrastructure.gateway;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.techmesa.application.domain.Rating;
import com.fiap.techmesa.application.gateway.RatingGateway;
import com.fiap.techmesa.application.usecase.exception.RatingNotFoundException;
import com.fiap.techmesa.infrastructure.persistence.entity.ClientEntity;
import com.fiap.techmesa.infrastructure.persistence.entity.RatingEntity;
import com.fiap.techmesa.infrastructure.persistence.repository.ClientRepository;
import com.fiap.techmesa.infrastructure.persistence.repository.RatingRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RatingGatewayImpl implements RatingGateway {

    private final RatingRepository ratingRepository;
    private final ClientRepository clientRepository;

    @Override
    public Rating save(final Rating rating) {
        final Optional<ClientEntity> clientEntityFound =
            clientRepository.findById(rating.getClientId());

        if (clientEntityFound.isEmpty()) {
            throw new IllegalArgumentException(
                String.format("Client with id [%s] not found", rating.getClientId()));
        }

        final var clientEntity = clientEntityFound.get();

        final var ratingEntity =
            RatingEntity.builder()
                .client(clientEntity)
                .title(rating.getTitle())
                .text(rating.getText())
                .note(rating.getNote())
                .dateRegistration(rating.getDateRegistration())
                .build();

        final var saved = ratingRepository.save(ratingEntity);

        return mapToDomain(saved);
    }

    @Override
    public Optional<Rating> findById(final int id) {
        return ratingRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public List<Rating> findAll() {
        return ratingRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Rating update(final Rating rating) {
        final var ratingFound =
            ratingRepository.findById(rating.getId())
                .orElseThrow(() -> new RatingNotFoundException(rating.getId()));

        final var ratingEntity =
            RatingEntity.builder()
                .id(ratingFound.getId())
                .client(ratingFound.getClient())
                .title(rating.getTitle())
                .text(rating.getText())
                .note(rating.getNote())
                .dateRegistration(rating.getDateRegistration())
                .build();

        final var updated = ratingRepository.save(ratingEntity);

        return mapToDomain(updated);
    }

    @Transactional
    @Override
    public void delete(final int id) {
        ratingRepository.deleteById(id);
    }

    private Rating mapToDomain(final RatingEntity entity) {
        return Rating.builder()
            .id(entity.getId())
            .clientId(entity.getClient().getId())
            .title(entity.getTitle())
            .text(entity.getText())
            .note(entity.getNote())
            .dateRegistration(entity.getDateRegistration())
            .build();
    }
}
