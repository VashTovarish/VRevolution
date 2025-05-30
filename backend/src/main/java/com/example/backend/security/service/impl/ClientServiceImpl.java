package com.example.backend.security.service.impl;

import com.example.backend.entity.ClientEntity;
import com.example.backend.repository.ClientRepository;
import com.example.backend.security.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public long addNewClient(ClientEntity client) {
        client.setCountVisit(1);
        client.setBonusVisit(false);
        client.setLastDate(LocalDate.now());
        return clientRepository.save(client).getId();
    }

    @Override
    public Optional<ClientEntity> findById(long id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<ClientEntity> findAllByName(String firstName) {
        return clientRepository.findAllByFirstNameContainsIgnoreCase(firstName);
    }

    @Transactional
    public void updateCountVisit(long clientId, Integer countVisit) {
        Optional<ClientEntity> client = clientRepository.findById(clientId);

        if (client.isPresent()) {
            ClientEntity newClient = client.get();
            newClient.setCountVisit(countVisit);
            newClient.setLastDate(LocalDate.now());
            if (countVisit == 5) {
                newClient.setBonusVisit(true);
                newClient.setCountVisit(0);
            } else if (countVisit >= 1) {
                newClient.setBonusVisit(false);
            }
            clientRepository.save(newClient);
            log.info("Обновление посещения на новое значение = {} для клиента с id {}", countVisit, clientId);
        }
    }

    @Override
    public boolean checkClientExistence(long clientId) {
        return false;
    }

    @Override
    public boolean deleteById(long clientId) {
        clientRepository.deleteById(clientId);
        return true;
    }
}

