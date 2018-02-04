package shadattonmoy.ams;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.zip.CheckedOutputStream;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import shadattonmoy.ams.spreadsheetapi.SpreadSheetActivity;
import shadattonmoy.ams.spreadsheetapi.StudentAdapter;

import static shadattonmoy.ams.Setup.courseAdapter;

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
    static StudentAdapter studentAdapter;




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
                intent.putExtra("Course",course);
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

        studentList = (ListView) findViewById(R.id.student_list);
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
            initStudents(isUpdated);
        }
    }

    public void initStudents(boolean isUpdated)
    {
        if(studentCursor!=null)
        {

            while (studentCursor.moveToNext())
            {
                Log.e("Student Cursor Column ",studentCursor.getColumnCount()+"");

                int indexOfStudentId = studentCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.STUDENT_ID);
                int indexOfStudentName = studentCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.STUDENT_NAME);
                int indexOfStudentRegNo = studentCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.STUDENT_REG_NO);
                int indexOfStudentRegular = studentCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.IS_REGULAR);
                int studentId = studentCursor.getInt(indexOfStudentId);
                String studentName = studentCursor.getString(indexOfStudentName);
                String studentRegNo = studentCursor.getString(indexOfStudentRegNo);
                Log.e("Index ",indexOfStudentRegular+"");
                int isStudentRegular = studentCursor.getInt(3);
                Student student= new Student(studentName,studentRegNo,isStudentRegular);
//                if(courseId == updatedCourseId)
//                    course.setUpdated(true);
//                else course.setUpdated(false);
                students.add(student);
            }
            studentAdapter = new StudentAdapter(StudentListActivity.this,R.layout.student_single_row,R.id.student_icon,students);
            studentAdapter.setFragmentManager(getSupportFragmentManager());
            studentList.setAdapter(studentAdapter);
            /*courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Course course = (Course) parent.getItemAtPosition(position);
                    Toast.makeText(Setup.this,"Opening : "+course.toString(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Setup.this,StudentListActivity.class);
                    intent.putExtra("Course", course);
                    startActivity(intent);

                }
            });*/
        }
    }


}
