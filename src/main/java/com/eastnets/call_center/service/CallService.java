package com.eastnets.call_center.service;

import com.eastnets.call_center.enums.CallStatus;
import com.eastnets.call_center.model.Call;
import com.eastnets.call_center.repositoryInterfaces.ICallRepository;
import com.eastnets.call_center.serviceInterfaces.ICallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CallService implements ICallService {

    private final ICallRepository callRepository;

    @Autowired
    public CallService(ICallRepository callRepository) {
        this.callRepository = callRepository;
    }

    @Override
    public Call getCallById(Long id) {
        return callRepository.findById(id);
    }

    @Override
    public List<Call> getAllCalls() {
        return callRepository.findAll();
    }

    @Override
    public void saveCall(Call call) {
        callRepository.save(call);
    }

    @Override
    public void deleteCall(Long id) {
        callRepository.delete(id);
    }

    @Override
    public Call simulateCall(Long agentId) {
        Call call = new Call();
        call.setAgentId(agentId);
        call.setStartTime(LocalDateTime.now());
        call.setStatus(CallStatus.ACTIVE);
        callRepository.save(call);
        return call;
    }

    @Override
    public Call closeCall(Long id) {
        Call call = callRepository.findById(id);
        if (call != null) {
            call.setEndTime(LocalDateTime.now());
            call.setStatus(CallStatus.CLOSED);
            call.setCallDuration((java.time.Duration.between(call.getStartTime(), call.getEndTime()).toMinutes()));
            callRepository.save(call);
        }
        return call;
    }
}
