package shadattonmoy.ams;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by Shadat Tonmoy on 2/26/2018.
 */

public class PastRecordGraphFragment extends Fragment {

    private PieChart pieChart;
    private ArrayList<Entry> yValues;
    private ArrayList<String> xValues;
    private PieDataSet pieDataSet;
    private PieData pieData;
    private Course course;
    private Student student;
    private View view;
    private Cursor pastRecordCursor;
    private Context context;
    private SQLiteAdapter sqLiteAdapter;
    private int totalClass,presentClass,absentClass;
    private float presentPercent,absentPercent;

    public PastRecordGraphFragment() {
        // Required empty public constructor
    }

    public PastRecordGraphFragment(Course course, Student student, Context context) {
        this.course = course;
        this.student = student;
        this.context = context;
        initSQLiteDB();
        pastRecordCursor = sqLiteAdapter.getStudentPastInfo(String.valueOf(course.getCourseId()),String.valueOf(student.getStudentId()));


        while (pastRecordCursor.moveToNext())
        {
            int indexOfClassDate = pastRecordCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.CLASS_DATE);
            int indexOfClassWeight = pastRecordCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.CLASS_WEIGHT);
            int indexOfIsPresent = pastRecordCursor.getColumnIndex(sqLiteAdapter.sqLiteHelper.IS_PRESENT);
            String classDate = pastRecordCursor.getString(indexOfClassDate);
            int classWeight = pastRecordCursor.getInt(indexOfClassWeight);
            int isPresent = pastRecordCursor.getInt(indexOfIsPresent);
            totalClass+=classWeight;
            if(isPresent>0)
                presentClass+=classWeight;
            else absentClass+=classWeight;
        }
        presentPercent = ((float)presentClass/totalClass)*100f;
        absentPercent = ((float)absentClass/totalClass)*100f;
        Log.e("Total","Present "+presentClass+" Absent "+absentClass+" Total "+totalClass);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.past_record_graph_fragment, container, false);
        pieChart = (PieChart) view.findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        yValues = new ArrayList<Entry>();
        yValues.add(new Entry(absentPercent,0));
        yValues.add(new Entry(presentPercent,1));
        xValues = new ArrayList<String>();
        xValues.add("Absent");
        xValues.add("Present");
        pieDataSet = new PieDataSet(yValues,"Attendance Statistics");
        int [] color={ Color.rgb(192, 57, 43),Color.rgb(26, 188, 156)};
        pieDataSet.setColors(color);
        pieData = new PieData(xValues,pieDataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextColor(Color.WHITE);
        pieData.setValueTextSize(16f);
        pieChart.setData(pieData);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setDescription("Description");
        pieChart.animateXY(1500, 1500);


        return view;
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(context);
    }

}
