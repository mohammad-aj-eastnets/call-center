package com.eastnets.call_center.service;

import com.eastnets.call_center.enums.AgentStatus;
import com.eastnets.call_center.model.CallCenterAgent;
import com.eastnets.call_center.repositoryInterfaces.ICallCenterAgentRepository;
import com.eastnets.call_center.serviceInterfaces.ICallCenterAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallCenterAgentService implements ICallCenterAgentService {

    private final ICallCenterAgentRepository callCenterAgentRepository;

    @Autowired
    public CallCenterAgentService(ICallCenterAgentRepository callCenterAgentRepository) {
        this.callCenterAgentRepository = callCenterAgentRepository;
    }

    @Override
    public CallCenterAgent getAgentById(Long id) {
        return callCenterAgentRepository.findById(id);
    }

    @Override
    public List<CallCenterAgent> getAllAgents() {
        return callCenterAgentRepository.findAll();
    }

    @Override
    public void addAgent(CallCenterAgent agent) {
        callCenterAgentRepository.save(agent);
    }

    @Override
    public void removeAgent(Long id) {
        callCenterAgentRepository.delete(id);
    }

    @Override
    public Long getAgentStatusDuration(Long id, String status) {
        return callCenterAgentRepository.getStatusDuration(id, status);
    }

    @Override
    public boolean changeAgentStatus(Long id, AgentStatus newStatus) {
        return callCenterAgentRepository.updateStatus(id, newStatus);
    }

    @Override
    public void incrementTotalCalls(Long id) {
        callCenterAgentRepository.incrementTotalCalls(id);
    }

    public boolean toggleAgentStatus(Long id) {
        CallCenterAgent agent = getAgentById(id);
        if (agent != null && agent.getStatus() != AgentStatus.ON_CALL) {
            AgentStatus newStatus = (agent.getStatus() == AgentStatus.READY) ? AgentStatus.NOT_READY : AgentStatus.READY;
            return changeAgentStatus(id, newStatus);
        }
        return false;
    }
}
