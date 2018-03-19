package shadattonmoy.ams;

/**
 * Created by Shadat Tonmoy on 2/27/2018.
 */

public class StudentPastRecord {
    int classWeight,isPresent;
    String classDate;

    public StudentPastRecord(int classWeight, int isPresent, String classDate) {
        this.classWeight = classWeight;
        this.isPresent = isPresent;
        this.classDate = classDate;
    }

    public StudentPastRecord() {

    }

    public int getClassWeight() {
        return classWeight;
    }

    public void setClassWeight(int classWeight) {
        this.classWeight = classWeight;
    }

    public int getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }
}
