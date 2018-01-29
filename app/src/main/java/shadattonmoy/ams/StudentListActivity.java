package shadattonmoy.ams;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;

public class StudentListActivity extends AppCompatActivity {

    private SQLiteAdapter sqLiteAdapter;
    private Cursor studentCursor;
    private RelativeLayout noStudentFoundMsg;
    private FloatingActionButton studentAddFab;
    private ListView studentList;
    private ArrayList<Course> students;
    private CoordinatorLayout coordinatorLayout;
    private static boolean isUpdated;
    private static long updatedStudentId;
    private Course course;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        initialize();
    }

    public void initialize()
    {

        course = (Course) getIntent().getSerializableExtra("Course");

        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(course.getCourseCode()a);

        isUpdated = false;


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.student_list_activity_layout);
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
        studentAddFab = (FloatingActionButton) findViewById(R.id.student_add_fab);
        studentAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /*
        * initialize sqite database
        * */
        initSQLiteDB();

        /*
        * find the no course found UI from xml for toggle visibility
        * */
        noStudentFoundMsg = (RelativeLayout) findViewById(R.id.no_student_found_msg);

        /*
        * find course list view
        * */
        studentList = (ListView) findViewById(R.id.student_list);

        /*
        * initialize course array list
        * */
        students = new ArrayList<Course>();
    }



    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(StudentListActivity.this);
        //String res = sqLiteAdapter.getCourse();
        //Toast.makeText(MainActivity.this,res,Tost.LENGTH_SHORT).show();
    }

}
