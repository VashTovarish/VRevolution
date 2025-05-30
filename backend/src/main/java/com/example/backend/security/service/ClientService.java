package com.example.backend.security.service;

import com.example.backend.entity.ClientEntity;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для взаимодействия с клиентами
 */
public interface ClientService {
    /**
     * Добавляет нового клиента
     *
     * @param client Новый клиент
     * @return Возвращает идентификатор добавленного клиента
     */
    long addNewClient(ClientEntity client);

    /**
     * Ищет клиента по его идентификатору
     *
     * @param id Уникальный идентификатор клиента
     * @return Возвращает найденного клиента
     */
    Optional<ClientEntity> findById(long id);

    /**
     * Ищет клиенита по имени
     *
     * @param firstName Имя клиента
     * @return Возвращает список найденных клиентов
     */
    List<ClientEntity> findAllByName(String firstName);


    /**
     * Изменяет количество посещений клиента
     *
     * @param clientId   id клиента
     * @param countVisit новое кол-во посещений
     */
    void updateCountVisit(long clientId, Integer countVisit);

    /**
     * Проверяет существование клиента
     *
     * @param clientId Уникальный идентификатор клиента
     * @return Возвращает статус проверки
     */
    boolean checkClientExistence(long clientId);

    /**
     * Удаляет клиента по идентификатору
     *
     * @param clientId Уникальный идентификатор клиента
     * @return Возвращает статус удаления клиента
     */
    boolean deleteById(long clientId);
}
