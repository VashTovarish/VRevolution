package com.example.backend.repository;

import com.example.backend.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    /**
     * Ищет все клиенты, содержащие в названии определенную строку
     *
     * @param firstName, lastName, Строки, по которым идет поиск
     * @return Возвращает полученный список клиентов
     */
    List<ClientEntity> findAllByFirstNameContainsIgnoreCase(String firstName);
}

