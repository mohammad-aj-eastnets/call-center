package com.eastnets.call_center.serviceInterfaces;

import com.eastnets.call_center.enums.AgentStatus;
import com.eastnets.call_center.model.CallCenterAgent;

import java.util.List;

public interface ICallCenterAgentService {
    CallCenterAgent getAgentById(Long id);
    List<CallCenterAgent> getAllAgents();
    void addAgent(CallCenterAgent agent);
    void removeAgent(Long id);
    Long getAgentStatusDuration(Long id, String status);
    boolean changeAgentStatus(Long id, AgentStatus newStatus);

    void incrementTotalCalls(Long id);

    boolean toggleAgentStatus(Long id);
}
