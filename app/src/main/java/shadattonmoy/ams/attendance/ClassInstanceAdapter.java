package shadattonmoy.ams.attendance;

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

import shadattonmoy.ams.Course;
import shadattonmoy.ams.CourseAddBottomSheet;
import shadattonmoy.ams.R;
import shadattonmoy.ams.Setup;

/**
 * Created by Shadat Tonmoy on 2/7/2018.
 */

public class ClassInstanceAdapter extends ArrayAdapter<ClassInstance> {

    private FragmentManager fragmentManager;
    private TextView courseCodeView,courseTitleView,courseSessionView,courseDeptView;
    private static CourseAddBottomSheet courseAddBottomSheet;
    private boolean showVertIcon;
    public ClassInstanceAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<ClassInstance> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        if(row==null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.class_instance_single_row,parent,false);
        }

        /*
        * find views by their IDs
        * */
        ClassInstance classInstance = getItem(position);
        TextView numericDateView = (TextView) row.findViewById(R.id.numeric_date_view);
        TextView monthView = (TextView) row.findViewById(R.id.month_view);
        TextView totalStudentView = (TextView) row.findViewById(R.id.total_student_view);
        TextView absentPresentView = (TextView) row.findViewById(R.id.absent_present_view);
        TextView dayView = (TextView) row.findViewById(R.id.date_view);


        /*
        * get the specific attributes for a particular course
        * */

        ClassDate classDate = classInstance.getClassDate();
        String numericDate = classDate.getDateValue();
        if(Integer.parseInt(numericDate)<=9){
            numericDate = "0"+numericDate;
        }
        String month = classDate.getMonthValue();
        String day = classDate.getDayValue();

        String totalAbsent = "Undefined!!";
        String totalPresent = "Undefined!!";
        if(classInstance.getTotalAbsent()!=-1)
            totalAbsent = String.valueOf(classInstance.getTotalAbsent());
        if(classInstance.getTotalPresent()!=-1)
            totalPresent = String.valueOf(classInstance.getTotalPresent());
        String totalStudent = String.valueOf(classInstance.getTotalStudent());

        /*
        * set the attributes like text or clicklistener to the specific view/textview
        * */
        numericDateView.setText(numericDate);
        monthView.setText(month);
        dayView.setText(day);
        totalStudentView.setText("Total Student : "+totalStudent);
        absentPresentView.setText("Total Present "+totalPresent+"\nTotal Absent "+totalAbsent);


        if(showVertIcon)
        {
            LinearLayout moreVert = (LinearLayout) row.findViewById(R.id.class_instance_more_vert_layout);
            ImageView moreVertIcon = (ImageView) moreVert.findViewById(R.id.class_instance_more_vert_icon);
            moreVertIcon.setImageResource(R.drawable.more_vert);
            moreVert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }

        return row;

    }

    public static void hideBottomSheet()
    {
        courseAddBottomSheet.dismiss();

    }

    public boolean isShowVertIcon() {
        return showVertIcon;
    }

    public void setShowVertIcon(boolean showVertIcon) {
        this.showVertIcon = showVertIcon;
    }
}
