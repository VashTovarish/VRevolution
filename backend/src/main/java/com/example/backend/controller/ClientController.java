package com.example.backend.controller;

import com.example.backend.entity.ClientEntity;
import com.example.backend.security.service.ClientService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("clients")
public class ClientController {
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Добавляет нового клиента
     *
     * @param client Добавляемый клиент
     * @return статус добавления клиента
     */
    @PostMapping
    public ResponseEntity<?> addClient(@Valid @RequestBody ClientEntity client) {
        long clientId = clientService.addNewClient(client);
        log.info("Добавление клиента {}", client);
        return ResponseEntity.created(URI.create("clients/" + clientId)).build();
    }

    /**
     * Выдает всех клиентов по фильтру
     *
     * @param firstName Имя клиента (фильтр)
     * @return список найденных клиентов
     */
    @GetMapping
    public List<ClientEntity> getClients(@RequestParam(required = false) String firstName) {

        if (firstName == null) {
            firstName = "";
            log.info("Вывод всех клиентов");
        } else {
            log.info("Поиск клиентов по имени {}", firstName);
        }
        return clientService.findAllByName(firstName);
    }

    /**
     * Получение клиента по id
     *
     * @param id Идентификатор для поиска
     * @return Возвращает найденного клиента
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getClient(@PathVariable long id) {
        log.info("Получение клиента по id");
        Optional<ClientEntity> client = clientService.findById(id);
        return client.map(clientEntity ->
                ResponseEntity.ok().body(clientEntity)).orElseGet(() ->
                ResponseEntity.notFound().build());
    }

    /**
     * Обновление информации о клиенте
     *
     * @param clientId id клиента
     * @param client   кол-во посещений
     * @return Возвращает обновленного клиента
     */
    @PutMapping("/{clientId}")
    public ResponseEntity<?> updateCountVisit(@Valid @PathVariable long clientId, @RequestBody ClientEntity client) {
        clientService.updateCountVisit(clientId, client.getCountVisit());
        log.info("Обновление информации о клиенте");
        return ResponseEntity.ok().body(clientId);
    }

    /**
     * Удаление клиента по id
     *
     * @param id Идентификатор клиента
     * @return Возвращает статус удаления клиента
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        log.info("Удаление клиента по id");
        boolean isDeleted = clientService.deleteById(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

