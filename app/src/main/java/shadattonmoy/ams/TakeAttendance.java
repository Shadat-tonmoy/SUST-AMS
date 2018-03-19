package shadattonmoy.ams;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import shadattonmoy.ams.spreadsheetapi.StudentAdapter;

public class TakeAttendance extends AppCompatActivity {


    private SQLiteAdapter sqLiteAdapter;
    private Cursor courseCursor;
    private RelativeLayout noCourseFoundMsg;
    private ListView courseList;
    private ArrayList<Course> courses;
    private ArrayList<Student> students;
    private SearchView searchView;
    private CourseAdapter courseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        initialize();
        getCourse();
    }


    public void initialize()
    {
        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Attendance");

        setSupportActionBar(toolbar);
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


        noCourseFoundMsg = (RelativeLayout) findViewById(R.id.no_course_found_msg);

        /*
        * find course list view
        * */
        courseList = (ListView) findViewById(R.id.course_list_for_attendance);

        /*
        * initialize course array list
        * */
        courses = new ArrayList<Course>();

        initSQLiteDB();
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(TakeAttendance.this);
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
            courseAdapter = new CourseAdapter(TakeAttendance.this,R.layout.course_single_row,R.id.course_icon,courses);
            courseAdapter.setShowVertIcon(false);
            Log.e("Course Size",courses.size()+"");
            //courseAdapter.setFragmentManager(getSupportFragmentManager());
            courseList.setAdapter(courseAdapter);
            courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Course course = (Course) parent.getItemAtPosition(position);
                    Toast.makeText(TakeAttendance.this,"Opening : "+course.toString(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TakeAttendance.this,ClassInstanceListActivity.class);
                    intent.putExtra("Course", course);
                    startActivity(intent);

                }
            });
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.take_attendance_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.take_attendance_search_menu);
        searchView = new SearchView(TakeAttendance.this);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search Here");
        searchView.setQueryHint(Html.fromHtml("<font color = #95a5a6>Course Code, Session</font>"));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return false;
            }
        });
        return true;
    }

    public void filterData(String query)
    {
        List<Course> filteredCourses= new ArrayList<Course>();
        if(searchView!=null)
        {
            for(Course course:courses)
            {
                if(course.getCourseCode().toLowerCase().startsWith(query.toLowerCase()) || course.getCourseCode().toLowerCase().endsWith(query.toLowerCase())|| course.getCourseSession().toLowerCase().startsWith(query.toLowerCase()) || course.getCourseSession().endsWith(query.toLowerCase()))
                {
                    filteredCourses.add(course);
                }
                //else filteredVendors=vendors;
                courseAdapter = new CourseAdapter(TakeAttendance.this,R.layout.student_single_row,R.id.student_reg_no,filteredCourses);
                courseAdapter.setShowVertIcon(false);
                Log.e("Course Size",courses.size()+"");
                //courseAdapter.setFragmentManager(getSupportFragmentManager());
                courseList.setAdapter(courseAdapter);
                courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Course course = (Course) parent.getItemAtPosition(position);
                        Toast.makeText(TakeAttendance.this,"Opening : "+course.toString(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(TakeAttendance.this,ClassInstanceListActivity.class);
                        intent.putExtra("Course", course);
                        startActivity(intent);

                    }
                });
            }
        }
    }
}
