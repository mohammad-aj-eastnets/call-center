package com.eastnets.call_center.model;

import java.time.LocalDateTime;
import java.time.Duration;

public class Call {
    private int callID;
    private long agentID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int duration;

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

    public int getDuration() {
        if (endTime != null) {
            return (int) Duration.between(startTime, endTime).getSeconds();
        } else {
            return (int) Duration.between(startTime, LocalDateTime.now()).getSeconds();
        }
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFormattedDuration() {
        int seconds = getDuration();
        int minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%d min, %d sec", minutes, seconds);
    }
}
