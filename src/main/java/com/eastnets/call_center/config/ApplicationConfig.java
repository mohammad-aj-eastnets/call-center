package com.eastnets.call_center.config;

import com.eastnets.call_center.repository.*;
import com.eastnets.call_center.repositoryInterfaces.ICallCenterAgentRepository;
import com.eastnets.call_center.repositoryInterfaces.ICallRepository;
import com.eastnets.call_center.repositoryInterfaces.IGeneratedReportRepository;
import com.eastnets.call_center.repositoryInterfaces.ISupervisorRepository;
import com.eastnets.call_center.service.*;
import com.eastnets.call_center.serviceInterfaces.ICallCenterAgentService;
import com.eastnets.call_center.serviceInterfaces.ICallService;
import com.eastnets.call_center.serviceInterfaces.IGeneratedReportService;
import com.eastnets.call_center.serviceInterfaces.ISupervisorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
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
    public ICallService callService(ICallRepository callRepository) {
        return new CallService(callRepository);
    }

    @Bean
    public IGeneratedReportRepository generatedReportRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        return new GeneratedReportRepository(jdbcTemplate);
    }

    @Bean
    public IGeneratedReportService generatedReportService(IGeneratedReportRepository generatedReportRepository) {
        return new GeneratedReportService(generatedReportRepository);
    }


}
