package by.clevertec.sakuuj.carshowroom.service.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import by.clevertec.sakuuj.carshowroom.dto.ClientRequest;
import by.clevertec.sakuuj.carshowroom.dto.ClientResponse;
import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import by.clevertec.sakuuj.carshowroom.mapper.ClientMapper;
import by.clevertec.sakuuj.carshowroom.repository.ClientRepo;
import by.clevertec.sakuuj.carshowroom.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientMapper clientMapper;

    private final ClientRepo clientRepo;


    @Override
    public void addCarToClient(UUID carId, UUID clientId) {

        clientRepo.addCarToClient(carId, clientId);
    }

    @Override
    public PageResponse<ClientResponse> findAll(Pageable pageable) {

        Page<ClientResponse> found = clientRepo.findAll(pageable).map(clientMapper::toResponse);

        return PageResponse.of(found);
    }

    @Override
    public ClientResponse findById(UUID id) {

        return clientRepo.findById(id).map(clientMapper::toResponse)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ClientResponse create(ClientRequest request) {

        Client clientToSave = clientMapper.toEntity(request);
        Client saved = clientRepo.save(clientToSave);

        return clientMapper.toResponse(saved);
    }

    @Override
    public void update(UUID id, ClientRequest request) {

            Client clientToUpdate = clientRepo.findById(id)
                    .orElseThrow(EntityNotFoundException::new);

            clientMapper.updateEntity(clientToUpdate, request);
    }

    @Override
    public void deleteById(UUID id) {

        clientRepo.deleteById(id);
    }
}
