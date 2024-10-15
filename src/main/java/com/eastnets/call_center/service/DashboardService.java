package com.eastnets.call_center.service;

import com.eastnets.call_center.enums.AgentStatus;
import com.eastnets.call_center.model.Call;
import com.eastnets.call_center.model.CallCenterAgent;
import com.eastnets.call_center.serviceInterfaces.ICallCenterAgentService;
import com.eastnets.call_center.serviceInterfaces.ICallService;
import com.eastnets.call_center.serviceInterfaces.IDashboardService;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DashboardService implements IDashboardService {

    private final ICallService callService;
    private final ICallCenterAgentService agentService;

    @Autowired
    public DashboardService(ICallService callService, ICallCenterAgentService agentService) {
        this.callService = callService;
        this.agentService = agentService;
    }

    @Override
    public PieChartModel createPieModel() {
        List<CallCenterAgent> agents = agentService.getAllAgents();
        PieChartModel pieModel = new PieChartModel();
        long readyCount = agents.stream().filter(agent -> agent.getStatus() == AgentStatus.READY).count();
        long onCallCount = agents.stream().filter(agent -> agent.getStatus() == AgentStatus.ON_CALL).count();
        long notReadyCount = agents.stream().filter(agent -> agent.getStatus() == AgentStatus.NOT_READY).count();

        pieModel.set("Ready", readyCount);
        pieModel.set("On Call", onCallCount);
        pieModel.set("Not Ready", notReadyCount);

        pieModel.setTitle("Agent Status Distribution");
        pieModel.setLegendPosition("w");
        pieModel.setShowDataLabels(true);
        pieModel.setSeriesColors("FF9999,99CCFF,FFFF99"); // Light Red, Light Blue, Light Yellow

        return pieModel;
    }
    private String formatDuration(long durationInSeconds) {
        long hours = TimeUnit.SECONDS.toHours(durationInSeconds);
        long minutes = TimeUnit.SECONDS.toMinutes(durationInSeconds) % 60;
        long seconds = durationInSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public String getAverageTalkTime() {
        List<Call> calls = callService.getAllCalls();
        long averageDurationInSeconds = (long) calls.stream().mapToDouble(Call::getDuration).average().orElse(0);
        return formatDuration(averageDurationInSeconds);
    }

    @Override
    public String getLongestTalkTime() {
        List<Call> calls = callService.getAllCalls();
        long longestDurationInSeconds = (long) calls.stream().mapToDouble(Call::getDuration).max().orElse(0);
        return formatDuration(longestDurationInSeconds);
    }

    @Override
    public int getTotalCalls() {
        return callService.getAllCalls().size();
    }
}
