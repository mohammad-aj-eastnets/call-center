package com.eastnets.call_center.model;

import java.time.LocalDateTime;

public class Call {
    private int callID;
    private long agentID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String closure;
    private long duration;

    // Getters and setters
    public int getCallID() {
        return callID;
    }

    public void setCallID(int callID) {
        this.callID = callID;
    }

    public long getAgentID() {
        return agentID;
    }

    public void setAgentID(long agentID) {
        this.agentID = agentID;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getFormattedDuration() {
        int seconds = (int) duration;
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%d min, %d sec", minutes, seconds);
    }

    public String getClosure() {
        return closure;
    }

    public void setClosure(String closure) {
        this.closure = closure;
    }
}
