package shadattonmoy.ams;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Shadat Tonmoy on 11/13/2017.
 */

public class CourseAddBottomSheet extends BottomSheetDialogFragment {

    private LinearLayout bottomSheetEditMenu,bottomSheetDeleteMenu,bottomSheetCloneMenu;
    private Course course;
    private int viewPosition;
    private ListView courseList;
    private CourseAdapter adapter;
    private ArrayList<Course> courses;
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.course_add_bottom_sheet, null);
        initNodes(contentView);
        setNodesTouchListener();
        dialog.setContentView(contentView);
    }

    /*
    * method to initialize bottom sheet nodes
    * */

    public void initNodes(View view)
    {
        bottomSheetEditMenu = (LinearLayout) view.findViewById(R.id.bottom_sheet_edit_menu);
        bottomSheetDeleteMenu = (LinearLayout) view.findViewById(R.id.bottom_sheet_delete_menu);
        //bottomSheetCloneMenu = (LinearLayout) view.findViewById(R.id.bottom_sheet_clone_menu);
    }


    public void setCourse(Course course) {
        this.course = course;
    }

    /*
        * method to handle bottom sheet menu click
        * */
    public void setNodesTouchListener()
    {
        bottomSheetEditMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"Edit Course",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(),CourseEditActivity.class);
                CourseEditActivity.setCourse(course);
                startActivity(intent);
            }
        });

        bottomSheetDeleteMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseDeleteConfirmationDialog courseDeleteConfirmationDialog = new CourseDeleteConfirmationDialog();
                courseDeleteConfirmationDialog.setCourse(course);
                courseDeleteConfirmationDialog.setViewPosition(viewPosition);
                courseDeleteConfirmationDialog.setCourseList(courseList);
                courseDeleteConfirmationDialog.setCourses(courses);
                courseDeleteConfirmationDialog.setAdapter(adapter);
                courseDeleteConfirmationDialog.show(getActivity().getFragmentManager(),"Confirmation");



            }
        });




//        bottomSheetCloneMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity().getApplicationContext(),"Clone Course",Toast.LENGTH_SHORT).show();
//            }
//        });




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

    public CourseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CourseAdapter adapter) {
        this.adapter = adapter;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
