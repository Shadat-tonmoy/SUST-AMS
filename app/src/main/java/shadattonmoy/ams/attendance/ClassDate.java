package shadattonmoy.ams.attendance;

import java.util.Calendar;

/**
 * Created by Shadat Tonmoy on 2/8/2018.
 */

public class ClassDate {
    private String dateValue,dayValue,monthValue,yearValue;
    private String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    private String[] days = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    private int year,month,date,day;

    public ClassDate() {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        date = c.get(Calendar.DAY_OF_MONTH);
        day = c.get(Calendar.DAY_OF_WEEK)-1;
        dateValue = String.valueOf(date);
        yearValue = String.valueOf(year);
        dayValue = days[day];
        monthValue = months[month];
    }

    public String getDateValue() {
        return dateValue;
    }

    public String getDayValue() {
        return dayValue;
    }

    public String getMonthValue() {
        return monthValue;
    }

    public String getYearValue() {
        return yearValue;
    }

    public void setDateValue(String dateValue) {
        this.dateValue = dateValue;
    }

    public void setDayValue(String dayValue) {
        this.dayValue = dayValue;
    }

    public void setMonthValue(String monthValue) {
        this.monthValue = monthValue;
    }

    public void setYearValue(String yearValue) {
        this.yearValue = yearValue;
    }
}


