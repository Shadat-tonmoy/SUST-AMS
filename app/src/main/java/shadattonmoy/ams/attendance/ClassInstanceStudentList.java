package shadattonmoy.ams.attendance;

import java.io.Serializable;
import java.util.ArrayList;

import shadattonmoy.ams.Student;

/**
 * Created by Shadat Tonmoy on 2/13/2018.
 */

public class ClassInstanceStudentList implements Serializable{
    private ArrayList<Student> students;

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }
}
