package shadattonmoy.ams;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CustomStudentAddActivity extends AppCompatActivity {

    private FloatingActionButton customStudentAddFab;
    private TextInputLayout studentNameLayout,studentRegNoLayout;
    private EditText studentNameField,studentRegNoField;
    private RadioGroup studentRegularRadioButton;
    private boolean isValid;
    private int isRegular;
    private SQLiteAdapter sqLiteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_student_add);
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
                    String regular = "Regular";
                    if(studentRegularRadioButton.getCheckedRadioButtonId()==R.id.radio_dropper)
                        regular = "Dropper";
                    Toast.makeText(CustomStudentAddActivity.this,"Name : "+studentName+" RegNo : "+ studentRegNo + " and "+regular,Toast.LENGTH_SHORT).show();
                }
            }
        });


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
}
