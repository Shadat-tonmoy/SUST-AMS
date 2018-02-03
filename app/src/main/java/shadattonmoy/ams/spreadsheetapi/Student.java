package shadattonmoy.ams.spreadsheetapi;

/**
 * Created by Shadat Tonmoy on 2/1/2018.
 */

public class Student {
    private String name,regNo,email;
    private boolean regular;

    public Student(String name, String regNo, String email,boolean regular) {
        this.name = name;
        this.regNo = regNo;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRegular() {
        return regular;
    }

    public void setRegular(boolean regular) {
        this.regular = regular;
    }
}
