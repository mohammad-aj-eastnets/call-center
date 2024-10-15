package com.eastnets.call_center.serviceInterfaces;

import org.primefaces.model.chart.PieChartModel;

public interface IDashboardService {
    PieChartModel createPieModel();
    String getAverageTalkTime();
    String getLongestTalkTime();
    int getTotalCalls();
}
