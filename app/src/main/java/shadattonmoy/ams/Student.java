package shadattonmoy.ams;

/**
 * Created by Shadat Tonmoy on 1/30/2018.
 */

public class Student {
    private String name,regNo,email;
    private long studentId,courseId;
    private int regular,present;

    public Student(String name, String regNo, int regular) {
        this.name = name;
        this.regNo = regNo;
        this.regular = regular;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public int isRegular() {
        return regular;
    }

    public void setRegular(int regular) {
        this.regular = regular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPresent() {
        return present;
    }

    public void setPresent(int present) {
        this.present = present;
    }
}
