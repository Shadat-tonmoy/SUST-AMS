package shadattonmoy.ams;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import shadattonmoy.ams.spreadsheetapi.SpreadSheetActivity;

public class StudentListActivity extends AppCompatActivity {

    private SQLiteAdapter sqLiteAdapter;
    private Cursor studentCursor;
    private RelativeLayout noStudentFoundMsg;
    private FloatingActionButton studentAddFab;
    private ListView studentList;
    private ArrayList<Student> students;
    private CoordinatorLayout coordinatorLayout;
    private static boolean isUpdated;
    private static long updatedStudentId;
    private Course course;
    private TextView noStudentFoundText;
    private RelativeLayout studentAddFabContainer;
    private boolean menuIsOpen;
    private FloatingActionMenu floatingActionMenu;
    FloatingActionButton customStudentFab, csvFileFab, googleSheetFab;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        initialize();
        getStudents(false);
    }

    public void initialize()
    {

        course = (Course) getIntent().getSerializableExtra("Course");



        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);

        floatingActionMenu.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(StudentListActivity.this,"Inc",Toast.LENGTH_SHORT).show();
                //floatingActionMenu.showMenu(true);
                if(!menuIsOpen)
                {
                    floatingActionMenu.open(true);
                    menuIsOpen = true;
                }
                else
                {
                    floatingActionMenu.close(true);
                    menuIsOpen = false;
                }


            }
        });

        customStudentFab = (FloatingActionButton) findViewById(R.id.custom_student_fab);
        csvFileFab = (FloatingActionButton) findViewById(R.id.csv_file_fab);
        googleSheetFab= (FloatingActionButton) findViewById(R.id.google_sheet_fab);




        customStudentFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                floatingActionMenu.close(true);
                Intent intent = new Intent(StudentListActivity.this,CustomStudentAddActivity.class);
                startActivity(intent);;



            }
        });
        csvFileFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked

            }
        });
        googleSheetFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(StudentListActivity.this, SpreadSheetActivity.class);
                startActivity(intent);

                //TODO something when floating action menu third item clicked

            }
        });

        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(course.getCourseCode());

        isUpdated = false;
        menuIsOpen = false;

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



        /*
        * initialize sqite database
        * */
        initSQLiteDB();

        /*
        * find the no course found UI from xml for toggle visibility
        * */
        noStudentFoundMsg = (RelativeLayout) findViewById(R.id.no_student_found_msg);
        noStudentFoundText = (TextView) findViewById(R.id.no_student_found_txt);

        /*
        * initialize course array list
        * */
        students = new ArrayList<Student>();
    }



    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(StudentListActivity.this);
        //String res = sqLiteAdapter.getCourse();
        //Toast.makeText(MainActivity.this,res,Tost.LENGTH_SHORT).show();
    }

    public void getStudents(boolean isUpdated)
    {
        studentCursor = sqLiteAdapter.getStudents(Long.toString(course.getCourseId()));
        Toast.makeText(StudentListActivity.this,"Found "+studentCursor.getCount(),Toast.LENGTH_LONG).show();
        if(studentCursor.getCount()<=0)
        {
            noStudentFoundMsg.setVisibility(View.VISIBLE);
            noStudentFoundText.setText("Sorry!! No Student Record Found For "+course.getCourseCode()+"\nTap the '+' button to add student");

        }
        else
        {
            noStudentFoundMsg.setVisibility(View.GONE);
            //initCourses(isUpdated);
        }
    }


}
