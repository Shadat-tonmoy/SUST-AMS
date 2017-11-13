package shadattonmoy.ams;

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

    private TextView courseCodeView,courseTitleView,courseSessionView,courseDeptView;
    public CourseAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<Course> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        if(row==null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.course_single_row,parent,false);
        }
        Course course = getItem(position);
        TextView courseIconView = (TextView) row.findViewById(R.id.course_icon);
        TextView courseCodeView = (TextView) row.findViewById(R.id.course_code);
        TextView courseTitleView = (TextView) row.findViewById(R.id.course_title);
        TextView courseSessionView = (TextView) row.findViewById(R.id.course_session);



        String courseCode = course.getCourseCode();
        String courseTitle = course.getCourseTitle();
        String courseSession = course.getCourseSession();
        String iconText = String.valueOf(courseCode.charAt(0)+""+courseCode.charAt(1)+""+courseCode.charAt(2));

        courseIconView.setText(iconText);
        courseCodeView.setText(courseCode);
        courseTitleView.setText(courseTitle);
        courseSessionView.setText(courseSession);

        return row;

    }
}
