package com.example.dev_pro.cache.impl;


import com.example.dev_pro.cache.ReportDataCache;
import com.example.dev_pro.model.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory cache.
 * reports: adopter_id and report
 */

@Component
@RequiredArgsConstructor
public class ReportDataCacheImpl implements ReportDataCache {

    private final Map<Long, Report> reports = new HashMap<>();

    @Override
    public Report getReport(Long adopterId) {
        Report report = reports.get(adopterId);
        if (report == null) {
            report = new Report();
        }
        return report;
    }

    @Override
    public void saveReport(Long adopterId, Report report) {
        reports.put(adopterId, report);
    }

}
