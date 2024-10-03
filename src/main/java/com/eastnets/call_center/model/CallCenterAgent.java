package com.eastnets.call_center.model;

import com.eastnets.call_center.enums.AgentStatus;

import java.sql.Timestamp;

public class CallCenterAgent {
    private Long id;
    private String name;
    private AgentStatus status;
    private Timestamp statusTime;
    private Long totalNumberOfCalls;

    public Timestamp getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(Timestamp statusTime) {
        this.statusTime = statusTime;
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
    }

    public void setTotalNumberOfCalls(long totalNumberOfCalls) {
        this.totalNumberOfCalls = totalNumberOfCalls;
    }

    public long getTotalNumberOfCalls() {
        return totalNumberOfCalls;
    }
}
