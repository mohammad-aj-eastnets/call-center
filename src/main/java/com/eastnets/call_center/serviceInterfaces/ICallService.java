package com.eastnets.call_center.serviceInterfaces;

import com.eastnets.call_center.model.Call;

import java.util.List;

public interface ICallService {
    Call getCallById(Long id);
    List<Call> getAllCalls();
    void saveCall(Call call);
    void deleteCall(Long id);
    Call simulateCall(Long agentId);
    Call closeCall(Long id);
}
