package com.eastnets.call_center.service;

import com.eastnets.call_center.model.GeneratedReport;
import com.eastnets.call_center.serviceInterfaces.IJasperReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperReportService implements IJasperReportService {

    @Override
    public byte[] generatePdfReport(List<GeneratedReport> reports) throws JRException {
        // Create JasperDesign
        JasperDesign jasperDesign = new JasperDesign();
        jasperDesign.setName("GeneratedReport");
        jasperDesign.setPageWidth(595);
        jasperDesign.setPageHeight(842);
        jasperDesign.setColumnWidth(555);
        jasperDesign.setColumnSpacing(5);
        jasperDesign.setLeftMargin(20);
        jasperDesign.setRightMargin(20);
        jasperDesign.setTopMargin(20);
        jasperDesign.setBottomMargin(20);
        jasperDesign.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);

        // Add fields
        addField(jasperDesign, "agentID", Long.class);
        addField(jasperDesign, "totalNumberOfCalls", Long.class);
        addField(jasperDesign, "totalTalkTime", Long.class);
        addField(jasperDesign, "longestTalkTime", Long.class);
        addField(jasperDesign, "shortestTalkTime", Long.class);
        addField(jasperDesign, "totalTimeNotReady", Long.class);
        addField(jasperDesign, "avgRecOnTotal", Double.class);

        // Add title band
        JRDesignBand titleBand = new JRDesignBand();
        titleBand.setHeight(50);
        JRDesignStaticText titleText = new JRDesignStaticText();
        titleText.setText("Generated Report");
        titleText.setX(0);
        titleText.setY(0);
        titleText.setWidth(555);
        titleText.setHeight(50);
        titleText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        titleText.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        titleBand.addElement(titleText);
        jasperDesign.setTitle(titleBand);

        // Add column headers
        JRDesignBand columnHeaderBand = new JRDesignBand();
        columnHeaderBand.setHeight(20);
        addColumnHeader(columnHeaderBand, "Agent ID", 0);
        addColumnHeader(columnHeaderBand, "Total Calls", 80);
        addColumnHeader(columnHeaderBand, "Total Talk Time", 160);
        addColumnHeader(columnHeaderBand, "Longest Talk Time", 240);
        addColumnHeader(columnHeaderBand, "Shortest Talk Time", 320);
        addColumnHeader(columnHeaderBand, "Total Time Not Ready", 400);
        addColumnHeader(columnHeaderBand, "Avg Rec On Total", 480);
        jasperDesign.setColumnHeader(columnHeaderBand);

        // Add detail band
        JRDesignBand detailBand = new JRDesignBand();
        detailBand.setHeight(20);
        addTextField(detailBand, "agentID", 0);
        addTextField(detailBand, "totalNumberOfCalls", 80);
        addTextField(detailBand, "totalTalkTime", 160);
        addTextField(detailBand, "longestTalkTime", 240);
        addTextField(detailBand, "shortestTalkTime", 320);
        addTextField(detailBand, "totalTimeNotReady", 400);
        addTextField(detailBand, "avgRecOnTotal", 480);
        ((JRDesignSection) jasperDesign.getDetailSection()).addBand(detailBand);

        // Compile the design
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        // Create a data source from the report data
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reports);

        // Parameters for the report
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Call Center Management System");

        // Fill the report with data
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export the report to a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();
    }

    private void addField(JasperDesign jasperDesign, String fieldName, Class<?> fieldType) throws JRException {
        JRDesignField field = new JRDesignField();
        field.setName(fieldName);
        field.setValueClass(fieldType);
        jasperDesign.addField(field);
    }

    private void addTextField(JRDesignBand band, String fieldName, int xPosition) {
        JRDesignTextField textField = new JRDesignTextField();
        textField.setX(xPosition);
        textField.setY(0);
        textField.setWidth(80);
        textField.setHeight(20);
        textField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
        textField.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        textField.setExpression(new JRDesignExpression("$F{" + fieldName + "}"));
        band.addElement(textField);
    }

    private void addColumnHeader(JRDesignBand band, String headerText, int xPosition) {
        JRDesignStaticText staticText = new JRDesignStaticText();
        staticText.setText(headerText);
        staticText.setX(xPosition);
        staticText.setY(0);
        staticText.setWidth(80);
        staticText.setHeight(20);
        staticText.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
        staticText.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
        band.addElement(staticText);
    }
}
