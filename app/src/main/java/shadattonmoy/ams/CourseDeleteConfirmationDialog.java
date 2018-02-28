package shadattonmoy.ams;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Shadat Tonmoy on 1/29/2018.
 */

public class CourseDeleteConfirmationDialog extends DialogFragment {

    private View view;
    private Course course;
    private SQLiteAdapter sqLiteAdapter;
    private int viewPosition;
    private ListView courseList;
    private ArrayList<Course> courses;
    private CourseAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.course_delete_confirmation_dialog,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.confirmation_dialog_image);
        TextView textView = (TextView) view.findViewById(R.id.confirmation_dialog_msg);
        textView.setText("This operation will permanently delete the course and all the related information with the course. Are you sure to proceed? ");
        initSQLiteDB();

        builder.setView(view);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCourse();
            }
        });
        return builder.create();
    }

    private void deleteCourse()
    {
        int result = sqLiteAdapter.deleteCourse(course.getCourseId());
        if(result>0)
        {
            CourseAdapter.hideBottomSheet();
            courses.remove(viewPosition);
            adapter.notifyDataSetChanged();
            Toast.makeText(getActivity().getApplicationContext(),"Course Records Deleted",Toast.LENGTH_SHORT).show();

        }

    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(getActivity().getApplicationContext());
    }

    public int getViewPosition() {
        return viewPosition;
    }

    public void setViewPosition(int viewPosition) {
        this.viewPosition = viewPosition;
    }

    public ListView getCourseList() {
        return courseList;
    }

    public void setCourseList(ListView courseList) {
        this.courseList = courseList;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public CourseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CourseAdapter adapter) {
        this.adapter = adapter;
    }
}
