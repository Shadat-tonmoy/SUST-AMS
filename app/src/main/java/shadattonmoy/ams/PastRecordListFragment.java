package shadattonmoy.ams;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Shadat Tonmoy on 2/26/2018.
 */

public class PastRecordListFragment extends Fragment {
    private Course course;
    private Student student;
    private View view;
    private Cursor pastRecordCursor;
    private SQLiteAdapter sqLiteAdapter;
    private Context context;

    public PastRecordListFragment() {
        // Required empty public constructor
    }

    public PastRecordListFragment(Course course, Student student,Context context) {
        this.course = course;
        this.student = student;
        this.context = context;
        initSQLiteDB();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.past_record_list_fragment, container, false);
        TextView bal = (TextView) view.findViewById(R.id.bal);
        pastRecordCursor = sqLiteAdapter.getStudentPastInfo(String.valueOf(course.getCourseId()),String.valueOf(student.getStudentId()));
        String log = "";
        while (pastRecordCursor.moveToNext())
        {
            String[] columns = pastRecordCursor.getColumnNames();
            int indexOfClassDate = pastRecordCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.CLASS_DATE);
            int indexOfClassWeight = pastRecordCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.CLASS_WEIGHT);
            int indexOfIsPresent = pastRecordCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.IS_PRESENT);
            String classDate = pastRecordCursor.getString(indexOfClassDate);
            int classWeight = pastRecordCursor.getInt(indexOfClassWeight);
            int isPresent = pastRecordCursor.getInt(indexOfIsPresent);
            log+="Date : "+classDate+"\nWeight : "+classWeight+"\nisPresent : "+isPresent+"\n\n";

//            Student student= new Student(studentName,studentRegNo,isStudentRegular);
//            student.setStudentId(studentId);
//            students.add(student);
        }
        bal.setText(log);


        // Inflate the layout for this fragment
        return view;
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(context);
    }

}
