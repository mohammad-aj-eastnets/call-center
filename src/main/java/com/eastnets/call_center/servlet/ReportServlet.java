package com.eastnets.call_center.servlet;

import com.eastnets.call_center.model.GeneratedReport;
import com.eastnets.call_center.service.GeneratedReportService;
import com.eastnets.call_center.service.JasperReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReportServlet extends HttpServlet {

    private GeneratedReportService generatedReportService;
    private JasperReportService jasperReportService;

    @Override
    public void init() throws ServletException {
        System.out.println("Initializing ReportServlet");
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        generatedReportService = context.getBean(GeneratedReportService.class);
        jasperReportService = context.getBean(JasperReportService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("ReportServlet doGet method called");
        List<GeneratedReport> reports = generatedReportService.getAllReports();
        if (reports == null || reports.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_NO_CONTENT, "No reports available to generate PDF");
            return;
        }

        byte[] pdfBytes;
        try {
            pdfBytes = jasperReportService.generatePdfReport(reports);
            if (pdfBytes == null || pdfBytes.length == 0) {
                resp.sendError(HttpServletResponse.SC_NO_CONTENT, "Generated PDF is empty");
                return;
            }
        } catch (JRException e) {
            e.printStackTrace(); // Log the exception for debugging
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF");
            return;
        }

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "attachment; filename=report.pdf");
        resp.getOutputStream().write(pdfBytes);
    }
}
