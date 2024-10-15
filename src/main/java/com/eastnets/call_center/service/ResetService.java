package com.eastnets.call_center.service;

import com.eastnets.call_center.repositoryInterfaces.ICallCenterAgentRepository;
import com.eastnets.call_center.repositoryInterfaces.ICallRepository;
import com.eastnets.call_center.repositoryInterfaces.IGeneratedReportRepository;
import com.eastnets.call_center.serviceInterfaces.IResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ResetService implements IResetService {

    private final ICallCenterAgentRepository agentRepository;
    private final ICallRepository callRepository;
    private final IGeneratedReportRepository generatedReportRepository;

    @Autowired
    public ResetService(ICallCenterAgentRepository agentRepository, ICallRepository callRepository, IGeneratedReportRepository generatedReportRepository) {
        this.agentRepository =agentRepository;
        this.callRepository = callRepository;
        this.generatedReportRepository = generatedReportRepository;
    }

    @Override
    @Scheduled(fixedRate = 300000) // Run every 5 minutes (300,000 milliseconds)
    public void resetDatabase() {
        agentRepository.resetAgents();
        callRepository.truncateCalls();
        generatedReportRepository.truncateGeneratedReports();
    }
}
