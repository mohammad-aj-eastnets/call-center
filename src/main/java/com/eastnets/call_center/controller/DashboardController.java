package com.eastnets.call_center.controller;

import com.eastnets.call_center.model.CallCenterAgent;
import com.eastnets.call_center.model.Call;
import com.eastnets.call_center.model.GeneratedReport;
import com.eastnets.call_center.serviceInterfaces.ICallCenterAgentService;
import com.eastnets.call_center.serviceInterfaces.ICallService;
import com.eastnets.call_center.serviceInterfaces.IGeneratedReportService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IGeneratedReportService reportService;

    private List<CallCenterAgent> agents;
    private List<Call> calls;
    private List<GeneratedReport> reports;

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

    public List<GeneratedReport> getReports() {
        return reports;
    }

    private void loadDashboardData() {
        this.agents = agentService.getAllAgents();
        this.calls = callService.getAllCalls();
        this.reports = reportService.getAllReports();
    }

    public String getAgentById(Long id) {
        CallCenterAgent agent = agentService.getAgentById(id);
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("agent", agent);
        return "agent";
    }

    public String updateAgentStatus(Long id, String status) {
        CallCenterAgent agent = agentService.updateAgentStatus(id, status);
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("agent", agent);
        return "agent";
    }

    public String simulateCall(Long agentId) {
        Call call = callService.simulateCall(agentId);
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("call", call);
        return "call";
    }

    public String closeCall(Long id) {
        Call call = callService.closeCall(id);
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("call", call);
        return "call";
    }

    public String generateDailyReport() {
        this.reports = reportService.getAllReports();
        FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("reports", reports);
        return "reports";
    }

    public long getStatusDuration(Long id, String status) {
        return agentService.getStatusDuration(id, status);
    }
}
