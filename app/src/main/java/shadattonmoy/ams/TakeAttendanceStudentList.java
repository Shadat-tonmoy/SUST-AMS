package shadattonmoy.ams;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import shadattonmoy.ams.spreadsheetapi.SpreadSheetActivity;
import shadattonmoy.ams.spreadsheetapi.StudentAdapter;

public class TakeAttendanceStudentList extends AppCompatActivity {

    private String classInstanceDate;
    private SQLiteAdapter sqLiteAdapter;
    private RelativeLayout noStudentFoundMsg;
    private FloatingActionButton studentAddFab;
    private ListView studentList;
    private static ArrayList<Student> students;
    private CoordinatorLayout coordinatorLayout;
    private Course course;
    private TextView noStudentFoundText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance_student_list);
        course = (Course) getIntent().getSerializableExtra("Course");
        initialize();
        initStudents();
    }

    public void initialize()
    {

        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Take Attendance");


        /*
        * set toolbar navigation Icon
        * */
        toolbar.setNavigationIcon(R.drawable.back);


        /*
        * set toolbar navigation Icon Click Listener
        * */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /*
        * initialize sqite database
        * */
        //initSQLiteDB();

        /*
        * find the no course found UI from xml for toggle visibility
        * */
        noStudentFoundMsg = (RelativeLayout) findViewById(R.id.take_attendance_no_student_found_msg);
        noStudentFoundText = (TextView) findViewById(R.id.take_attendance_no_student_found_txt);


        studentList = (ListView) findViewById(R.id.take_attendance_student_list);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.student_list_activity_layout);
    }



    public void initStudents()
    {
        if(students.size()<=0)
        {
            noStudentFoundMsg.setVisibility(View.VISIBLE);
            noStudentFoundText.setText("Sorry!! No Student Record Found For "+course.getCourseCode());

        }
        else
        {
            noStudentFoundMsg.setVisibility(View.GONE);
            StudentAdapter studentAdapter = new StudentAdapter(TakeAttendanceStudentList.this,R.layout.student_single_row,R.id.student_icon,students);
            studentAdapter.setFragmentManager(getSupportFragmentManager());
            studentAdapter.setShowVertIcon(false);
            studentAdapter.setShowPresentAbsentRadio(true);
            studentList.setAdapter(studentAdapter);

        }
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public static void setStudents(ArrayList<Student> students) {
        TakeAttendanceStudentList.students = students;
    }
}
