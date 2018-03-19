package shadattonmoy.ams;

/**
 * Created by shadat on 3/19/18.
 */

public class StudentPreviousRecord {
    private int classWeight,isPresent;
    private String classDate;

    public StudentPreviousRecord(int classWeight, int isPresent) {
        this.classWeight = classWeight;
        this.isPresent = isPresent;
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
