package com.eastnets.call_center.repositoryInterfaces;

import com.eastnets.call_center.enums.AgentStatus;
import com.eastnets.call_center.model.CallCenterAgent;

import java.util.List;

public interface ICallCenterAgentRepository {
    CallCenterAgent findById(Long id);
    List<CallCenterAgent> findAll();
    void save(CallCenterAgent agent);
    void delete(Long id);
    Long getStatusDuration(Long id, String status);
    boolean updateStatus(Long id, AgentStatus newStatus);
    void incrementTotalCalls(Long id);
    void updateTotalTimeReady(Long id, Long totalReadyTime);
    void updateTotalTimeOnCall(Long id, Long totalOnCallTime);
    void updateTotalTimeNotReady(Long id, Long totalNotReadyTime);
    void resetAgents();
}
