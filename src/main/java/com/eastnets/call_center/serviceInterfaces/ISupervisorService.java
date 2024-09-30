package com.eastnets.call_center.serviceInterfaces;

import com.eastnets.call_center.model.Supervisor;

public interface ISupervisorService {
    Supervisor login(String username, String password);
    Supervisor getSupervisorById(Long id);
}
