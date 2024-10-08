package com.eastnets.call_center.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallConfig {

    @Value("${call.duration.threshold.seconds}")
    private long callDurationThreshold;

    @Value("${call.close.percentage}")
    private double callClosePercentage;

    public long getCallDurationThreshold() {
        return callDurationThreshold;
    }

    public double getCallClosePercentage() {
        return callClosePercentage;
    }
}
