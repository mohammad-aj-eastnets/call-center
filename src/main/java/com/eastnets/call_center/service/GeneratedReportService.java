package com.eastnets.call_center.service;

import com.eastnets.call_center.model.GeneratedReport;
import com.eastnets.call_center.model.Call;
import com.eastnets.call_center.model.CallCenterAgent;
import com.eastnets.call_center.repositoryInterfaces.IGeneratedReportRepository;
import com.eastnets.call_center.repositoryInterfaces.ICallRepository;
import com.eastnets.call_center.serviceInterfaces.ICallCenterAgentService;
import com.eastnets.call_center.serviceInterfaces.IGeneratedReportService;
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

    @Override
    public void generateReports() {
        List<CallCenterAgent> agents = agentService.getAllAgents();
        List<Call> allCalls = callRepository.findAll();
        int totalCallsInSystem = allCalls.size();

        for (CallCenterAgent agent : agents) {
            List<Call> agentCalls = allCalls.stream()
                    .filter(call -> call.getAgentID() == agent.getId())
                    .collect(Collectors.toList());

            long totalNumberOfCalls = agent.getTotalNumberOfCalls();
            long totalTalkTime = agentCalls.stream().mapToLong(Call::getDuration).sum();
            long longestTalkTime = agentCalls.stream().mapToLong(Call::getDuration).max().orElse(0);
            long shortestTalkTime = agentCalls.stream().mapToLong(Call::getDuration).filter(duration -> duration > 0).min().orElse(0);
            long totalTimeNotReady = agent.getTotalTimeNotReady(); // Assuming this is tracked in the agent entity
            double avgRecOnTotal = totalCallsInSystem > 0 ? (double) totalNumberOfCalls / totalCallsInSystem : 0;

            GeneratedReport report = generatedReportRepository.findByAgentID(agent.getId());
            if (report == null) {
                report = new GeneratedReport();
                report.setAgentID(agent.getId());
            }

            report.setTotalNumberOfCalls(totalNumberOfCalls);
            report.setTotalTalkTime(totalTalkTime);
            report.setLongestTalkTime(longestTalkTime);
            report.setShortestTalkTime(shortestTalkTime);
            report.setTotalTimeNotReady(totalTimeNotReady);
            report.setAvgRecOnTotal(avgRecOnTotal);

            if (report.getReportID() == 0) {
                generatedReportRepository.save(report);
            } else {
                generatedReportRepository.update(report);
            }
        }
    }

    @Override
    public List<GeneratedReport> getAllReports() {
        return generatedReportRepository.findAll();
    }
}
