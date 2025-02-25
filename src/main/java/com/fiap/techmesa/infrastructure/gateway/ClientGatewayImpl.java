package com.fiap.techmesa.infrastructure.gateway;

import java.util.Optional;
import org.springframework.stereotype.Component;
import com.fiap.techmesa.application.domain.Client;
import com.fiap.techmesa.application.gateway.ClientGateway;

@Component
public class ClientGatewayImpl implements ClientGateway {

    @Override
    public Client save(Client client) {
        // Implementar a persistência do cliente
        return null;
    }

    @Override
    public Optional<Client> findByPartName(String partName) {
        // Implementar a busca de cliente por parte do nome
        return Optional.empty();
    }

    @Override
    public Optional<Client> findByName(String name) {
        // Implementar a busca de cliente por nome
        return Optional.empty();
    }

    @Override
    public Optional<Client> findById(int id) {
        // Implementar a busca de cliente por ID
        return Optional.empty();
    }

    @Override
    public Optional<Client> findByEmail(String email) {
        // Implementar a busca de cliente por email
        return Optional.empty();
    }

    @Override
    public Client update(Client client) {
        // Implementar a atualização do cliente
        return null;
    }

    @Override
    public void delete(int id) {
        // Implementar a exclusão do cliente
    }
}