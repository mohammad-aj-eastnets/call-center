package com.eastnets.call_center.service;

import com.eastnets.call_center.config.CallConfig;
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
import java.time.Duration;

@Service
public class CallService implements ICallService {

    private final ICallRepository callRepository;
    private final ICallCenterAgentService agentService;
    private final CallConfig callConfig;

    @Autowired
    public CallService(ICallRepository callRepository, ICallCenterAgentService agentService, CallConfig callConfig) {
        this.callRepository = callRepository;
        this.agentService = agentService;
        this.callConfig = callConfig;
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
            call.setClosure("closed by system"); // Set closure field
            call.setDuration(Duration.between(call.getStartTime(), call.getEndTime()).getSeconds()); // Calculate and set duration
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
                .filter(call -> Duration.between(call.getStartTime(), LocalDateTime.now()).getSeconds() > callConfig.getCallDurationThreshold())
                .sorted((c1, c2) -> Long.compare(
                        Duration.between(c2.getStartTime(), LocalDateTime.now()).getSeconds(),
                        Duration.between(c1.getStartTime(), LocalDateTime.now()).getSeconds()))
                .collect(Collectors.toList());

        int callsToClose = (int) (longCalls.size() * callConfig.getCallClosePercentage());
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
