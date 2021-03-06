package shadattonmoy.ams;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class CourseEditActivity extends AppCompatActivity {

    private FloatingActionButton courseEditDoneFab;
    private static Course courseToEdit;
    private EditText courseCodeEditField,courseTitleEditField,courseSessionEditField;
    private boolean isValid;
    private TextInputLayout courseCodeEditLayout,courseTitleEditLayout,courseSessionEditLayout;
    private SQLiteAdapter sqLiteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        initialize();
        //getSupportActionBar().setTitle("Edit Course");
    }


    public void initialize()
    {
        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Course");

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
        courseEditDoneFab = (FloatingActionButton) findViewById(R.id.course_edit_done_fab);
        courseEditDoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
                if(isValid)
                {
                    updateCourseDetail();
                }

            }
        });

        /*
        * initialize sqite database
        * */
        initSQLiteDB();



        /*
        * find text input layout by id
        * */
        courseCodeEditLayout = (TextInputLayout) findViewById(R.id.course_code_edit_layout);
        courseTitleEditLayout = (TextInputLayout) findViewById(R.id.course_title_edit_layout);
        courseSessionEditLayout = (TextInputLayout) findViewById(R.id.course_session_edit_layout);

        /*
        * find edit text by id
        * */
        courseCodeEditField = (EditText) findViewById(R.id.course_code_edit_field);
        courseTitleEditField = (EditText) findViewById(R.id.course_title_edit_field);
        courseSessionEditField = (EditText) findViewById(R.id.course_session_edit_field);

        /*set the value of the course*/
        setFieldValues();
    }

    public static void setCourse(Course course)
    {
        courseToEdit = course;
    }

    public void setFieldValues()
    {
        courseCodeEditField.setText(courseToEdit.getCourseCode());
        courseTitleEditField.setText(courseToEdit.getCourseTitle());
        courseSessionEditField.setText(courseToEdit.getCourseSession());
    }

    public void validateForm()
    {
        isValid = true;
        if(courseCodeEditField.getText().toString().isEmpty())
        {
            isValid = false;
            courseCodeEditLayout.setError("Course Code Field is empty");
        }
        else
        {
            courseCodeEditLayout.setErrorEnabled(false);
        }

        if(courseTitleEditField.getText().toString().isEmpty())
        {
            isValid = false;
            courseTitleEditLayout.setError("Course Title Field is empty");
        }
        else
        {
            courseTitleEditLayout.setErrorEnabled(false);
        }

        if(courseSessionEditField.getText().toString().isEmpty())
        {
            isValid = false;
            courseSessionEditLayout.setError("Session Field is empty");
        }
        else
        {
            courseSessionEditLayout.setErrorEnabled(false);
        }
        if(isValid)
        {
            //Toast.makeText(this,"All Data are valied. Updating....",Toast.LENGTH_SHORT).show();
        }
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(CourseEditActivity.this);
    }

    public void updateCourseDetail()
    {
        String courseCode = courseCodeEditField.getText().toString();
        String courseTitle = courseTitleEditField.getText().toString();
        String courseSession = courseSessionEditField.getText().toString();
        long id = courseToEdit.getCourseId();
        Course updatedCourse = new Course(courseCode,courseTitle,courseSession,id);
        //Toast.makeText(this,"Will updated to "+updatedCourse.toString(),Toast.LENGTH_SHORT).show();
        int updatedId = sqLiteAdapter.update(updatedCourse);
        if(updatedId>0)
        {
            Toast.makeText(this,"Record is updated..",Toast.LENGTH_SHORT).show();
            Setup.setIsUpdated(true);
            Setup.setUpdatedCourseId(id);
            finish();

        }

    }
}
