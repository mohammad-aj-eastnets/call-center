package com.eastnets.call_center.serviceInterfaces;

import com.eastnets.call_center.model.Call;

import java.util.List;

public interface ICallService {
    void startCall(Call call);
    void endCall(int callID);
    Call getCallById(int callID);
    List<Call> getAllCalls();
    void closeLongestCalls();

    void generateCalls();
}