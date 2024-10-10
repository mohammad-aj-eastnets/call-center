package com.eastnets.call_center.model;

import com.eastnets.call_center.enums.AgentStatus;

public class CallCenterAgent {
    private Long id;
    private String name;
    private AgentStatus status;
    private long statusDurationSeconds;
    private long statusDurationMinutes;
    private long statusDurationHours;
    private Long totalNumberOfCalls;
    private Long totalTimeNotReady;

    public CallCenterAgent() {
        resetStatusDuration();
        this.totalTimeNotReady = 0L;
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
        resetStatusDuration();
    }

    public boolean isReady() {
        return this.status == AgentStatus.READY;
    }

    public void setReady(boolean ready) {
        this.status = ready ? AgentStatus.READY : AgentStatus.NOT_READY;
    }

    public long getStatusDurationSeconds() {
        return statusDurationSeconds;
    }

    public long getStatusDurationMinutes() {
        return statusDurationMinutes;
    }

    public long getStatusDurationHours() {
        return statusDurationHours;
    }

    public void setTotalNumberOfCalls(long totalNumberOfCalls) {
        this.totalNumberOfCalls = totalNumberOfCalls;
    }

    public long getTotalNumberOfCalls() {
        return totalNumberOfCalls;
    }

    private void resetStatusDuration() {
        this.statusDurationSeconds = 0;
        this.statusDurationMinutes = 0;
        this.statusDurationHours = 0;
    }

    public void updateStatusDuration(long durationInSeconds) {
        this.statusDurationSeconds = durationInSeconds % 60;
        this.statusDurationMinutes = (durationInSeconds / 60) % 60;
        this.statusDurationHours = durationInSeconds / 3600;
    }

    public Long getTotalTimeNotReady() {
        return totalTimeNotReady;
    }

    public void accumulateNotReadyTime(long durationInSeconds) {
        this.totalTimeNotReady += durationInSeconds;
    }
}
