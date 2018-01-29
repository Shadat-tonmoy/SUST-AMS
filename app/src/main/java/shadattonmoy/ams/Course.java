package shadattonmoy.ams;

import java.io.Serializable;

/**
 * Created by Shadat Tonmoy on 11/13/2017.
 */

public class Course implements Serializable{
    private String courseCode,courseTitle,courseSession;
    long courseId;
    private boolean isUpdated;

    public Course()
    {

    }

    public Course(String courseCode, String courseTitle, String courseSession, long courseId) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.courseSession = courseSession;
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseSession() {
        return courseSession;
    }

    public void setCourseSession(String courseSession) {
        this.courseSession = courseSession;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", courseSession='" + courseSession + '\'' +
                ", courseId=" + courseId +
                '}';
    }


}
