package shadattonmoy.ams;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadattonmoy.ams.attendance.ClassInstance;
import shadattonmoy.ams.attendance.ClassInstanceStudentList;
import shadattonmoy.ams.spreadsheetapi.SpreadSheetActivity;
import shadattonmoy.ams.spreadsheetapi.StudentAdapter;

public class TakeAttendanceStudentList extends AppCompatActivity {

    private String classInstanceDate;
    private SQLiteAdapter sqLiteAdapter;
    private RelativeLayout noStudentFoundMsg;
    private FloatingActionButton studentAddFab;
    private ListView studentList;
    private ArrayList<Student> students;
    private CoordinatorLayout coordinatorLayout;
    private Course course;
    private TextView noStudentFoundText;
    private int classInstanceId;
    private Cursor attendanceCursor;
    private Map presentMap;
    private StudentAdapter studentAdapter;
    private ClassInstanceStudentList classInstanceStudentList;
    private int classInstanceWeight;
    private SortingOptionBottomSheet sortingOptionBottomSheet;
    private String sortingParameter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance_student_list);
        course = (Course) getIntent().getSerializableExtra("Course");
        classInstanceId = getIntent().getIntExtra("ClassInstanceId",-1);
        classInstanceWeight = getIntent().getIntExtra("Weight",1);
        Log.e("Weight",classInstanceWeight+"");
        classInstanceStudentList = (ClassInstanceStudentList) getIntent().getSerializableExtra("StudentList");
        classInstanceDate = getIntent().getStringExtra("Date");
        students = classInstanceStudentList.getStudents();
        initialize();
        sortStudentList("RegNo");
        Log.e("Class ID",classInstanceId+"");
        initStudents();
    }

    public void initialize()
    {

        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(classInstanceDate);


        /*
        * set toolbar navigation Icon
        * */
        toolbar.setNavigationIcon(R.drawable.back);

        setSupportActionBar(toolbar);


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
        noStudentFoundMsg = (RelativeLayout) findViewById(R.id.take_attendance_no_student_found_msg);
        noStudentFoundText = (TextView) findViewById(R.id.take_attendance_no_student_found_txt);


        studentList = (ListView) findViewById(R.id.take_attendance_student_list);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.student_list_activity_layout);

        presentMap = new HashMap();
        getAttendance();
    }



    public void initStudents()
    {
        if(students.size()<=0)
        {
            noStudentFoundMsg.setVisibility(View.VISIBLE);
            noStudentFoundText.setText("Sorry!! No Student Record Found For "+course.getCourseCode());

        }
        else
        {
            noStudentFoundMsg.setVisibility(View.GONE);
            studentAdapter = new StudentAdapter(TakeAttendanceStudentList.this,R.layout.student_single_row,R.id.student_icon,students);
            studentAdapter.setPresentFlagMap(presentMap);
            studentAdapter.setFragmentManager(getSupportFragmentManager());
            studentAdapter.setShowVertIcon(false);
            studentAdapter.setShowPresentAbsentRadio(true);
            studentList.setAdapter(studentAdapter);
        }
    }

    public void getAttendance()
    {
        attendanceCursor = sqLiteAdapter.getAttendance(String.valueOf(classInstanceId));
        Log.e("Total Attendance","From Get Attendance Found "+attendanceCursor.getCount());
        presentMap.clear();
        while (attendanceCursor.moveToNext())
        {
            int indexOfStudentId = attendanceCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.STUDENT_ID);
            int indexOfStudentPresent= attendanceCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.IS_PRESENT);
            int indexOfAttendanceId = attendanceCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.ATTENDANCE_ID);
            int studentId = attendanceCursor.getInt(indexOfStudentId);
            int isPresent = attendanceCursor.getInt(indexOfStudentPresent);
            int attendanceId = attendanceCursor.getInt(indexOfAttendanceId);
            //Pair<Integer,Integer> key = new Pair<>(new Integer(studentId),new Integer(classInstanceId));
            //presentMap.put(key,isPresent);
            presentMap.put((long)studentId,isPresent);
            for(Student student:students)
            {
                if(student.getStudentId()==studentId){
                    student.setPresent(isPresent);
                    break;
                }
            }
            //Log.e("Present ","ID "+studentId+" Present "+presentMap.get(studentId));
        }
        Log.e("Mapping",presentMap.toString());
    }

    public void insertAttendance()
    {
        Log.e("Method","Insert Attendance");
        int totalPresent = 0;
        int totalAbsent = 0;
        for(Student student : students)
        {
            if(student.getPresent()==1)
                totalPresent++;
            else totalAbsent++;
            Log.e("Insert Info","Student ID "+student.getStudentId()+" Course ID "+course.getCourseId()+" Present "+student.getPresent()*classInstanceWeight);
            sqLiteAdapter.addAttendanceToDB(classInstanceId,student.getStudentId(),student.getPresent()*classInstanceWeight);
        }

    }

    public void updateAttendance()
    {
        //Log.e("Method","Update Attendance "+presentMap.toString());
        String studentIDs = "";
        for(Student student : students)
        {
            int present = student.getPresent();
            int mapPresent = (int) presentMap.get(student.getStudentId());
            studentIDs += student.getStudentId()+" : "+present+" , ";
            if(mapPresent==present){
                Log.e("Not Changed ",student.getRegNo());
            }
            else
            {
                Log.e("Changed ",student.getRegNo()+" Changed to "+student.getPresent());
                presentMap.put(student.getStudentId(),present*classInstanceWeight);
                int res = sqLiteAdapter.updateAttendance(classInstanceId,student.getStudentId(),present*classInstanceWeight);
                if(res>0)
                {
                    Log.e("Updating....",student.getRegNo()+" is updated with "+present*classInstanceWeight+" For class instane "+classInstanceId);
                    int totalPresent = sqLiteAdapter.getPresentStudentNum(String.valueOf(classInstanceId));
                    Log.e("From Other ","Total Present "+totalPresent);
                }
            }
        }
        //Log.e("Student IDS ",studentIDs);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(TakeAttendanceStudentList.this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(attendanceCursor.getCount()==0)
            insertAttendance();
        else updateAttendance();
        ClassInstanceListActivity.setReturnFlag(true);
        Toast.makeText(TakeAttendanceStudentList.this,"All Changes are saved",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.take_attendance_student_list_menu, menu);


        MenuItem searchItem = menu.findItem(R.id.search_menu);
        searchView = new SearchView(TakeAttendanceStudentList.this);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search Here");
        searchView.setQueryHint(Html.fromHtml("<font color = #95a5a6>" + getResources().getString(R.string.search_hint) + "</font>"));

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
        List<Student> filteredStudents= new ArrayList<Student>();
        if(searchView!=null)
        {
            for(Student student:students)
            {
                if(student.getName().toLowerCase().startsWith(query.toLowerCase()) || student.getName().toLowerCase().endsWith(query.toLowerCase())|| student.getRegNo().toLowerCase().startsWith(query.toLowerCase()) || student.getRegNo().endsWith(query.toLowerCase()))
                {
                    filteredStudents.add(student);
                }
                //else filteredVendors=vendors;
                studentAdapter = new StudentAdapter(TakeAttendanceStudentList.this,R.layout.student_single_row,R.id.student_reg_no,filteredStudents);
                studentAdapter.setPresentFlagMap(presentMap);
                studentAdapter.setFragmentManager(getSupportFragmentManager());
                studentAdapter.setShowVertIcon(false);
                studentAdapter.setShowPresentAbsentRadio(true);

                studentList.setAdapter(studentAdapter);
            }


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_menu:
                Toast.makeText(TakeAttendanceStudentList.this,"Search",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sort_menu:
            {
                Toast.makeText(TakeAttendanceStudentList.this,"Sort",Toast.LENGTH_SHORT).show();
                sortingOptionBottomSheet = new SortingOptionBottomSheet();
                sortingOptionBottomSheet.setTakeAttendanceStudentList(this);
                sortingOptionBottomSheet.show(getSupportFragmentManager(),"Sorting Options");
            }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String getSortingParameter() {
        return this.sortingParameter;
    }

    public void setSortingParameter(String sortingParameter) {
        this.sortingParameter = sortingParameter;
        sortStudentList(this.sortingParameter);

    }

    public void sortStudentList(String parameter)
    {
        Toast.makeText(TakeAttendanceStudentList.this,"Sorting By "+parameter,Toast.LENGTH_SHORT).show();
        try{
            sortingOptionBottomSheet.dismiss();
        }catch (Exception e)
        {

        }
        if(parameter.equals("RegNo"))
        {
            Log.e("Sorting By","RegNo");
            Collections.sort(students, new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    String regNo1 = o1.getRegNo();
                    String regNo2 = o2.getRegNo();
                    int roll1 = Integer.parseInt(regNo1.substring(regNo1.length()-3,regNo1.length()));
                    int roll2 = Integer.parseInt(regNo2.substring(regNo2.length()-3,regNo2.length()));
                    //Log.e("Roll","Roll 1 : "+Integer.parseInt(roll1)+" Roll 2 : "+Integer.parseInt(roll2));
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
            for (Student student:students)
            {
                Log.e("Reg No",student.getRegNo());
            }

        }
        else if(parameter.equals("Name"))
        {
            Collections.sort(students, new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    String name1 = o1.getName();
                    String name2 = o2.getName();
                    if(name1.compareTo(name2)>=0)
                        return 1;
                    else if(name1.compareTo(name2)<=0)
                        return -1;
                    else return 0;
                }
            });
        }
        //studentAdapter.clear();
        studentAdapter = new StudentAdapter(TakeAttendanceStudentList.this,R.layout.student_single_row,R.id.student_reg_no,students);
        studentAdapter.setPresentFlagMap(presentMap);
        studentAdapter.setFragmentManager(getSupportFragmentManager());
        studentAdapter.setShowVertIcon(false);
        studentAdapter.setShowPresentAbsentRadio(true);
        studentList.setAdapter(studentAdapter);
        studentAdapter.notifyDataSetChanged();
    }
}


