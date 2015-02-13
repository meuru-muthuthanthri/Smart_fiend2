package smartfriend.util.reports.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Nilaksha
 */
public class NumberLearningDTO extends ReportDTO {

    private String childName;
    private String appID;
    private String reportID;
    private Date dateTime;
    private String marks;
    private String message;
    private HashMap<String, String> results;
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final DateFormat timeFormat = new SimpleDateFormat("h:mm a");

    public NumberLearningDTO() {

        super.setReportType("NUMBERLEARNING");
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getReportID() {
        return reportID;
    }

    public void setReportID(String reportID) {
        this.reportID = reportID;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getDate() {
        return dateFormat.format(dateTime);
    }

    public String getTime() {
        return timeFormat.format(dateTime);
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, String> getResults() {
        return results;
    }

    public void setResults(HashMap<String, String> results) {
        this.results = results;
    }
}
