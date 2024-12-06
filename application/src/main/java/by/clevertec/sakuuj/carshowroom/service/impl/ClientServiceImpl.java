package by.clevertec.sakuuj.carshowroom.service.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import by.clevertec.sakuuj.carshowroom.dto.ClientRequest;
import by.clevertec.sakuuj.carshowroom.dto.ClientResponse;
import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import by.clevertec.sakuuj.carshowroom.mapper.ClientMapper;
import by.clevertec.sakuuj.carshowroom.repository.ClientRepo;
import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import by.clevertec.sakuuj.carshowroom.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.SortDirection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientMapper clientMapper;

    private final ClientRepo clientRepo;

    private final SessionFactory sessionFactory;

    @Override
    public void addCarToClient(UUID carId, UUID clientId) {

        sessionFactory.inTransaction(
                session -> clientRepo.addCarToClient(carId, clientId, session)
        );
    }

    @Override
    public PageResponse<ClientResponse> findAll(Pageable pageable, SortDirection sortDirection) {

        List<Client> found = sessionFactory.fromTransaction(session -> clientRepo.findAll(pageable, sortDirection, session));
        List<ClientResponse> pageContent = found.stream()
                .map(clientMapper::toResponse)
                .toList();

        return PageResponse.builder(pageContent, pageable).build();
    }

    @Override
    public Optional<ClientResponse> findById(UUID id) {

        return sessionFactory.fromTransaction(session -> clientRepo.findById(id, session))
                .map(clientMapper::toResponse);
    }

    @Override
    public ClientResponse create(ClientRequest request) {

        Client savedClient = sessionFactory.fromTransaction(session -> {

            Client clientToSave = clientMapper.toEntity(request);
            return clientRepo.create(clientToSave, session);
        });

        return clientMapper.toResponse(savedClient);
    }

    @Override
    public void update(UUID id, ClientRequest request) {

        sessionFactory.inTransaction(session -> {

            Client clientToUpdate = clientRepo.findById(id, session)
                    .orElseThrow(EntityNotFoundException::new);

            clientMapper.updateEntity(clientToUpdate, request);
        });
    }

    @Override
    public void deleteById(UUID id) {

        sessionFactory.inTransaction(session -> clientRepo.deleteById(id, session));
    }
}
