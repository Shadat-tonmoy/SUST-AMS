package shadattonmoy.ams;

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
import android.widget.TextView;

import java.util.List;

/**
 * Created by Shadat Tonmoy on 11/13/2017.
 */

public class CourseAdapter extends ArrayAdapter<Course> {

    private FragmentManager fragmentManager;
    private TextView courseCodeView,courseTitleView,courseSessionView,courseDeptView;

    public CourseAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<Course> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

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
        ImageView moreVert = (ImageView) row.findViewById(R.id.course_more_vert_icon);


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

        moreVert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseAddBottomSheet courseAddBottomSheet = new CourseAddBottomSheet();
                courseAddBottomSheet.setCourse(course);
                courseAddBottomSheet.show(fragmentManager,courseAddBottomSheet.getTag());
            }
        });

        return row;

    }
}
