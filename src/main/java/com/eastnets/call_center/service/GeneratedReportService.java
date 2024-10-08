package com.eastnets.call_center.service;

import com.eastnets.call_center.model.GeneratedReport;
import com.eastnets.call_center.model.Call;
import com.eastnets.call_center.model.CallCenterAgent;
import com.eastnets.call_center.serviceInterfaces.IGeneratedReportService;
import com.eastnets.call_center.repositoryInterfaces.ICallRepository;
import com.eastnets.call_center.repositoryInterfaces.IGeneratedReportRepository;
import com.eastnets.call_center.serviceInterfaces.ICallCenterAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GeneratedReportService implements IGeneratedReportService {

    private final IGeneratedReportRepository generatedReportRepository;
    private final ICallRepository callRepository;
    private final ICallCenterAgentService agentService;

    @Autowired
    public GeneratedReportService(IGeneratedReportRepository generatedReportRepository, ICallRepository callRepository, ICallCenterAgentService agentService) {
        this.generatedReportRepository = generatedReportRepository;
        this.callRepository = callRepository;
        this.agentService = agentService;
    }

    public void generateReports() {
        List<CallCenterAgent> agents = agentService.getAllAgents();
        List<Call> allCalls = callRepository.findAll();
        int totalCallsInSystem = allCalls.size();

        for (CallCenterAgent agent : agents) {
            List<Call> agentCalls = allCalls.stream()
                    .filter(call -> call.getAgentID() == agent.getId())
                    .collect(Collectors.toList());

            Integer totalNumberOfCalls = !agentCalls.isEmpty() ? agentCalls.size() : null;
            Long totalTalkTime = agentCalls.stream().mapToLong(Call::getDuration).sum() > 0 ? agentCalls.stream().mapToLong(Call::getDuration).sum() : null;
            Long longestTalkTime = agentCalls.stream().mapToLong(Call::getDuration).max().orElse(0) > 0 ? agentCalls.stream().mapToLong(Call::getDuration).max().orElse(0) : null;
            Long shortestTalkTime = agentCalls.stream().mapToLong(Call::getDuration).min().orElse(0) > 0 ? agentCalls.stream().mapToLong(Call::getDuration).min().orElse(0) : null;
            Long totalTimeNotReady = agent.getTotalTimeNotReady() > 0 ? agent.getTotalTimeNotReady() : null; // Assuming this is tracked in the agent entity
            Double avgRecOnTotal = totalNumberOfCalls != null && totalCallsInSystem > 0 ? (double) totalNumberOfCalls / totalCallsInSystem : null;

            GeneratedReport report = new GeneratedReport();
            report.setAgentID(agent.getId());
            report.setTotalNumberOfCalls(totalNumberOfCalls);
            report.setTotalTalkTime(totalTalkTime);
            report.setLongestTalkTime(longestTalkTime);
            report.setShortestTalkTime(shortestTalkTime);
            report.setTotalTimeNotReady(totalTimeNotReady);
            report.setAvgRecOnTotal(avgRecOnTotal);

            generatedReportRepository.save(report);
        }
    }

    public List<GeneratedReport> getAllReports() {
        return generatedReportRepository.findAll();
    }
}
