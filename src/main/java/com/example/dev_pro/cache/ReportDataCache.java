package com.example.dev_pro.cache;

import com.example.dev_pro.model.Report;

public interface ReportDataCache {

    /**
     * Метод по сохранению отчета в словарь "reports"
     * @param adopterId идентификатор усыновителя
     * @param report отчет
     */
    void saveReport(Long adopterId, Report report);


    /**
     * Метод по получению из словаря "reports" отчета по идентификатору усыновителя
     * @param adopterId идентификатор усыновителя
     * @return отчет
     */
    Report getReport(Long adopterId);
}
