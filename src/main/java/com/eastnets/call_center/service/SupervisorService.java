package com.eastnets.call_center.service;

import com.eastnets.call_center.model.Supervisor;
import com.eastnets.call_center.repositoryInterfaces.ISupervisorRepository;
import com.eastnets.call_center.serviceInterfaces.ISupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupervisorService implements ISupervisorService {
    private final ISupervisorRepository supervisorRepository;

    @Autowired
    public SupervisorService(ISupervisorRepository supervisorRepository) {
        this.supervisorRepository = supervisorRepository;
    }

    @Override
    public Supervisor login(String username, String password) {
        Supervisor supervisor = supervisorRepository.findByUsername(username);
        if (supervisor != null && supervisor.getPassword().equals(password)) {
            return supervisor;
        }
        return null;
    }

    @Override
    public Supervisor getSupervisorById(Long id) {
        return supervisorRepository.findById(id);
    }
}
