package com.eastnets.call_center.controller;

import com.eastnets.call_center.model.Call;
import com.eastnets.call_center.model.CallCenterAgent;
import com.eastnets.call_center.serviceInterfaces.ICallCenterAgentService;
import com.eastnets.call_center.serviceInterfaces.ICallService;
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
    }

    public List<CallCenterAgent> getAgents() {
        return agents;
    }

    public List<Call> getCalls() {
        return calls;
    }

    public void loadDashboardData() {
        this.agents = agentService.getAllAgents();
        this.calls = callService.getAllCalls();
    }

    public String toggleAgentStatus(Long id) {
        boolean updated = agentService.toggleAgentStatus(id);
        if (updated) {
            loadDashboardData(); // Reload the data after status change
            return null; // Stay on the same page
        }
        // Handle the case where the status could not be updated
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("error", "Cannot update status while agent is on a call.");
        return "error";
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
}