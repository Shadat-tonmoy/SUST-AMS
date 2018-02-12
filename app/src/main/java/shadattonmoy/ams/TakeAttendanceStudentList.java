package shadattonmoy.ams;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private int classInstanceId;
    private Cursor attendanceCursor;
    private Map presentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance_student_list);
        course = (Course) getIntent().getSerializableExtra("Course");
        classInstanceId = getIntent().getIntExtra("ClassInstanceId",-1);
        Log.e("Class ID",classInstanceId+"");
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
        initSQLiteDB();

        /*
        * find the no course found UI from xml for toggle visibility
        * */
        noStudentFoundMsg = (RelativeLayout) findViewById(R.id.take_attendance_no_student_found_msg);
        noStudentFoundText = (TextView) findViewById(R.id.take_attendance_no_student_found_txt);


        studentList = (ListView) findViewById(R.id.take_attendance_student_list);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.student_list_activity_layout);

        presentMap = new HashMap();
        getAttendance();
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
            studentAdapter.setPresentFlagMap(presentMap);

            studentAdapter.setFragmentManager(getSupportFragmentManager());
            studentAdapter.setShowVertIcon(false);
            studentAdapter.setShowPresentAbsentRadio(true);
            studentList.setAdapter(studentAdapter);

        }
    }

    public void getAttendance()
    {
        attendanceCursor = sqLiteAdapter.getAttendance(String.valueOf(classInstanceId));
        Log.e("Total Attendance","Found "+attendanceCursor.getCount());
        while (attendanceCursor.moveToNext())
        {
            int indexOfStudentId = attendanceCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.STUDENT_ID);
            int indexOfStudentPresent= attendanceCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.IS_PRESENT);
            int indexOfAttendanceId = attendanceCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.ATTENDANCE_ID);
            int studentId = attendanceCursor.getInt(indexOfStudentId);
            int isPresent = attendanceCursor.getInt(indexOfStudentPresent);
            int attendanceId = attendanceCursor.getInt(indexOfAttendanceId);
            presentMap.put((long)studentId,isPresent);
        }
    }

    public void insertAttendance()
    {
        Log.e("Method","Insert Attendance");
        int totalPresent = 0;
        int totalAbsent = 0;
        for(Student student : students)
        {
            if(student.getPresent()==1)
                totalPresent++;
            else totalAbsent++;
            Log.e("Insert Info","Student ID "+student.getStudentId()+" Course ID "+course.getCourseId()+" Present "+student.getPresent());
            sqLiteAdapter.addAttendanceToDB(classInstanceId,student.getStudentId(),student.getPresent());
        }

    }

    public void updateAttendance()
    {
        Log.e("Method","Update Attendance");
        for(Student student : students)
        {
            int present = (int) presentMap.get(student.getStudentId());
            int newValue;
            if(present==1)
                newValue=0;
            else newValue=1;
            if(student.getPresent()==present)
                Log.e("Not Changed ",student.getRegNo());
            else
            {
                Log.e("Changed ",student.getRegNo());
                presentMap.put(student.getStudentId(),newValue);
                int res = sqLiteAdapter.updateAttendance(classInstanceId,student.getStudentId(),newValue);
                if(res>0)
                {
                    Log.e("Updated Messaage",student.getRegNo()+" is updated with "+newValue);
                }
            }
        }
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public static void setStudents(ArrayList<Student> students) {
        TakeAttendanceStudentList.students = students;
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(TakeAttendanceStudentList.this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Total Attendacne","Size "+attendanceCursor.getCount());
        if(attendanceCursor.getCount()==0)
            insertAttendance();
        else updateAttendance();
        Toast.makeText(TakeAttendanceStudentList.this,"All Changes are saved",Toast.LENGTH_SHORT).show();
    }
}


