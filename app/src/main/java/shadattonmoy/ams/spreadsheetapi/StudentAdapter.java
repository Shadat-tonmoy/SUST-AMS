package shadattonmoy.ams.spreadsheetapi;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    static StudentBottomSheet studentBottomSheet;
    private boolean showVertIcon,showPresentAbsentRadio;
    private Map flagMap,presentFlagMap;


    public StudentAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int         textViewResourceId, @NonNull List<Student> objects) {
            super(context, resource, textViewResourceId, objects);
        this.context = context;
        showVertIcon = true;
        showPresentAbsentRadio = false;
        flagMap = new HashMap();
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
        final String studentRegNo = student.getRegNo();
        final String studentEmail = student.getEmail();
        int isRegular = student.isRegular();
        long studentId = student.getStudentId();
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

        if(showPresentAbsentRadio)
        {
            LinearLayout presentAbsentRadio = (LinearLayout) row.findViewById(R.id.student_present_absent_layout);
            presentAbsentRadio.setVisibility(View.VISIBLE);
            RadioButton presentRadio = (RadioButton) row.findViewById(R.id.present_radio);
            RadioButton absentRadio = (RadioButton) row.findViewById(R.id.absent_radio);
            RadioGroup presentAbsentRadioGroup = (RadioGroup) row.findViewById(R.id.radio_present_absent);

            if(presentFlagMap.size()>0)
            {
                if(presentFlagMap.get(studentId).equals(new Integer(0)))
                {
                    absentRadio.setChecked(true);
                    student.setPresent(0);
                }
                else
                {
                    presentRadio.setChecked(true);
                    student.setPresent(1);
                }
            }
            if(flagMap.get(studentRegNo)!=null)
            {
                boolean present = (boolean) flagMap.get(studentRegNo);
                Log.e("Reg No ",studentRegNo+" present : "+present);
                if(present){
                    presentRadio.setChecked(true);
                    student.setPresent(1);
                }
                else {

                    absentRadio.setChecked(true);
                    student.setPresent(0);
                }
            }
            else if(presentFlagMap.size()==0)
            {
                Log.e("Reg No ",studentRegNo+" NULL");
                presentAbsentRadioGroup.clearCheck();
            }
            presentRadio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Toast.makeText(context,"Present : "+studentRegNo,Toast.LENGTH_SHORT).show();
                    flagMap.put(studentRegNo,true);
                    student.setPresent(1);
                    Log.e("Updated to","Present "+student.getPresent());
                    Log.e("Putting True ",studentRegNo);

                }
            });

            absentRadio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context,"Absent : "+student.getRegNo(),Toast.LENGTH_SHORT).show();
                    flagMap.put(studentRegNo,false);
                    student.setPresent(0);
                    Log.e("Putting False ",studentRegNo);

                }
            });
        }

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

    public boolean isShowPresentAbsentRadio() {
        return showPresentAbsentRadio;
    }

    public void setShowPresentAbsentRadio(boolean showPresentAbsentRadio) {
        this.showPresentAbsentRadio = showPresentAbsentRadio;
    }

    public Map getPresentFlagMap() {
        return presentFlagMap;
    }

    public void setPresentFlagMap(Map presentFlagMap) {
        this.presentFlagMap = presentFlagMap;
    }
}