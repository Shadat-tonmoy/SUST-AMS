package shadattonmoy.ams;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class Setup extends AppCompatActivity {

    private SQLiteAdapter sqLiteAdapter;
    private Cursor courseCursor;
    private RelativeLayout noCourseFoundMsg;
    private FloatingActionButton courseAddFab;
    private ListView courseList;
    private ArrayList<Course> courses;
    boolean returnFlag = false;
    private CoordinatorLayout coordinatorLayout;
    static CourseAdapter courseAdapter;
    private static boolean isUpdated,isFirstData,isRandomAddedData;
    private static long updatedCourseId;
    private static String addedCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        initialize();
        getCourse(false);
    }

    public void initialize()
    {
        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Setup System");

        isUpdated = false;
        isFirstData = false;
        isRandomAddedData = false;

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.setup_activity_layout);
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

    public void getCourse(boolean isUpdated)
    {
        courseCursor = sqLiteAdapter.getCourse();
        //Toast.makeText(Setup.this,"Found "+courseCursor.getCount(),Toast.LENGTH_LONG).show();
        if(courseCursor.getCount()<=0)
        {
            noCourseFoundMsg.setVisibility(View.VISIBLE);
        }
        else
        {
            noCourseFoundMsg.setVisibility(View.GONE);
            initCourses(isUpdated);
        }
    }

    public void initCourses(boolean isUpdated)
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
                if(courseId == updatedCourseId)
                    course.setUpdated(true);
                else course.setUpdated(false);
                courses.add(course);
            }
            courseAdapter = new CourseAdapter(Setup.this,R.layout.course_single_row,R.id.course_icon,courses);
            courseAdapter.setFragmentManager(getSupportFragmentManager());
            courseAdapter.setCourseList(courseList);
            courseList.setAdapter(courseAdapter);
            courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Course course = (Course) parent.getItemAtPosition(position);
                    //Toast.makeText(Setup.this,"Opening : "+course.toString(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Setup.this,StudentListActivity.class);
                    intent.putExtra("Course", course);
                    startActivity(intent);

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this,"On Resume is Called",Toast.LENGTH_SHORT).show();
        if(isUpdated)
        {
            CourseAdapter.hideBottomSheet();
            initialize();
            getCourse(isUpdated);
            Snackbar snackbar = Snackbar.make(coordinatorLayout,"Course Info. is Updated",Snackbar.LENGTH_LONG);
            snackbar.show();
            isUpdated = false;
        }
        else if(isFirstData)
        {
            initialize();
            getCourse(isUpdated);
            Snackbar snackbar = Snackbar.make(coordinatorLayout,addedCourse+" is Added",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        else if(isRandomAddedData)
        {
            Snackbar snackbar = Snackbar.make(coordinatorLayout,addedCourse+" is Added",Snackbar.LENGTH_LONG);
            snackbar.show();
            isRandomAddedData = false;


        }


    }

    public static void setIsUpdated(boolean isUpdated) {
        Setup.isUpdated = isUpdated;
    }

    public static void setUpdatedCourseId(long updatedCourseId) {
        Setup.updatedCourseId = updatedCourseId;
    }

    public static boolean isFirstData() {
        return isFirstData;
    }

    public static void setIsFirstData(boolean isFirstData) {
        Setup.isFirstData = isFirstData;
    }

    public static String getAddedCourse() {
        return addedCourse;
    }

    public static void setAddedCourse(String addedCourse) {
        Setup.addedCourse = addedCourse;
    }

    public static boolean isRandomAddedData() {
        return isRandomAddedData;
    }

    public static void setIsRandomAddedData(boolean isRandomAddedData) {
        Setup.isRandomAddedData = isRandomAddedData;
    }
}
