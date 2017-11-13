package shadattonmoy.ams;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CourseAddActivity extends AppCompatActivity {

    private TextInputLayout courseCodeLayout,courseTitleLayout,courseSessionLayout;
    private EditText courseCodeField,courseTitleField,courseSessionField;
    private FloatingActionButton courseAddDoneFab;
    private boolean isValid;
    private SQLiteAdapter sqLiteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add);
        initialize();

        /*
        * add onclicklistener to courseAddDoneFab
        * */
        courseAddDoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
                if(isValid)
                {
                    /*
                    * insert data to sqlite
                    * */
                    insertData();
                }
            }
        });
    }

    public void initialize()
    {
        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.setup_avtivity_toolbar);
        toolbar.setTitle("Fill The Form");

        Toolbar toolbarStatic = (Toolbar) findViewById(R.id.toolbar);
        toolbarStatic.setTitle("Add Course");

        /*
        * set toolbar navigation Icon
        * */
        toolbarStatic.setNavigationIcon(R.drawable.back);


        /*
        * set toolbar navigation Icon Click Listener
        * */
        toolbarStatic.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /*
        * find text input layout by id
        * */
        courseCodeLayout = (TextInputLayout) findViewById(R.id.course_code_layout);
        courseTitleLayout = (TextInputLayout) findViewById(R.id.course_title_layout);
        courseSessionLayout = (TextInputLayout) findViewById(R.id.course_session_layout);

        /*
        * find edit text by id
        * */
        courseCodeField = (EditText) findViewById(R.id.course_code_field);
        courseTitleField = (EditText) findViewById(R.id.course_title_field);
        courseSessionField = (EditText) findViewById(R.id.course_session_field);

        /*
        * find course add done fab by id and set on click listener
        * */
        courseAddDoneFab = (FloatingActionButton) findViewById(R.id.course_add_done_fab);

        /*
        * method to established sqlite connection
        * */
        initSQLiteDB();
    }


    public void validateForm()
    {
        isValid = true;
        if(courseCodeField.getText().toString().isEmpty())
        {
            isValid = false;
            courseCodeLayout.setError("Course Code Field is empty");
        }
        else
        {
            courseCodeLayout.setErrorEnabled(false);
        }

        if(courseTitleField.getText().toString().isEmpty())
        {
            isValid = false;
            courseTitleLayout.setError("Course Title Field is empty");
        }
        else
        {
            courseTitleLayout.setErrorEnabled(false);
        }

        if(courseSessionField.getText().toString().isEmpty())
        {
            isValid = false;
            courseSessionLayout.setError("Session Field is empty");
        }
        else
        {
            courseSessionLayout.setErrorEnabled(false);
        }
        if(isValid)
        {
            Toast.makeText(this,"Welcome",Toast.LENGTH_SHORT).show();
        }
    }

    /*
   * method to initialize database connection
   * */
    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(CourseAddActivity.this);
    }

    /*
    * method to insert data in sqlite
    * */
    public void insertData()
    {
        String courseCode = courseCodeField.getText().toString();
        String courseTitle = courseTitleField.getText().toString();
        String session = courseSessionField.getText().toString();
        long id = sqLiteAdapter.addCourseToDB(courseCode,courseTitle,session);
        if(id>=0)
        {
            Toast.makeText(CourseAddActivity.this,"Course is added with id "+id,Toast.LENGTH_LONG).show();
            Setup.courseAdapter.add(new Course(courseCode,courseTitle,session,id));
        }
        else
        {
            Toast.makeText(CourseAddActivity.this,"Error "+id,Toast.LENGTH_LONG).show();
        }
    }
}
