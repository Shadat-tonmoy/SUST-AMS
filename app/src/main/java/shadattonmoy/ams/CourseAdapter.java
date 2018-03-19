package shadattonmoy.ams;

import android.os.CpuUsageInfo;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Shadat Tonmoy on 11/13/2017.
 */

public class CourseAdapter extends ArrayAdapter<Course> {

    private FragmentManager fragmentManager;
    private TextView courseCodeView,courseTitleView,courseSessionView,courseDeptView;
    private static CourseAddBottomSheet courseAddBottomSheet;
    private ListView courseList;
    private boolean showVertIcon;
    private List<Course> courses;
    public CourseAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<Course> objects) {
        super(context, resource, textViewResourceId, objects);
        this.courses = objects;
        showVertIcon = true;
        courseAddBottomSheet = new CourseAddBottomSheet();
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        if(row==null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.course_single_row,parent,false);
        }

        /*
        * find views by their IDs
        * */
        final Course course = getItem(position);
        TextView courseIconView = (TextView) row.findViewById(R.id.course_icon);
        TextView courseCodeView = (TextView) row.findViewById(R.id.course_code);
        TextView courseTitleView = (TextView) row.findViewById(R.id.course_title);
        TextView courseSessionView = (TextView) row.findViewById(R.id.course_session);


        /*
        * get the specific attributes for a particular course
        * */
        String courseCode = course.getCourseCode();
        String courseTitle = course.getCourseTitle();
        String courseSession = course.getCourseSession();
        String iconText = String.valueOf(courseCode.charAt(0)+""+courseCode.charAt(1)+""+courseCode.charAt(2));


        /*
        * set the attributes like text or clicklistener to the specific view/textview
        * */
        courseIconView.setText(iconText);
        courseCodeView.setText(courseCode);
        courseTitleView.setText(courseTitle);
        courseSessionView.setText(courseSession);

        if(course.isUpdated()) {
            courseIconView.setBackgroundResource(R.drawable.round_blue);
            //course.setUpdated(false);
            Setup.setUpdatedCourseId(-1);

        }

        if(showVertIcon)
        {
            LinearLayout moreVert = (LinearLayout) row.findViewById(R.id.course_more_vert_layout);
            ImageView moreVertIcon = (ImageView) moreVert.findViewById(R.id.course_more_vert_icon);
            moreVertIcon.setImageResource(R.drawable.more_vert);

            ClickHandler clickHandler = new ClickHandler(course,position,courseList, (ArrayList<Course>) courses,fragmentManager,this);
            clickHandler.setCourseAddBottomSheet(courseAddBottomSheet);
            moreVert.setOnClickListener(clickHandler);
//            moreVert.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    courseAddBottomSheet = new CourseAddBottomSheet();
//                    courseAddBottomSheet.setCourse(course);
//                    courseAddBottomSheet.setViewPosition(position);
//                    courseAddBottomSheet.setCourseList(courseList);
//                    courseAddBottomSheet.setCourses((ArrayList<Course>) courses);
//                    courseAddBottomSheet.show(fragmentManager,courseAddBottomSheet.getTag());
//                }
//            });
        }

        return row;

    }

    public static void hideBottomSheet()
    {
        courseAddBottomSheet.dismiss();

    }

    public boolean isShowVertIcon() {
        return showVertIcon;
    }

    public void setShowVertIcon(boolean showVertIcon) {
        this.showVertIcon = showVertIcon;
    }

    public ListView getCourseList() {
        return courseList;
    }

    public void setCourseList(ListView courseList) {
        this.courseList = courseList;
    }
}
class ClickHandler implements View.OnClickListener{

    private CourseAddBottomSheet courseAddBottomSheet;
    private Course course;
    private int viewPosition;
    private ListView courseList;
    private ArrayList<Course> courses;
    private FragmentManager fragmentManager;
    private CourseAdapter courseAdapter;

    public ClickHandler(Course course, int viewPosition, ListView courseList, ArrayList<Course> courses, FragmentManager fragmentManager,CourseAdapter courseAdapter) {
        this.course = course;
        this.viewPosition = viewPosition;
        this.courseList = courseList;
        this.courses = courses;
        this.fragmentManager = fragmentManager;
        this.courseAdapter = courseAdapter;
    }

    @Override
    public void onClick(View v) {
        //courseAddBottomSheet = new CourseAddBottomSheet();
        courseAddBottomSheet.setCourse(course);
        courseAddBottomSheet.setViewPosition(viewPosition);
        courseAddBottomSheet.setCourseList(courseList);
        courseAddBottomSheet.setCourses((ArrayList<Course>) courses);
        courseAddBottomSheet.setAdapter(courseAdapter);
        courseAddBottomSheet.show(fragmentManager,courseAddBottomSheet.getTag());
    }

    public CourseAddBottomSheet getCourseAddBottomSheet() {
        return courseAddBottomSheet;
    }

    public void setCourseAddBottomSheet(CourseAddBottomSheet courseAddBottomSheet) {
        this.courseAddBottomSheet = courseAddBottomSheet;
    }
}
