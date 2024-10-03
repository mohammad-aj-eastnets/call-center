package com.eastnets.call_center.service;

import com.eastnets.call_center.enums.AgentStatus;
import com.eastnets.call_center.model.Call;
import com.eastnets.call_center.model.CallCenterAgent;
import com.eastnets.call_center.repositoryInterfaces.ICallRepository;
import com.eastnets.call_center.serviceInterfaces.ICallService;
import com.eastnets.call_center.serviceInterfaces.ICallCenterAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CallService implements ICallService {

    private final ICallRepository callRepository;
    private final ICallCenterAgentService agentService;

    @Autowired
    public CallService(ICallRepository callRepository, ICallCenterAgentService agentService) {
        this.callRepository = callRepository;
        this.agentService = agentService;
    }

    @Override
    public void startCall(Call call) {
        call.setStartTime(LocalDateTime.now());
        callRepository.save(call);
    }

    @Override
    public void endCall(int callID) {
        Call call = callRepository.findById(callID);
        if (call != null && call.getEndTime() == null) { // Check if the call has already ended
            call.setEndTime(LocalDateTime.now());
            callRepository.update(call);
            agentService.changeAgentStatus(call.getAgentID(), AgentStatus.READY); // Change agent status to READY
            agentService.incrementTotalCalls(call.getAgentID()); // Increment total calls for the agent
        }
    }

    @Override
    public Call getCallById(int callID) {
        return callRepository.findById(callID);
    }

    @Override
    public List<Call> getAllCalls() {
        return callRepository.findAll();
    }

    public void closeLongestCalls() {
        List<Call> longCalls = getAllCalls().stream()
                .filter(call -> call.getDuration() > 10)
                .sorted((c1, c2) -> Integer.compare(c2.getDuration(), c1.getDuration()))
                .collect(Collectors.toList());

        int callsToClose = (int) (longCalls.size() * 0.75);
        for (int i = 0; i < callsToClose; i++) {
            Call call = longCalls.get(i);
            endCall(call.getCallID());
        }
    }

    public void generateCalls() {
        List<CallCenterAgent> readyAgents = agentService.getAllAgents().stream()
                .filter(agent -> agent.getStatus() == AgentStatus.READY)
                .collect(Collectors.toList());

        if (!readyAgents.isEmpty()) {
            Random random = new Random();
            CallCenterAgent selectedAgent = readyAgents.get(random.nextInt(readyAgents.size()));
            Call call = new Call();
            call.setAgentID(selectedAgent.getId());
            startCall(call);
            agentService.changeAgentStatus(selectedAgent.getId(), AgentStatus.ON_CALL); // Change status to ON_CALL
        }
    }
}
