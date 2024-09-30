package com.eastnets.call_center.model;

import java.time.LocalDate;

public class GeneratedReport {
    private Long id;
    private Long agentId;
    private LocalDate reportDate;
    private int totalCalls;
    private long totalTalkTime;
    private long longestTalkTime;
    private long shortestTalkTime;
    private long totalNotReadyTime;
    private double averageCalls;

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

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public int getTotalCalls() {
        return totalCalls;
    }

    public void setTotalCalls(int totalCalls) {
        this.totalCalls = totalCalls;
    }

    public long getTotalTalkTime() {
        return totalTalkTime;
    }

    public void setTotalTalkTime(long totalTalkTime) {
        this.totalTalkTime = totalTalkTime;
    }

    public long getLongestTalkTime() {
        return longestTalkTime;
    }

    public void setLongestTalkTime(long longestTalkTime) {
        this.longestTalkTime = longestTalkTime;
    }

    public long getShortestTalkTime() {
        return shortestTalkTime;
    }

    public void setShortestTalkTime(long shortestTalkTime) {
        this.shortestTalkTime = shortestTalkTime;
    }

    public long getTotalNotReadyTime() {
        return totalNotReadyTime;
    }

    public void setTotalNotReadyTime(long totalNotReadyTime) {
        this.totalNotReadyTime = totalNotReadyTime;
    }

    public double getAverageCalls() {
        return averageCalls;
    }

    public void setAverageCalls(double averageCalls) {
        this.averageCalls = averageCalls;
    }

}
