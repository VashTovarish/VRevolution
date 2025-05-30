package com.example.backend.security.service.impl;

import com.example.backend.entity.EmployeeEntity;
import com.example.backend.repository.EmployeeRepository;
import com.example.backend.security.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public long signUp(EmployeeEntity employee) {
        return employeeRepository.save(employee).getId();
    }

    @Override
    public Optional<EmployeeEntity> getUserById(long employeeId) {
        return employeeRepository.findById(employeeId);
    }
}
