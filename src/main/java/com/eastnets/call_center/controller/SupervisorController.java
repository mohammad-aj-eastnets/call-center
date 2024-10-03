package com.eastnets.call_center.controller;

import com.eastnets.call_center.model.Supervisor;
import com.eastnets.call_center.serviceInterfaces.ISupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;

@Named
@RequestScope
@Controller
public class SupervisorController {
    private ISupervisorService supervisorService;
    private Supervisor supervisor;

    @Autowired
    public SupervisorController(ISupervisorService supervisorService) {
        this.supervisorService = supervisorService;
    }

    // Empty constructor
    public SupervisorController() {
        // Default constructor
    }

    @PostConstruct
    public void init() {
        supervisor = getSupervisor();
    }

    public Supervisor getSupervisor() {
        if (supervisor == null) {
            supervisor = new Supervisor();
        }
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public String login() {
        Supervisor loggedInSupervisor = supervisorService.login(supervisor.getUsername(), supervisor.getPassword());
        if (loggedInSupervisor != null) {
            String supervisor_name = loggedInSupervisor.getUsername();
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            try {
                externalContext.redirect(externalContext.getRequestContextPath() + "/faces/views/dashboard.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null; // Return null to prevent JSF navigation
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failed", "Invalid username or password."));
            return null;
        }
    }
}
