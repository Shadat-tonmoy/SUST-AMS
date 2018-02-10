package shadattonmoy.ams.attendance;

import android.widget.Toast;

import shadattonmoy.ams.ClassInstanceListActivity;

/**
 * Created by Shadat Tonmoy on 2/7/2018.
 */

public class ClassInstance {
    private String date,month,year,numericDate;
    private int totalStudent,totalPresent,classInstanceid,totalAbsent;
    private ClassDate classDate;

    public ClassInstance(ClassDate date) {
        this.classDate= date;
    }

    public ClassInstance() {

    }

    public String getDate() {
        String date = classDate.getDateValue();
        String day = classDate.getDayValue();
        String month = classDate.getMonthValue();
        String year = classDate.getYearValue();
        String formattedDate = date+","+month+","+year+","+day;
        return formattedDate;
    }

    public void setDate(String date) {
        classDate = new ClassDate();
        String[] dateArray = date.split(",");
        classDate.setDateValue(dateArray[0]);
        classDate.setMonthValue(dateArray[1]);
        classDate.setYearValue(dateArray[2]);
        classDate.setDayValue(dateArray[3]);
    }

    public int getTotalStudent() {
        return totalStudent;
    }

    public void setTotalStudent(int totalStudent) {
        this.totalStudent = totalStudent;
    }

    public int getTotalPresent() {
        return totalPresent;
    }

    public void setTotalPresent(int totalPresent) {
        this.totalPresent = totalPresent;
    }

    public int getClassInstanceid() {
        return classInstanceid;
    }

    public void setClassInstanceid(int classInstanceid) {
        this.classInstanceid = classInstanceid;
    }

    public int getTotalAbsent() {
        return totalAbsent;
    }

    public void setTotalAbsent(int totalAbsent) {
        this.totalAbsent = totalAbsent;
    }

    public ClassDate getClassDate() {
        return classDate;
    }

    public void setClassDate(ClassDate classDate) {
        this.classDate = classDate;
    }
}
