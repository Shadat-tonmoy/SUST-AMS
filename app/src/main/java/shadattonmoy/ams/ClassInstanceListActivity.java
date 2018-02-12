package shadattonmoy.ams;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.CheckedOutputStream;

import shadattonmoy.ams.attendance.ClassDate;
import shadattonmoy.ams.attendance.ClassInstance;
import shadattonmoy.ams.attendance.ClassInstanceAdapter;

public class ClassInstanceListActivity extends AppCompatActivity {

    private SQLiteAdapter sqLiteAdapter;
    private Cursor classInstanceCursor;
    private RelativeLayout noClassInstancFoundMsg;
    private ListView classInstanceList;
    private ArrayList<ClassInstance> classInstances;
    private Course course;
    private FloatingActionButton classInstanceAddFab;
    private ClassInstanceAdapter classInstanceAdapter;
    private static ArrayList<Student> students;
    private int totalStudent;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_instance_list);
        course = (Course) getIntent().getSerializableExtra("Course");
        initialize();
        getStudents(String.valueOf(course.getCourseId()));
        getClassInstances();

    }

    public void initialize()
    {
        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Class Instance");

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


        noClassInstancFoundMsg = (RelativeLayout) findViewById(R.id.no_class_instance_found_msg);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.class_instance_list_activity_layout);

        /*
        * find class instance list view
        * */
        classInstanceList = (ListView) findViewById(R.id.class_instance_list);

        /*
        * initialize class instance array list
        * */
        classInstances = new ArrayList<ClassInstance>();

        classInstanceAddFab = (FloatingActionButton) findViewById(R.id.add_class_instance_fab);

        classInstanceAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassDate classDate = new ClassDate();
                String date = classDate.getDateValue();
                String day = classDate.getDayValue();
                String month = classDate.getMonthValue();
                String year = classDate.getYearValue();
                ClassInstance classInstance = new ClassInstance(classDate);
                long id = sqLiteAdapter.addClassInstanceToDB(classInstance,course.getCourseId());
                Toast.makeText(ClassInstanceListActivity.this,"Class instance added...",Toast.LENGTH_SHORT).show();
                if(classInstanceAdapter==null)
                {
                    classInstances.add(classInstance);
                    classInstanceAdapter = new ClassInstanceAdapter(ClassInstanceListActivity.this,R.layout.class_instance_single_row,R.id.numeric_date_view,classInstances);
                    classInstanceList.setAdapter(classInstanceAdapter);
                    classInstanceList.setOnItemClickListener(new ClassInstanceClickHandler(ClassInstanceListActivity.this,course,students));
                    noClassInstancFoundMsg.setVisibility(View.GONE);

                }
                else classInstanceAdapter.add(classInstance);
                Snackbar snackbar = Snackbar.make(coordinatorLayout,"Class Instance Added",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });

        initSQLiteDB();
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(ClassInstanceListActivity.this);
    }

    public void getClassInstances()
    {
        classInstanceCursor = sqLiteAdapter.getClassInstances(Long.toString(course.getCourseId()));
        Toast.makeText(ClassInstanceListActivity.this,"Total Class "+classInstanceCursor.getCount(),Toast.LENGTH_SHORT).show();

        if(classInstanceCursor.getCount()<=0)
        {
            noClassInstancFoundMsg.setVisibility(View.VISIBLE);
        }
        else
        {
            noClassInstancFoundMsg.setVisibility(View.GONE);
            initClassInstances();
        }
    }

    public void initClassInstances()
    {
        if(classInstanceCursor!=null)
        {
            while (classInstanceCursor.moveToNext())
            {
                int indexOfClassInstanceId = classInstanceCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.CLASS_ID);
                int indexOfClassInstanceDate = classInstanceCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.CLASS_DATE);
                int indexOfCourseId = classInstanceCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.COURSE_ID);

                int classInstanceId = classInstanceCursor.getInt(indexOfClassInstanceId);
                int courseId = classInstanceCursor.getInt(indexOfCourseId);
                int totalPresent = -1;
                int totalAbsent = -1;
                String classInstanceDate = classInstanceCursor.getString(indexOfClassInstanceDate);
                ClassInstance classInstance= new ClassInstance();
                classInstance.setClassInstanceid(classInstanceId);
                classInstance.setTotalPresent(totalPresent);
                classInstance.setTotalStudent(totalStudent);
                classInstance.setTotalAbsent(totalAbsent);
                classInstance.setDate(classInstanceDate);
                classInstances.add(classInstance);
            }
            classInstanceAdapter= new ClassInstanceAdapter(ClassInstanceListActivity.this,R.layout.class_instance_single_row,R.id.numeric_date_view,classInstances);
            classInstanceList.setAdapter(classInstanceAdapter);
            classInstanceList.setOnItemClickListener(new ClassInstanceClickHandler(ClassInstanceListActivity.this,course,students));
        }
    }

    public void getStudents(String courseId)
    {
        students = new ArrayList<Student>();
        Cursor studentCursor = sqLiteAdapter.getStudents(courseId);
        totalStudent = studentCursor.getCount();
        if(studentCursor!=null)
        {
            while (studentCursor.moveToNext())
            {
                int indexOfStudentId = studentCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.STUDENT_ID);
                int indexOfStudentName= studentCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.STUDENT_NAME);
                int indexOfStudentRegNo = studentCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.STUDENT_REG_NO);
                int indexOfRegular = studentCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.IS_REGULAR);
                int studentId = studentCursor.getInt(indexOfStudentId);
                int isRegular = studentCursor.getInt(indexOfRegular);
                String studentName = studentCursor.getString(indexOfStudentName);
                String studentRegNo = studentCursor.getString(indexOfStudentRegNo);
                Student student = new Student(studentName,studentRegNo,isRegular);
                student.setPresent(0);
                student.setStudentId(studentId);
                students.add(student);
            }
        }


    }
}
class ClassInstanceClickHandler implements AdapterView.OnItemClickListener{

    private Context context;
    private Course course;
    private ArrayList<Student> students;
    public ClassInstanceClickHandler(Context context, Course course, ArrayList<Student> students)
    {
        this.context = context;
        this.course = course;
        this.students = students;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        ClassInstance classInstance= (ClassInstance) parent.getItemAtPosition(position);
        Toast.makeText(context,"Opening : ",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context,TakeAttendanceStudentList.class);
        intent.putExtra("Course", course);
        intent.putExtra("ClassInstanceId",classInstance.getClassInstanceid());
        TakeAttendanceStudentList.setStudents(students);
        context.startActivity(intent);

    }
}
