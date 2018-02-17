package shadattonmoy.ams;

import android.app.DatePickerDialog;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import shadattonmoy.ams.attendance.ClassInstance;
import shadattonmoy.ams.attendance.ClassInstanceAdapter;

public class ClassInstanceEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    static ClassInstance classInstance;

    private TextView classInstanceDateEdit;
    private ImageView classInstanceDateEditIcon;
    private Spinner classWeightEditSpinner;
    private boolean isValid;
    private SQLiteAdapter sqLiteAdapter;
    private FloatingActionButton classInstanceEditDoneFab;
    private String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

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
               updateClassInstance();

            }
        });

        /*
        * initialize sqite database
        * */
        initSQLiteDB();





        /*
        * find edit text by id
        * */
        classInstanceDateEdit= (TextView) findViewById(R.id.class_instance_date_edit);
        classInstanceDateEditIcon = (ImageView) findViewById(R.id.class_instance_date_edit_icon);
        classWeightEditSpinner = (Spinner) findViewById(R.id.weight_edit_spinner);


        /*set the value of the course*/
        setFieldValues();



        classInstanceDateEditIcon.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                String date = classInstance.getDate();
                Map monthMap = new HashMap();
                for(int i=0;i<months.length;i++)
                {
                    monthMap.put(months[i],i);
                }

                String[] dateArray = date.split(",");
                int dayOfMonth = Integer.valueOf(dateArray[0]);
                int year = Integer.valueOf(dateArray[2]);
                int month = (int) monthMap.get(dateArray[1]);
                //dateArray[3].substring(0,3)+", "+dateArray[0]+" "+dateArray[1].substring(0,3)+" "+dateArray[2];

                //DatePickerDialog datePickerDialog = new DatePickerDialog (ClassInstanceEditActivity.this, ClassInstanceEditActivity.this, year,month,dayOfMonth);
                DatepickerDialog datepickerDialog = new DatepickerDialog();
                datepickerDialog.setEditDateView(classInstanceDateEdit);
                datepickerDialog.setClassInstance(classInstance);
                datepickerDialog.show(getFragmentManager(), "datePicker");

            }
        });


    }

    public String formatDate(String date)
    {
        String[] dateArray = date.split(",");
        return dateArray[3].substring(0,3)+", "+dateArray[0]+" "+dateArray[1].substring(0,3)+" "+dateArray[2];
    }



    public void setFieldValues()
    {
        classInstanceDateEdit.setText(formatDate(classInstance.getDate()));

        ArrayAdapter adapter = ArrayAdapter.createFromResource(ClassInstanceEditActivity.this,R.array.class_instance_weight,R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        classWeightEditSpinner.setAdapter(adapter);
        String[] weights = getResources().getStringArray(R.array.class_instance_weight);
        for(int i=0;i<weights.length;i++)
        {
            if(weights[i].equals(String.valueOf(classInstance.getWeight())))
            {
                classWeightEditSpinner.setSelection(i);
                break;
            }
        }
        classWeightEditSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String weight = (String) parent.getItemAtPosition(position);
                classInstance.setWeight(Integer.parseInt(weight));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(ClassInstanceEditActivity.this);
    }

    public void updateClassInstance()
    {
        String msg = classInstance.getDate()+" Weight : "+classInstance.getWeight()+" ID : "+classInstance.getClassInstanceid();
        int res = sqLiteAdapter.updateClassInstance(classInstance);
        if(res>0)
        {
            Toast.makeText(ClassInstanceEditActivity.this,"Class Instance Record Updated",Toast.LENGTH_SHORT).show();
            ClassInstanceListActivity.setReturnFlag(true);
            finish();
        }
        Log.e("Updating to....",msg);
    }

    public static ClassInstance getClassInstance() {
        return classInstance;
    }

    public static void setClassInstance(ClassInstance classInstance) {
        ClassInstanceEditActivity.classInstance = classInstance;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            ClassInstanceAdapter.hideBottomSheet();
        }catch (Exception e)
        {
            Log.e("Exception",e.getMessage());
        }
    }
}
