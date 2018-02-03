package shadattonmoy.ams.spreadsheetapi;

import java.util.List;

/**
 * Created by Shadat Tonmoy on 2/2/2018.
 */

public class SpreadSheetData {
    private List<Student> studentList;

    public SpreadSheetData(List<Student> studentList) {
        this.studentList = studentList;
    }

    public SpreadSheetData() {

    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

}
