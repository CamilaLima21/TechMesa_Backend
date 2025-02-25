package com.fiap.techmesa.infrastructure.gateway;

import java.util.Optional;
import org.springframework.stereotype.Component;
import com.fiap.techmesa.application.domain.Rating;
import com.fiap.techmesa.application.gateway.RatingGateway;

@Component
public class RatingGatewayImpl implements RatingGateway {

    @Override
    public Rating save(Rating rating) {
        // Implementar a persistência da avaliação
        return null;
    }

    @Override
    public Optional<Rating> findById(int id) {
        // Implementar a busca de avaliação por ID
        return Optional.empty();
    }

    @Override
    public Rating update(Rating rating) {
        // Implementar a atualização da avaliação
        return null;
    }

    @Override
    public void delete(int id) {
        // Implementar a exclusão da avaliação
    }
}