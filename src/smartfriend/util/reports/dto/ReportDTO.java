/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.util.reports.dto;

/**
 *
 * @author Nilaksha
 */
public abstract class ReportDTO {

    private String reportType;

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportType() {
        return reportType;
    }   
}
