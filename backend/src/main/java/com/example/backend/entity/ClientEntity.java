package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "clients")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String lastName;

    @Column
    private String firstName;

    @Column
    private LocalDate dateBirth;

    @Column
    private String numberTel;

    @Column
    private LocalDate lastDate;

    @Column
    private String description;

    @Column
    private Integer countVisit;

    @Column
    private Boolean bonusVisit;
}

