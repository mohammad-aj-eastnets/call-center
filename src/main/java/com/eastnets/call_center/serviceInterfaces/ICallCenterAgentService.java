package com.eastnets.call_center.serviceInterfaces;

import com.eastnets.call_center.model.CallCenterAgent;

import java.util.List;

public interface ICallCenterAgentService {
    CallCenterAgent getAgentById(Long id);
    List<CallCenterAgent> getAllAgents();
    void saveAgent(CallCenterAgent agent);
    void deleteAgent(Long id);
    long getStatusDuration(Long id, String status);
    CallCenterAgent updateAgentStatus(Long id, String status);
}
