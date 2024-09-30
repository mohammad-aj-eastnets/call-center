package com.eastnets.call_center.repositoryInterfaces;

import com.eastnets.call_center.model.CallCenterAgent;

import java.util.List;

public interface ICallCenterAgentRepository {
    CallCenterAgent findById(Long id);
    List<CallCenterAgent> findAll();
    void save(CallCenterAgent agent);
    void delete(Long id);
    Long getStatusDuration(Long id, String status);
}
