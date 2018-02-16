package shadattonmoy.ams;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import shadattonmoy.ams.attendance.ClassInstance;

public class ClassInstanceEditActivity extends AppCompatActivity {
    private static ClassInstance classInstance;

    private EditText classInstanceDateEditField,classInstanceWeightEditField;
    private boolean isValid;
    private TextInputLayout classInstanceDateEditLayout,classInstanceWeightEditLayout;
    private SQLiteAdapter sqLiteAdapter;
    private FloatingActionButton classInstanceEditDoneFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_instance_edit);
        initialize();

    }

    public void initialize()
    {
        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Class Instance");

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
        classInstanceEditDoneFab = (FloatingActionButton) findViewById(R.id.class_instancee_edit_done_fab);
        classInstanceEditDoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateForm();
                if(isValid)
                {
                    //updateCourseDetail();
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
        classInstanceDateEditLayout = (TextInputLayout) findViewById(R.id.class_instance_date_edit_layout);
        classInstanceWeightEditLayout = (TextInputLayout) findViewById(R.id.class_instance_weight_title_edit_layout);


        /*
        * find edit text by id
        * */
        classInstanceDateEditField= (EditText) findViewById(R.id.class_instance_date_edit_field);
        classInstanceWeightEditField = (EditText) findViewById(R.id.class_instance_weight_edit_field);

        /*set the value of the course*/
        setFieldValues();
    }

    public void validateForm()
    {
        isValid = true;
        if(classInstanceDateEditField.getText().toString().isEmpty())
        {
            isValid = false;
            classInstanceDateEditLayout.setError("Date Field is empty");
        }
        else
        {
            classInstanceDateEditLayout.setErrorEnabled(false);
        }

        if(classInstanceWeightEditField.getText().toString().isEmpty())
        {
            isValid = false;
            classInstanceWeightEditLayout.setError("Weight Field is empty");
        }
        else
        {
            classInstanceWeightEditLayout.setErrorEnabled(false);
        }
        if(isValid)
        {
            Toast.makeText(this,"All Data are valied. Updating....",Toast.LENGTH_SHORT).show();
        }
    }

    public void setFieldValues()
    {
        classInstanceDateEditField.setText(classInstance.getDate());
        classInstanceWeightEditField.setText(String.valueOf(classInstance.getWeight()));
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(ClassInstanceEditActivity.this);
    }

    public static ClassInstance getClassInstance() {
        return classInstance;
    }

    public static void setClassInstance(ClassInstance classInstance) {
        ClassInstanceEditActivity.classInstance = classInstance;
    }
}
