package com.eastnets.call_center.controller;

import com.eastnets.call_center.enums.AgentStatus;
import com.eastnets.call_center.model.Call;
import com.eastnets.call_center.model.CallCenterAgent;
import com.eastnets.call_center.model.GeneratedReport;
import com.eastnets.call_center.serviceInterfaces.*;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScope
@Controller
public class DashboardController implements Serializable {

    private static final long serialVersionUID = 1L;

    private ICallCenterAgentService agentService;
    private ICallService callService;
    private IGeneratedReportService reportService;
    private IDashboardService dashboardService;
    private IResetService resetService;

    private List<CallCenterAgent> agents;
    private List<Call> calls;
    private List<GeneratedReport> reports;
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

    @Autowired
    public void setReportService(IGeneratedReportService reportService) {
        this.reportService = reportService;
    }

    @Autowired
    public void setDashboardService(IDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Autowired
    public void setResetService(IResetService resetService) {
        this.resetService = resetService;
    }

    @PostConstruct
    public void init() {
        if (agentService == null) {
            throw new IllegalStateException("agentService is not properly initialized");
        }
        if (callService == null) {
            throw new IllegalStateException("callService is not properly initialized");
        }
        if (reportService == null) {
            throw new IllegalStateException("reportService is not properly initialized");
        }
        if (dashboardService == null) {
            throw new IllegalStateException("dashboardService is not properly initialized");
        }
        if (resetService == null) {
            throw new IllegalStateException("resetService is not properly initialized");
        }
        loadDashboardData();
        createPieModel();
        generateReports();
    }

    public List<CallCenterAgent> getAgents() {
        return agents;
    }

    public List<Call> getCalls() {
        return calls;
    }

    public List<GeneratedReport> getReports() {
        return reports;
    }

    public PieChartModel getPieModel() {
        return pieModel;
    }

    public String getAverageTalkTime() {
        return dashboardService.getAverageTalkTime();
    }

    public String getLongestTalkTime() {
        return dashboardService.getLongestTalkTime();
    }

    public int getTotalCalls() {
        return dashboardService.getTotalCalls();
    }

    public void loadDashboardData() {
        if (agentService == null) {
            throw new IllegalStateException("agentService is not properly initialized");
        }
        if (callService == null) {
            throw new IllegalStateException("callService is not properly initialized");
        }
        if (reportService == null) {
            throw new IllegalStateException("reportService is not properly initialized");
        }
        this.agents = agentService.getAllAgents();
        this.calls = callService.getAllCalls();
        this.reports = reportService.getAllReports();
        createPieModel();
    }

    public void toggleAgentStatus(Long id) {
        if (agentService == null) {
            throw new IllegalStateException("agentService is not properly initialized");
        }
        CallCenterAgent agent = agentService.getAgentById(id);
        if (agent != null && agent.getStatus() != AgentStatus.ON_CALL) {
            agent.setReady(!agent.isReady());
            agentService.changeAgentStatus(id, agent.getStatus());
            loadDashboardData();
        }
    }

    @Scheduled(fixedRate = 10000)
    public void generateCalls() {
        if (callService == null) {
            throw new IllegalStateException("callService is not properly initialized");
        }
        callService.generateCalls();
        loadDashboardData(); // Refresh data
    }

    @Scheduled(fixedRate = 60000)
    public void closeLongestCalls() {
        if (callService == null) {
            throw new IllegalStateException("callService is not properly initialized");
        }
        callService.closeLongestCalls();
        loadDashboardData();
    }

    @Scheduled(fixedRate = 70000)
    public void generateReports() {
        if (reportService == null) {
            throw new IllegalStateException("reportService is not properly initialized");
        }
        reportService.generateReports();
        loadDashboardData();
    }

    private void createPieModel() {
        if (agents == null) {
            throw new IllegalStateException("Agents list is not properly initialized");
        }
        pieModel = dashboardService.createPieModel();
    }

    public void generatePdf() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/call-center/report");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}