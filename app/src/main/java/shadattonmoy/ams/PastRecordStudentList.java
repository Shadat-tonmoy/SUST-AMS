package shadattonmoy.ams;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import shadattonmoy.ams.attendance.ClassInstanceStudentList;
import shadattonmoy.ams.spreadsheetapi.StudentAdapter;

public class PastRecordStudentList extends AppCompatActivity {

    private SQLiteAdapter sqLiteAdapter;
    private RelativeLayout noStudentFoundMsg;
    private ListView studentList;
    private ArrayList<Student> students;
    private Course course;
    private TextView noStudentFoundText;
    private Cursor studentCursor;
    private StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_record_student_list);
        course = (Course) getIntent().getSerializableExtra("Course");
        initialize();
        getStudents();
    }

    public void initialize()
    {

        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(course.getCourseCode());


        /*
        * set toolbar navigation Icon
        * */
        toolbar.setNavigationIcon(R.drawable.back);

        setSupportActionBar(toolbar);


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
        noStudentFoundMsg = (RelativeLayout) findViewById(R.id.past_record_no_student_found_msg);
        noStudentFoundText = (TextView) findViewById(R.id.past_record_no_student_found_txt);


        studentList = (ListView) findViewById(R.id.past_record_student_list);

        students = new ArrayList<Student>();
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(PastRecordStudentList.this);
    }

    public void getStudents()
    {
        studentCursor = sqLiteAdapter.getStudents(Long.toString(course.getCourseId()));
        if(studentCursor.getCount()<=0)
        {
            noStudentFoundMsg.setVisibility(View.VISIBLE);
            noStudentFoundText.setText("Sorry!! No Student Record Found For "+course.getCourseCode());

        }
        else
        {
            noStudentFoundMsg.setVisibility(View.GONE);
            initStudents();
        }
    }

    public void initStudents()
    {
        if(studentCursor!=null)
        {

            while (studentCursor.moveToNext())
            {
                String[] columns = studentCursor.getColumnNames();
                String log = "";
                for(String column : columns)
                    log+=column;
                Log.e("Student Cursor Column ",log);
                int indexOfStudentId = studentCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.STUDENT_ID);
                int indexOfStudentName = studentCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.STUDENT_NAME);
                int indexOfStudentRegNo = studentCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.STUDENT_REG_NO);
                int indexOfStudentRegular = studentCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.IS_REGULAR);
                int studentId = studentCursor.getInt(indexOfStudentId);
                String studentName = studentCursor.getString(indexOfStudentName);
                String studentRegNo = studentCursor.getString(indexOfStudentRegNo);
                Log.e("Index ",indexOfStudentRegular+"");
                int isStudentRegular = studentCursor.getInt(indexOfStudentRegular);
                Log.e("From ListActivity ","Regular "+isStudentRegular);
                Student student= new Student(studentName,studentRegNo,isStudentRegular);
                students.add(student);
            }
            studentAdapter = new StudentAdapter(PastRecordStudentList.this,R.layout.student_single_row,R.id.student_icon,students);
            studentAdapter.setFragmentManager(getSupportFragmentManager());
            studentAdapter.setShowVertIcon(false);
            studentList.setAdapter(studentAdapter);
            studentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Student student = (Student) parent.getItemAtPosition(position);
                    Intent intent = new Intent(PastRecordStudentList.this,PastRecordCalendarActivity.class);
                    intent.putExtra("Student",student);
                    startActivity(intent);
                }
            });

        }
    }
}
