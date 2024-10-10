package com.eastnets.call_center.serviceInterfaces;

import com.eastnets.call_center.model.GeneratedReport;
import net.sf.jasperreports.engine.JRException;

import java.util.List;

public interface IJasperReportService {
    byte[] generatePdfReport(List<GeneratedReport> reports) throws JRException;
}
