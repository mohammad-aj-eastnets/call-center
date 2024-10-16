package com.eastnets.call_center.model;

import com.eastnets.call_center.enums.AgentStatus;

public class CallCenterAgent {
    private Long id;
    private String name;
    private AgentStatus status;
    private Long totalNumberOfCalls;
    private Long totalTimeReady;
    private Long totalTimeOnCall;
    private Long totalTimeNotReady;
    private long lastStatusChangeTimestamp; // New field

    public CallCenterAgent() {
        this.totalTimeReady = 0L;
        this.totalTimeOnCall = 0L;
        this.totalTimeNotReady = 0L;
        this.lastStatusChangeTimestamp = System.currentTimeMillis() / 1000; // Initialize with current time
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AgentStatus getStatus() {
        return status;
    }

    public void setStatus(AgentStatus status) {
        this.status = status;
        this.lastStatusChangeTimestamp = System.currentTimeMillis() / 1000; // Update timestamp on status change
    }

    public boolean isReady() {
        return this.status == AgentStatus.READY;
    }

    public void setReady(boolean ready) {
        this.status = ready ? AgentStatus.READY : AgentStatus.NOT_READY;
        this.lastStatusChangeTimestamp = System.currentTimeMillis() / 1000; // Update timestamp on status change
    }

    public Long getTotalNumberOfCalls() {
        return totalNumberOfCalls;
    }

    public void setTotalNumberOfCalls(Long totalNumberOfCalls) {
        this.totalNumberOfCalls = totalNumberOfCalls;
    }

    public Long getTotalTimeReady() {
        return totalTimeReady;
    }

    public void accumulateReadyTime(long durationInSeconds) {
        this.totalTimeReady += durationInSeconds;
    }

    public Long getTotalTimeOnCall() {
        return totalTimeOnCall;
    }

    public void accumulateOnCallTime(long durationInSeconds) {
        this.totalTimeOnCall += durationInSeconds;
    }

    public Long getTotalTimeNotReady() {
        return totalTimeNotReady;
    }

    public void accumulateNotReadyTime(long durationInSeconds) {
        this.totalTimeNotReady += durationInSeconds;
    }

    public long getLastStatusChangeTimestamp() {
        return lastStatusChangeTimestamp;
    }

    public void setLastStatusChangeTimestamp(long lastStatusChangeTimestamp) {
        this.lastStatusChangeTimestamp = lastStatusChangeTimestamp;
    }
}
