package shadattonmoy.ams;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadattonmoy.ams.attendance.ClassDate;
import shadattonmoy.ams.attendance.ClassInstance;
import shadattonmoy.ams.attendance.ClassInstanceAdapter;
import shadattonmoy.ams.attendance.ClassInstanceStudentList;

public class ClassInstanceListActivity extends AppCompatActivity {

    private SQLiteAdapter sqLiteAdapter;
    private Cursor classInstanceCursor;
    private RelativeLayout noClassInstancFoundMsg;
    private ListView classInstanceList;
    private ArrayList<ClassInstance> classInstances;
    private List<ClassInstance> filteredClassInstance;
    private Course course;
    private FloatingActionButton classInstanceAddFab;
    private ClassInstanceAdapter classInstanceAdapter;
    private ArrayList<Student> students;
    private int totalStudent;
    private CoordinatorLayout coordinatorLayout;
    private static boolean returnFlag;
    private SearchView searchView;
    private String filename;
    private String filepath = "SUSTAMSExportedFiles";
    private Map studentInfoMap;
    File myExternalFile;
    String myData = "";


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

        setSupportActionBar(toolbar);

        returnFlag = false;

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
                classInstance.setTotalStudent(students.size());
                classInstance.setTotalAbsent(students.size());
                ClassInstanceWeightSetDialog classInstanceWeightSetDialog= new ClassInstanceWeightSetDialog(classInstance);
                classInstanceWeightSetDialog.setClassInstance(classInstance);
                classInstanceWeightSetDialog.setClassInstances(classInstances);
                classInstanceWeightSetDialog.setClassInstanceList(classInstanceList);
                classInstanceWeightSetDialog.setSqLiteAdapter(sqLiteAdapter);
                classInstanceWeightSetDialog.setClassInstanceAdapter(classInstanceAdapter);
                classInstanceWeightSetDialog.setCourse(course);
                classInstanceWeightSetDialog.setStudents(students);
                classInstanceWeightSetDialog.setNoClassInstancFoundMsg(noClassInstancFoundMsg);
                classInstanceWeightSetDialog.setCancelable(false);
                classInstanceWeightSetDialog.show(getFragmentManager(),"Dialog");


//                long id = sqLiteAdapter.addClassInstanceToDB(classInstance,course.getCourseId());
//                Toast.makeText(ClassInstanceListActivity.this,"Class instance added...",Toast.LENGTH_SHORT).show();
//                if(classInstanceAdapter==null)
//                {
//                    classInstances.add(classInstance);
//                    classInstanceAdapter = new ClassInstanceAdapter(ClassInstanceListActivity.this,R.layout.class_instance_single_row,R.id.numeric_date_view,classInstances);
//                    classInstanceList.setAdapter(classInstanceAdapter);
//                    classInstanceList.setOnItemClickListener(new ClassInstanceClickHandler(ClassInstanceListActivity.this,course,students));
//                    noClassInstancFoundMsg.setVisibility(View.GONE);
//
//                }
//                else classInstanceAdapter.add(classInstance);
//                Snackbar snackbar = Snackbar.make(coordinatorLayout,"Class Instance Added",Snackbar.LENGTH_LONG);
//                snackbar.show();
            }
        });

        studentInfoMap = new HashMap();
        filename = course.getCourseCode()+".csv";
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
            Log.e("Cursor Size","ClassInstanceCursor "+classInstanceCursor.getCount());
            while (classInstanceCursor.moveToNext())
            {
                int indexOfClassInstanceId = classInstanceCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.CLASS_ID);
                int indexOfClassInstanceDate = classInstanceCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.CLASS_DATE);
                int indexOfCourseId = classInstanceCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.COURSE_ID);
                int indexOfClassWeight= classInstanceCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.CLASS_WEIGHT);

                int classInstanceId = classInstanceCursor.getInt(indexOfClassInstanceId);
                int classWeight = classInstanceCursor.getInt(indexOfClassWeight);
                int courseId = classInstanceCursor.getInt(indexOfCourseId);
                int totalPresent = sqLiteAdapter.getPresentStudentNum(String.valueOf(classInstanceId));
                int totalAbsent = totalStudent - totalPresent;
                String classInstanceDate = classInstanceCursor.getString(indexOfClassInstanceDate);
                ClassInstance classInstance= new ClassInstance();
                classInstance.setClassInstanceid(classInstanceId);
                classInstance.setTotalPresent(totalPresent);
                classInstance.setWeight(classWeight);
                classInstance.setTotalStudent(totalStudent);
                classInstance.setTotalAbsent(totalAbsent);
                classInstance.setDate(classInstanceDate);
                classInstances.add(classInstance);
            }
            classInstanceAdapter= new ClassInstanceAdapter(ClassInstanceListActivity.this,R.layout.class_instance_single_row,R.id.numeric_date_view,classInstances);
            classInstanceAdapter.setShowVertIcon(true);
            classInstanceAdapter.setClassInstanceList(classInstanceList);
            classInstanceAdapter.setFragmentManager(getSupportFragmentManager());
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
                studentInfoMap.put(studentId,studentRegNo);
                students.add(student);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(returnFlag)
            updateView();
        //Log.e("Method","onResume of Class Instance List Activity is called");
    }

    public void updateView()
    {
        Log.e("Called","Update View was called");
        classInstanceAdapter.clear();
        getClassInstances();
        classInstanceAdapter.hideBottomSheet();
        //Log.e("From Update View", "Class instance list Size "+classInstanceList.getCount());
//        for(int i=0;i<classInstanceList.getCount();i++)
//        {
//            ClassInstance classInstance = (ClassInstance) classInstanceList.getAdapter().getItem(i);
//            //Log.e("Total Present","At Instance "+i+" "+classInstance.getTotalPresent()+" ID "+classInstance.getClassInstanceid());
//            int totalPresent = sqLiteAdapter.getPresentStudentNum(String.valueOf(classInstance.getClassInstanceid()));
//            int totalAbsent = totalStudent - totalPresent;
//            //Log.e("Debug "+i,"Present "+totalPresent+" Absent "+totalAbsent);
//            try {
//                TextView presentAbsentView = (TextView) classInstanceList.getChildAt(i).findViewById(R.id.absent_present_view);
//                presentAbsentView.setText("Total Present "+totalPresent+"\nTotal Absent "+totalAbsent);
//                TextView dateView = (TextView) classInstanceList.getChildAt(i).findViewById(R.id.date_view);
//                TextView numericDateView = (TextView) classInstanceList.getChildAt(i).findViewById(R.id.numeric_date_view);
//                TextView monthView = (TextView) classInstanceList.getChildAt(i).findViewById(R.id.month_view);
//                //Log.e("Finding Child...",classInstanceList.getChildAt(i).findViewById(R.id.absent_present_view)+"");
//            }catch (Exception e)
//            {
//
//            }
//        }
    }

    public static boolean isReturnFlag() {
        return returnFlag;
    }

    public static void setReturnFlag(boolean returnFlag) {
        ClassInstanceListActivity.returnFlag = returnFlag;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.class_instance_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.class_instance_search_menu);
        searchView = new SearchView(ClassInstanceListActivity.this);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search Here");
        searchView.setQueryHint(Html.fromHtml("<font color = #95a5a6>Day,Month,Year</font>"));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return false;
            }
        });
        return true;
    }

    public void filterData(String query)
    {
        filteredClassInstance= new ArrayList<ClassInstance>();
        if(searchView!=null)
        {
            for(ClassInstance classInstance:classInstances)
            {
                //Log.e("Logging","Matched");
                if(classInstance.getClassDate().getDayValue().toLowerCase().startsWith(query.toLowerCase()) || classInstance.getClassDate().getDayValue().toLowerCase().endsWith(query.toLowerCase())|| classInstance.getClassDate().getDateValue().toLowerCase().startsWith(query.toLowerCase()) || classInstance.getClassDate().getDateValue().toLowerCase().endsWith(query.toLowerCase()) || classInstance.getClassDate().getMonthValue().toLowerCase().startsWith(query.toLowerCase()) || classInstance.getClassDate().getMonthValue().toLowerCase().endsWith(query.toLowerCase()))
                {
                    filteredClassInstance.add(classInstance);
                }
                //else filteredVendors=vendors;
                classInstanceAdapter= new ClassInstanceAdapter(ClassInstanceListActivity.this,R.layout.class_instance_single_row,R.id.numeric_date_view,filteredClassInstance);
                classInstanceAdapter.setShowVertIcon(true);
                classInstanceAdapter.setFragmentManager(getSupportFragmentManager());
                classInstanceList.setAdapter(classInstanceAdapter);


                classInstanceList.setOnItemClickListener(new ClassInstanceClickHandler(ClassInstanceListActivity.this,course,students));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.export_menu:
                exportAsCSV();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void exportAsCSV()
    {
        Toast.makeText(ClassInstanceListActivity.this,"Export s CSV",Toast.LENGTH_SHORT).show();
        //File file = new File(ClassInstanceListActivity.this.getFilesDir(), "myFile");

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            myExternalFile = new File(ClassInstanceListActivity.this.getFilesDir(),"SUSTAMSExportedFiles");
            if(!myExternalFile.exists()){
                myExternalFile.mkdir();
            }
        }
        else {
            filename = course.getCourseCode()+".csv";
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
        }
        try {
            Toast.makeText(ClassInstanceListActivity.this,myExternalFile.toString(),Toast.LENGTH_SHORT).show();

            Log.e("Path",myExternalFile.toString());
            CSVWriter writer = new CSVWriter(new FileWriter(myExternalFile));
            List<String[]> data = new ArrayList<String[]>();
            String[] csvHeader = new String [400];
            int j = 0;
            csvHeader[j++]="Registration Number";
            for(ClassInstance classInstance:classInstances)
                csvHeader[j++]=classInstance.getDate();
            csvHeader[j++]="Total Present";
            csvHeader[j++]="Total Absent";
            data.add(csvHeader);

            Collections.sort(students, new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    String regNo1 = o1.getRegNo();
                    String regNo2 = o2.getRegNo();
                    int roll1 = Integer.parseInt(regNo1.substring(regNo1.length()-3,regNo1.length()));
                    int roll2 = Integer.parseInt(regNo2.substring(regNo2.length()-3,regNo2.length()));
                    String session1 = regNo1.substring(0,5);
                    String session2 = regNo2.substring(0,5);
                    if(session1.compareTo(session2)==0)
                    {
                        if(roll1>roll2)
                            return 1;
                        else return -1;
                    }
                    if(session1.compareTo(session2)>=0)
                        return -1;
                    else return 1;
                }
            });


            for(Student student:students)
            {
                long student_id = student.getStudentId();
                //Log.e("Stat","For Student "+student.getStudentId()+" "+student.getRegNo());
                int i=0;
                String[] infoToExport = new String[500];
                infoToExport[i++]=student.getRegNo();
                int totalPresent = 0;
                int totalAbsent = 0;
                for(ClassInstance classInstance:classInstances)
                {
                    int classInstanceId = classInstance.getClassInstanceid();
                    int classInstanceWeight = classInstance.getWeight();
                    Cursor attendanceInfo = sqLiteAdapter.getAttendance(String.valueOf(classInstanceId),String.valueOf(student_id));
                    while (attendanceInfo.moveToNext())
                    {
                        int indexOfStudentId = attendanceInfo.getColumnIndex(sqLiteAdapter.sqLiteHelper.STUDENT_ID);
                        int indexOfPresent = attendanceInfo.getColumnIndex(sqLiteAdapter.sqLiteHelper.IS_PRESENT);
                        int studentId = attendanceInfo.getInt(indexOfStudentId);
                        int isPresent = attendanceInfo.getInt(indexOfPresent);
                        //Log.e("Detail","Class ID = "+classInstanceId+" Date = "+classInstance.getDate()+" isPresent = "+isPresent);
                        if(isPresent!=0)
                            totalPresent+=isPresent;
                        else totalAbsent+=classInstanceWeight;

                        infoToExport[i++]=String.valueOf(isPresent);
                        //infoToExport[i++]=
                    }
                }
                infoToExport[i++]=String.valueOf(totalPresent);
                infoToExport[i++]=String.valueOf(totalAbsent);
                data.add(infoToExport);
            }

            writer.writeAll(data);
            writer.close();
            openFolder();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public void openFolder()
    {

//        Uri uri = Uri.parse(ClassInstanceListActivity.this.getFilesDir()
//                + "/SUSTAMSExportedFiles/");

        Uri uri = Uri.parse(myExternalFile.getAbsolutePath());
        Log.e("Opening",uri.toString());
//        intent.setDataAndType(uri, "text/csv");
//        startActivity(Intent.createChooser(intent, "Open folder"));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "*/*");
        startActivity(Intent.createChooser(intent,"Open File"));

    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }




}
class ClassInstanceClickHandler implements AdapterView.OnItemClickListener{

    private Context context;
    private Course course;
    private ArrayList<Student> students;
    private int FLAG_ACTIVITY_NEW_TASK=1;
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
        ClassInstanceStudentList classInstanceStudentList = new ClassInstanceStudentList();
        classInstanceStudentList.setStudents(students);
        String[] dateArray = classInstance.getDate().split(",");
        String formattedDate = dateArray[3].substring(0,3)+" , "+dateArray[0]+" "+dateArray[1].substring(0,3)+" "+dateArray[2];
        Intent intent = new Intent(context,TakeAttendanceStudentList.class);
        intent.putExtra("Course", course);
        intent.putExtra("ClassInstanceId",classInstance.getClassInstanceid());
        intent.putExtra("StudentList",classInstanceStudentList);
        intent.putExtra("Date",formattedDate);
        intent.putExtra("Weight",classInstance.getWeight());
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        //TakeAttendanceStudentList.setStudents(students);

        context.startActivity(intent);

    }
}
