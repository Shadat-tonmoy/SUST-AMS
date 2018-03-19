package shadattonmoy.ams;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import shadattonmoy.ams.attendance.ClassDate;
import shadattonmoy.ams.attendance.ClassInstance;

/**
 * Created by Shadat Tonmoy on 2/27/2018.
 */

public class PastRecordClassInstanceAdapter  extends ArrayAdapter<StudentPastRecord> {
    private TextView courseCodeView,courseTitleView,courseSessionView,courseDeptView;
    private boolean showVertIcon;
    public PastRecordClassInstanceAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<StudentPastRecord> objects) {
        super(context, resource, textViewResourceId, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        if(row==null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.past_record_class_instance_single_row,parent,false);
        }

        /*
        * find views by their IDs
        * */
        StudentPastRecord studentPastRecord = getItem(position);
        TextView numericDateView = (TextView) row.findViewById(R.id.past_record_numeric_date_view);
        TextView monthView = (TextView) row.findViewById(R.id.past_record_month_view);
        TextView classWeightView = (TextView) row.findViewById(R.id.past_record_class_weight_view);
        TextView absentPresentView = (TextView) row.findViewById(R.id.past_record_absent_present_view);
        TextView dayView = (TextView) row.findViewById(R.id.past_record_date_view);


        /*
        * get the specific attributes for a particular course
        * */

        String[] dateArray = studentPastRecord.getClassDate().split(",");
        String numericDate = dateArray[0];
        if(Integer.parseInt(numericDate)<=9){
            numericDate = "0"+numericDate;
        }
        String month = dateArray[1];
        String day = dateArray[3];

        int classWeight = studentPastRecord.getClassWeight();
        int isPresent = studentPastRecord.getIsPresent();

        if(isPresent>0)
        {
            absentPresentView.setText("Present");
            absentPresentView.setBackgroundResource(R.drawable.present_background);
        }
        else
        {
            absentPresentView.setText("Absent");
            absentPresentView.setBackgroundResource(R.drawable.absent_background);
        }

        /*
        * set the attributes like text or clicklistener to the specific view/textview
        * */
        numericDateView.setText(numericDate);
        monthView.setText(month);
        dayView.setText(day);
        classWeightView.setText("Class Weight : "+classWeight);
        return row;

    }
}
