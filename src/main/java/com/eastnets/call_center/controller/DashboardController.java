package com.eastnets.call_center.controller;

import com.eastnets.call_center.enums.AgentStatus;
import com.eastnets.call_center.model.Call;
import com.eastnets.call_center.model.CallCenterAgent;
import com.eastnets.call_center.serviceInterfaces.ICallCenterAgentService;
import com.eastnets.call_center.serviceInterfaces.ICallService;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScope
@Controller
public class DashboardController implements Serializable {

    private ICallCenterAgentService agentService;
    private ICallService callService;

    private List<CallCenterAgent> agents;
    private List<Call> calls;
    private PieChartModel pieModel;

    public DashboardController() {
        // No-argument constructor
    }

    @Autowired
    public void setAgentService(ICallCenterAgentService agentService) {
        this.agentService = agentService;
    }

    @Autowired
    public void setCallService(ICallService callService) {
        this.callService = callService;
    }

    @PostConstruct
    public void init() {
        loadDashboardData();
        createPieModel();
    }

    public List<CallCenterAgent> getAgents() {
        return agents;
    }

    public List<Call> getCalls() {
        return calls;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public int getTotalAgents() {
        return agents != null ? agents.size() : 0;
    }

    public void loadDashboardData() {
        this.agents = agentService.getAllAgents();
        this.calls = callService.getAllCalls();
        createPieModel(); // Update pie chart data
    }

    public String getAgentById(Long id) {
        CallCenterAgent agent = agentService.getAgentById(id);
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("agent", agent);
        return "agent";
    }

    public void toggleAgentStatus(Long id) {
        CallCenterAgent agent = agentService.getAgentById(id);
        if (agent != null && agent.getStatus() != AgentStatus.ON_CALL) {
            agent.setReady(!agent.isReady());
            agentService.changeAgentStatus(id, agent.getStatus());
            loadDashboardData(); // Reload the data after status change
        }
    }

    @Scheduled(fixedRate = 10000) // Run every 10 seconds
    public void generateCalls() {
        callService.generateCalls();
        loadDashboardData(); // Refresh data
    }

    @Scheduled(fixedRate = 60000) // Run every 60 seconds
    public void closeLongestCalls() {
        callService.closeLongestCalls();
        loadDashboardData(); // Refresh data
    }

    private void createPieModel() {
        pieModel = new PieChartModel();
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
    }
}
