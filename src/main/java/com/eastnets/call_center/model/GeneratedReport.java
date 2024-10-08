package com.eastnets.call_center.model;

public class GeneratedReport {
    private int reportID;
    private long agentID;
    private long totalNumberOfCalls;
    private long totalTalkTime;
    private long longestTalkTime;
    private long shortestTalkTime;
    private long totalTimeNotReady;
    private double avgRecOnTotal;

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

    public long getTotalNumberOfCalls() {
        return totalNumberOfCalls;
    }

    public void setTotalNumberOfCalls(long totalNumberOfCalls) {
        this.totalNumberOfCalls = totalNumberOfCalls;
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

    public long getTotalTimeNotReady() {
        return totalTimeNotReady;
    }

    public void setTotalTimeNotReady(long totalTimeNotReady) {
        this.totalTimeNotReady = totalTimeNotReady;
    }

    public double getAvgRecOnTotal() {
        return avgRecOnTotal;
    }

    public void setAvgRecOnTotal(double avgRecOnTotal) {
        this.avgRecOnTotal = avgRecOnTotal;
    }
}
