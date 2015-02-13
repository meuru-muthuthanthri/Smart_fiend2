package smartfriend.util.reports.reportmanagement;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import smartfriend.util.general.MainConfiguration;
import smartfriend.util.reports.dto.NumberLearningDTO;
import smartfriend.util.reports.dto.ReportDTO;

/**
 *
 * @author Nilaksha
 */
public class ReportEngine {

    private static DefaultTableModel tableModel;

    public static String generateReport(ReportDTO reportDetails) {

        String jrxmlFilePath = null;
        String jasperFilePath = null;
        String printFileName = null;
        String pdfFilePath = null;
        String pdfFile = null;
        String headerImagePath = null;
        Map appData = new HashMap();

        try {
            jrxmlFilePath = MainConfiguration.getCurrentDirectory()
                    + MainConfiguration.getInstance().getProperty("numberLearningJRXMLPath");
            jasperFilePath = MainConfiguration.getCurrentDirectory()
                    + MainConfiguration.getInstance().getProperty("numberLearningJasperPath");
            headerImagePath = MainConfiguration.getCurrentDirectory()
                    + MainConfiguration.getInstance().getProperty("reportHeaderImagePath");

            if (reportDetails.getReportType().equalsIgnoreCase("NUMBERLEARNING")) {

                NumberLearningDTO details = (NumberLearningDTO) reportDetails;
                tableModelData(details);
                pdfFilePath = MainConfiguration.getCurrentDirectory()
                        + MainConfiguration.getInstance().getProperty("reportPDFPath")
                        + details.getReportID() + "-" + details.getDate() + ".pdf";
                pdfFile = MainConfiguration.getInstance().getProperty("reportPDF")
                        + details.getReportID() + "-" + details.getDate() + ".pdf";

                appData.put("headerimagepath", headerImagePath);
                appData.put("childName", details.getChildName());
                appData.put("date", details.getDate());
                appData.put("time", details.getTime());
                appData.put("marks", details.getMarks());
                appData.put("message", details.getMessage());
            }
        } catch (IOException ex) {
            Logger.getLogger(ReportEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            JasperCompileManager.compileReportToFile(jrxmlFilePath);
            printFileName = JasperFillManager.fillReportToFile(jasperFilePath,
                    appData, new JRTableModelDataSource(tableModel));
            JasperExportManager.exportReportToPdfFile(printFileName, pdfFilePath);

        } catch (JRException ex) {

            Logger.getLogger(ReportEngine.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        if (pdfFilePath != null) {
            return pdfFile;
        } else {
            return null;
        }
    }

    private static void tableModelData(NumberLearningDTO numberLearningDTO) {
        HashMap<String, String> results = numberLearningDTO.getResults();
        String[] columnNames = {"Number", "Result"};
        String[][] data = {
            {"1", results.get("0")},
            {"2", results.get("1")},
            {"3", results.get("2")},
            {"4", results.get("3")},
            {"5", results.get("4")},
            {"6", results.get("5")},
            {"7", results.get("6")},
            {"8", results.get("7")},
            {"9", results.get("8")},
            {"10", results.get("9")}
        };
        tableModel = new DefaultTableModel(data, columnNames);
    }

//    public static void main(String[] args) {
//
//        Date date = new Date();
//        NumberLearningDTO details = new NumberLearningDTO();
//        details.setChildName("Isuri");
//        details.setAppID("app1");
//        details.setReportID("rpt1");
//        details.setDateTime(date);
//        details.setMarks("7");
//        details.setMessage("Hello Mommy Daddy!");
//        details.setResults(null);
//        ReportEngine.generateReport(details);
//    }
}
