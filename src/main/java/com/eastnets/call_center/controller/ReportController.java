package com.eastnets.call_center.controller;

import com.eastnets.call_center.model.GeneratedReport;
import com.eastnets.call_center.serviceInterfaces.IGeneratedReportService;
import com.eastnets.call_center.serviceInterfaces.IJasperReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final IGeneratedReportService generatedReportService;
    private final IJasperReportService jasperReportService;

    @Autowired
    public ReportController(IGeneratedReportService generatedReportService, IJasperReportService jasperReportService) {
        this.generatedReportService = generatedReportService;
        this.jasperReportService = jasperReportService;
    }

    @GetMapping("/pdf")
    @ResponseBody
    public void generatePdfReport(HttpServletResponse response) {
        List<GeneratedReport> reports = generatedReportService.getAllReports();
        byte[] pdfBytes;
        try {
            pdfBytes = jasperReportService.generatePdfReport(reports);
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
            response.getOutputStream().write(pdfBytes);
        } catch (JRException | IOException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
