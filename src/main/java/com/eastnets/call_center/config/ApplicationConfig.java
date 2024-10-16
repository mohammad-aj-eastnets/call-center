package com.eastnets.call_center.config;

import com.eastnets.call_center.repository.*;
import com.eastnets.call_center.repositoryInterfaces.ICallCenterAgentRepository;
import com.eastnets.call_center.repositoryInterfaces.ICallRepository;
import com.eastnets.call_center.repositoryInterfaces.IGeneratedReportRepository;
import com.eastnets.call_center.repositoryInterfaces.ISupervisorRepository;
import com.eastnets.call_center.service.*;
import com.eastnets.call_center.serviceInterfaces.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan("com.eastnets")
public class ApplicationConfig {

    @Bean
    public ICallCenterAgentRepository agentRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new CallCenterAgentRepository(jdbcTemplate);
    }

    @Bean
    public ICallCenterAgentService agentService(ICallCenterAgentRepository agentRepository) {
        return new CallCenterAgentService(agentRepository);
    }

    @Bean
    public ISupervisorRepository supervisorRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new SupervisorRepository(jdbcTemplate);
    }

    @Bean
    public ISupervisorService supervisorService(ISupervisorRepository supervisorRepository) {
        return new SupervisorService(supervisorRepository);
    }

    @Bean
    public ICallRepository callRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new CallRepository(jdbcTemplate);
    }

    @Bean
    public ICallService callService(ICallRepository callRepository, ICallCenterAgentService agentService, CallConfig callConfig) {
        return new CallService(callRepository, agentService, callConfig);
    }

    @Bean
    public IGeneratedReportRepository generatedReportRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new GeneratedReportRepository(jdbcTemplate);
    }

    @Bean
    public IGeneratedReportService generatedReportService(IGeneratedReportRepository generatedReportRepository, ICallRepository callRepository, ICallCenterAgentService agentService) {
        return new GeneratedReportService(generatedReportRepository, callRepository, agentService);
    }

    @Bean
    public IJasperReportService jasperReportService() {
        return new JasperReportService();
    }

    @Bean
    public IDashboardService dashboardService(ICallService callService, ICallCenterAgentService agentService) {
        return new DashboardService(callService, agentService);
    }

    public IResetService resetService(ICallCenterAgentRepository agentRepository, ICallRepository callRepository, IGeneratedReportRepository generatedReportRepository) {
        return new ResetService(agentRepository,callRepository,generatedReportRepository);
    }

}
