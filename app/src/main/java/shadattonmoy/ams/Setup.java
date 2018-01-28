package shadattonmoy.ams;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Setup extends AppCompatActivity {

    private SQLiteAdapter sqLiteAdapter;
    private Cursor courseCursor;
    private RelativeLayout noCourseFoundMsg;
    private FloatingActionButton courseAddFab;
    private ListView courseList;
    private ArrayList<Course> courses;
    boolean returnFlag = false;
    static CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        initialize();
        getCourse();
    }

    public void initialize()
    {
        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Setup System");

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
        * find fab by id and set click event listener
        * */
        courseAddFab = (FloatingActionButton) findViewById(R.id.course_add_fab);
        courseAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setup.this,CourseAddActivity.class);
                startActivity(intent);

            }
        });

        /*
        * initialize sqite database
        * */
        initSQLiteDB();

        /*
        * find the no course found UI from xml for toggle visibility
        * */
        noCourseFoundMsg = (RelativeLayout) findViewById(R.id.no_course_found_msg);

        /*
        * find course list view
        * */
        courseList = (ListView) findViewById(R.id.course_list);

        /*
        * initialize course array list
        * */
        courses = new ArrayList<Course>();
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(Setup.this);
        //String res = sqLiteAdapter.getCourse();
        //Toast.makeText(MainActivity.this,res,Toast.LENGTH_SHORT).show();
    }

    public void getCourse()
    {
        courseCursor = sqLiteAdapter.getCourse();
        Toast.makeText(Setup.this,"Found "+courseCursor.getCount(),Toast.LENGTH_LONG).show();
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
            courseAdapter = new CourseAdapter(Setup.this,R.layout.course_single_row,R.id.course_icon,courses);
            courseAdapter.setFragmentManager(getSupportFragmentManager());
            courseList.setAdapter(courseAdapter);
        }
    }
}
