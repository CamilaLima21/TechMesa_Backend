package com.fiap.techmesa.infrastructure.gateway;

import java.util.Optional;
import org.springframework.stereotype.Component;
import com.fiap.techmesa.application.domain.OpeningHours;
import com.fiap.techmesa.application.gateway.OpeningHoursGateway;

@Component
public class OpeningHoursGatewayImpl implements OpeningHoursGateway {

    @Override
    public OpeningHours save(OpeningHours openingHours) {
        // Implementar a persistência do horário de funcionamento
        return null;
    }

    @Override
    public Optional<OpeningHours> findById(int id) {
        // Implementar a busca de horário de funcionamento por ID
        return Optional.empty();
    }

    @Override
    public OpeningHours update(OpeningHours openingHours) {
        // Implementar a atualização do horário de funcionamento
        return null;
    }

    @Override
    public void delete(int id) {
        // Implementar a exclusão do horário de funcionamento
    }
}