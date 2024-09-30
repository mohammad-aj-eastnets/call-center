package com.eastnets.call_center.model;
import com.eastnets.call_center.enums.CallStatus;
import java.time.LocalDateTime;

public class Call {
    private Long id;
    private Long agentId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private CallStatus status;
    private long callDuration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public CallStatus getStatus() {
        return status;
    }
    public void setStatus(CallStatus status) {
        this.status = status;
    }
    public long getCallDuration() {
        return callDuration;
    }
    public void setCallDuration(long callDuration) {
        this.callDuration = callDuration;
    }
}
