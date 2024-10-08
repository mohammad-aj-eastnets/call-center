package com.eastnets.call_center.model;

public class GeneratedReport {
    private int reportID;
    private long agentID;
    private Integer totalNumberOfCalls; // Use Integer instead of int
    private Long totalTalkTime; // Use Long instead of long
    private Long longestTalkTime; // Use Long instead of long
    private Long shortestTalkTime; // Use Long instead of long
    private Long totalTimeNotReady; // Use Long instead of long
    private Double avgRecOnTotal; // Use Double instead of double

    // Getters and setters
    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public long getAgentID() {
        return agentID;
    }

    public void setAgentID(long agentID) {
        this.agentID = agentID;
    }

    public Integer getTotalNumberOfCalls() {
        return totalNumberOfCalls;
    }

    public void setTotalNumberOfCalls(Integer totalNumberOfCalls) {
        this.totalNumberOfCalls = totalNumberOfCalls;
    }

    public Long getTotalTalkTime() {
        return totalTalkTime;
    }

    public void setTotalTalkTime(Long totalTalkTime) {
        this.totalTalkTime = totalTalkTime;
    }

    public Long getLongestTalkTime() {
        return longestTalkTime;
    }

    public void setLongestTalkTime(Long longestTalkTime) {
        this.longestTalkTime = longestTalkTime;
    }

    public Long getShortestTalkTime() {
        return shortestTalkTime;
    }

    public void setShortestTalkTime(Long shortestTalkTime) {
        this.shortestTalkTime = shortestTalkTime;
    }

    public Long getTotalTimeNotReady() {
        return totalTimeNotReady;
    }

    public void setTotalTimeNotReady(Long totalTimeNotReady) {
        this.totalTimeNotReady = totalTimeNotReady;
    }

    public Double getAvgRecOnTotal() {
        return avgRecOnTotal;
    }

    public void setAvgRecOnTotal(Double avgRecOnTotal) {
        this.avgRecOnTotal = avgRecOnTotal;
    }
}
