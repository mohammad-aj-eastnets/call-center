package com.eastnets.call_center.repositoryInterfaces;

import com.eastnets.call_center.model.Supervisor;

public interface ISupervisorRepository {
    Supervisor findByUsername(String username);
    Supervisor findById(Long id);
}
