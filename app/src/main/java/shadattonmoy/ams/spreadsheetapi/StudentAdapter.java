package shadattonmoy.ams.spreadsheetapi;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import shadattonmoy.ams.CourseAddBottomSheet;
import shadattonmoy.ams.R;
import shadattonmoy.ams.Student;
import shadattonmoy.ams.StudentBottomSheet;

/**
 * Created by Shadat Tonmoy on 2/1/2018.
 */

public class StudentAdapter extends ArrayAdapter<Student> {

    private FragmentManager fragmentManager;
    private Context context;
    private static StudentBottomSheet studentBottomSheet;
    private boolean showVertIcon;

    public StudentAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int         textViewResourceId, @NonNull List<Student> objects) {
            super(context, resource, textViewResourceId, objects);
        this.context = context;
        showVertIcon = true;
    }

@NonNull
@Override
    public View getView(int position, @Nullable final View convertView, @NonNull
        ViewGroup parent) {

        View row = convertView;
        if(row==null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.student_single_row,parent,false);
        }

        /*
        * find views by their IDs
        * */
        final Student student = getItem(position);
        TextView studentIconView = (TextView) row.findViewById(R.id.student_icon);
        TextView studentNameView = (TextView) row.findViewById(R.id.student_name);
        TextView studentRegNoView = (TextView) row.findViewById(R.id.student_reg_no);
        TextView studentEmailView = (TextView) row.findViewById(R.id.student_email);


        if(showVertIcon)
        {
            LinearLayout moreVert = (LinearLayout) row.findViewById(R.id.student_more_vert_layout);
            ImageView moreVertIcon = (ImageView) row.findViewById(R.id.student_more_vert_icon);
            moreVertIcon.setImageResource(R.drawable.more_vert);
            moreVert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    studentBottomSheet = new StudentBottomSheet();
                    studentBottomSheet.setStudent(student);
                    studentBottomSheet.show(fragmentManager,studentBottomSheet.getTag());
//            courseAddBottomSheet = new CourseAddBottomSheet();
//            courseAddBottomSheet.setCourse(course);
//            courseAddBottomSheet.show(fragmentManager,courseAddBottomSheet.getTag());

                }
            });


        }



        /*
        * get the specific attributes for a particular course
        * */
        String studentName = student.getName();
        String studentRegNo = student.getRegNo();
        String studentEmail = student.getEmail();
        int isRegular = student.isRegular();
        String regular = "Dropper";
        if(isRegular==1)
            regular = "Regular";


        String iconText = String.valueOf(studentName.charAt(0));
        if(isRegular==1)
            studentIconView.setBackgroundResource(R.drawable.round_light_green);
        else studentIconView.setBackgroundResource(R.drawable.round_red);

        /*
        * set the attributes like text or clicklistener to the specific view/textview
        * */
        studentIconView.setText(iconText);
        studentNameView.setText(studentName);
        studentRegNoView.setText(studentRegNo);
        studentEmailView.setText(regular);

        return row;

    }

    public FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public boolean isShowVertIcon() {
        return showVertIcon;
    }

    public void setShowVertIcon(boolean showVertIcon) {
        this.showVertIcon = showVertIcon;
    }
}