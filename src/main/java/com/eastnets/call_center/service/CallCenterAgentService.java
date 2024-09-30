package com.eastnets.call_center.service;

import com.eastnets.call_center.enums.AgentStatus;
import com.eastnets.call_center.model.CallCenterAgent;
import com.eastnets.call_center.repositoryInterfaces.ICallCenterAgentRepository;
import com.eastnets.call_center.serviceInterfaces.ICallCenterAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CallCenterAgentService implements ICallCenterAgentService {

    private final ICallCenterAgentRepository agentRepository;

    @Autowired
    public CallCenterAgentService(ICallCenterAgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public CallCenterAgent getAgentById(Long id) {
        return agentRepository.findById(id);
    }

    @Override
    public List<CallCenterAgent> getAllAgents() {
        return agentRepository.findAll();
    }

    @Override
    public void saveAgent(CallCenterAgent agent) {
        agentRepository.save(agent);
    }

    @Override
    public void deleteAgent(Long id) {
        agentRepository.delete(id);
    }

    @Override
    public long getStatusDuration(Long id, String status) {
        return agentRepository.getStatusDuration(id, status);
    }

    @Override
    public CallCenterAgent updateAgentStatus(Long id, String status) {
        CallCenterAgent agent = getAgentById(id);
        if (agent != null) {
            agent.setStatus(AgentStatus.valueOf(status));
            agent.setStatusTime(Timestamp.valueOf(LocalDateTime.now()));
            agentRepository.save(agent);
        }
        return agent;
    }
}
