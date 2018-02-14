package shadattonmoy.ams;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class PastRecord extends AppCompatActivity {

    private SQLiteAdapter sqLiteAdapter;
    private Cursor courseCursor;
    private RelativeLayout noCourseFoundMsg;
    private ListView courseList;
    private ArrayList<Course> courses;
    private ArrayList<Student> students;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_record);
        initialize();
        getCourse();


    }

    public void initialize()
    {
        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Past Record");

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

        noCourseFoundMsg = (RelativeLayout) findViewById(R.id.past_record_no_course_found_msg);

        /*
        * find course list view
        * */
        courseList = (ListView) findViewById(R.id.course_list_for_past_record);

        /*
        * initialize course array list
        * */
        courses = new ArrayList<Course>();

        initSQLiteDB();

    }



    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(PastRecord.this);
    }

    public void getCourse()
    {
        courseCursor = sqLiteAdapter.getCourse();

        if(courseCursor.getCount()<=0)
        {
            noCourseFoundMsg.setVisibility(View.VISIBLE);
        }
        else
        {
            noCourseFoundMsg.setVisibility(View.GONE);
            initCourses();
        }
    }


    public void initCourses()
    {
        if(courseCursor!=null)
        {
            while (courseCursor.moveToNext())
            {
                int indexOfCourseId = courseCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.COURSE_ID);
                int indexOfCourseTitle = courseCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.COURSE_TITLE);
                int indexOfCourseSession = courseCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.COURSE_SESSION);
                int indexOfCourseCode = courseCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.COURSE_CODE);
                int courseId = courseCursor.getInt(indexOfCourseId);
                String courseTitle = courseCursor.getString(indexOfCourseTitle);
                String courseSession = courseCursor.getString(indexOfCourseSession);
                String courseCode = courseCursor.getString(indexOfCourseCode);
                Course course = new Course(courseCode,courseTitle,courseSession,courseId);
                courses.add(course);
            }
            CourseAdapter courseAdapter = new CourseAdapter(PastRecord.this,R.layout.course_single_row,R.id.course_icon,courses);
            courseAdapter.setShowVertIcon(false);
            Log.e("Course Size",courses.size()+"");
            //courseAdapter.setFragmentManager(getSupportFragmentManager());
            courseList.setAdapter(courseAdapter);
            courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Course course = (Course) parent.getItemAtPosition(position);
                    //Toast.makeText(PastRecord.this,"Opening : "+course.toString(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PastRecord.this,PastRecordStudentList.class);
                    intent.putExtra("Course", course);
                    startActivity(intent);

                }
            });
        }
    }
}
