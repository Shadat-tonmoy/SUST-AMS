package shadattonmoy.ams;

/**
 * Created by Shadat Tonmoy on 11/13/2017.
 */

public class Course {
    private String courseCode,courseTitle,courseSession;
    long courseId;

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
}
