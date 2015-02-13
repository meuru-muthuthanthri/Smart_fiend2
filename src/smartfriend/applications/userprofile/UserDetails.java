package smartfriend.applications.userprofile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import smartfriend.util.general.MainConfiguration;

/**
 *
 * @author Isuri
 */
public class UserDetails {

    private String childName;
    private String email;
    private int age;
    
    public UserDetails(){
        readFile();
    }

    public String getParentEmail() {
        return email;
    }

    public String getChildName() {
        return childName;
    }

    public int getChildAge() {
        return age;
    }

    public void readFile() {

        BufferedReader br;
        int year,month,day;
        try {
            
            String currentLine;
            br = new BufferedReader(new FileReader(MainConfiguration.getCurrentDirectory() + MainConfiguration.getInstance().getProperty("userDetailsFilePath")));

            while ((currentLine = br.readLine()) != null) {

                String words[] = currentLine.split(" ");

                childName = words[0].trim();
                email =words[1].trim();
                year = Integer.parseInt(words[2].trim());
                month= Integer.parseInt(words[3].trim());
                day= Integer.parseInt(words[4].trim());

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date birthDate = sdf.parse(day+"/"+month+"/"+year); 
                Age calculatedAge = calculateAge(birthDate);
                age = calculatedAge.getYears();
//                System.out.println(getChildName()+" "+getParentEmail()+" "+ getChildAge());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UserDetails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UserDetails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(UserDetails.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Age calculateAge(Date birthDate) {
         
        int years, months, days;
        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());
        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        //Get difference between months
        months = currMonth - birthMonth;
        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                months--;
            }
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }
        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE)) {
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        } else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }
        //Create new Age object
        return new Age(days, months, years);
    }
}
