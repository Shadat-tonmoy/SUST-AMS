package shadattonmoy.ams;

import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.SubMenuBuilder;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

import shadattonmoy.ams.spreadsheetapi.StudentAdapter;

public class CustomStudentAddActivity extends AppCompatActivity {

    private FloatingActionButton customStudentAddFab;
    private TextInputLayout studentNameLayout,studentRegNoLayout;
    private EditText studentNameField,studentRegNoField;
    private RadioGroup studentRegularRadioButton;
    private boolean isValid;
    private Course course;
    private int isRegular;
    private SQLiteAdapter sqLiteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_student_add);
        course = (Course) getIntent().getSerializableExtra("Course");
        initialize();
    }

    public void initialize()
    {
     /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Custom Student");
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

        customStudentAddFab = (FloatingActionButton) findViewById(R.id.custom_student_add_done_fab);

        studentNameLayout = (TextInputLayout) findViewById(R.id.custom_student_add_name_layout);
        studentNameField = (EditText) findViewById(R.id.custom_student_add_name_field);
        studentRegNoLayout = (TextInputLayout) findViewById(R.id.custom_student_add_regNo_layout);
        studentRegNoField = (EditText) findViewById(R.id.custom_student_add_regNo_field);
        studentRegularRadioButton = (RadioGroup) findViewById(R.id.student_type_radio_group);

        customStudentAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
                if(isValid)
                {
                    String studentName = studentNameField.getText().toString();
                    String studentRegNo = studentRegNoField.getText().toString();
                    int regular = 1;
                    if(studentRegularRadioButton.getCheckedRadioButtonId()==R.id.radio_dropper)
                        regular = 0;
                    Toast.makeText(CustomStudentAddActivity.this,"Name : "+studentName+" RegNo : "+ studentRegNo + " and "+regular,Toast.LENGTH_SHORT).show();
                    Student student = new Student(studentName,studentRegNo,regular);
                    long id = sqLiteAdapter.addStudentToDB(student,course.getCourseId());
                    if(id>0)
                    {

                        ArrayList<Student> students= new ArrayList<Student>();
                        students.add(student);
                        if(StudentListActivity.studentAdapter == null)
                        {
                            StudentListActivity.studentAdapter = new StudentAdapter(getApplicationContext(),R.layout.student_single_row,R.id.student_icon,students);
                            StudentListActivity.setIsFirstStudent(true);
                        }
                        else StudentListActivity.setIsCustomStudentAdded(true);
                        StudentListActivity.studentAdapter.add(student);
                        StudentListActivity.setCustomStudentAddedRegNo(studentRegNo);
                        finish();
                    }
                }
            }
        });

        initSQLiteDB();


    }


    public void validateForm()
    {
        isValid = true;
        if(studentNameField.getText().toString().isEmpty())
        {
            isValid = false;
            studentNameLayout.setError("Student Name Field is empty");
        }
        else
        {
            studentNameLayout.setErrorEnabled(false);
        }

        if(studentRegNoField.getText().toString().isEmpty())
        {
            isValid = false;
            studentRegNoLayout.setError("Registration No Field is empty");
        }
        else
        {
            studentRegNoLayout.setErrorEnabled(false);
        }
    }
    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(CustomStudentAddActivity.this);
    }

}
