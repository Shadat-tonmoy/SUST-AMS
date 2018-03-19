package shadattonmoy.ams.spreadsheetapi;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shadattonmoy.ams.R;
import shadattonmoy.ams.Student;
import shadattonmoy.ams.StudentBottomSheet;
import shadattonmoy.ams.StudentPreviousRecord;

/**
 * Created by Shadat Tonmoy on 2/1/2018.
 */

public class StudentAdapter extends ArrayAdapter<Student> {

    private FragmentManager fragmentManager;
    private Context context;
    static StudentBottomSheet studentBottomSheet;
    private boolean showVertIcon,showPresentAbsentRadio,showPreviousRecordCard;
    private Map flagMap,presentFlagMap,previousRecordFlagMap;
    private CardView previosRecordCard;
    private LinearLayout pastRecordContainer;


    public StudentAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int         textViewResourceId, @NonNull List<Student> objects) {
            super(context, resource, textViewResourceId, objects);
        this.context = context;
        showVertIcon = true;
        showPresentAbsentRadio = false;
        showPreviousRecordCard = false;
        flagMap = new HashMap();
        previousRecordFlagMap = new HashMap();
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
        TextView studentRegularDropper = (TextView) row.findViewById(R.id.student_regular_dropper);



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

                }
            });


        }


        /*
        * get the specific attributes for a particular course
        * */
        String studentName = student.getName();
        final String studentRegNo = student.getRegNo();
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
        studentRegularDropper.setText(regular);

        if(showPresentAbsentRadio)
        {
            LinearLayout presentAbsentRadio = (LinearLayout) row.findViewById(R.id.student_present_absent_layout);
            presentAbsentRadio.setVisibility(View.VISIBLE);
            RadioButton presentRadio = (RadioButton) row.findViewById(R.id.present_radio);
            RadioButton absentRadio = (RadioButton) row.findViewById(R.id.absent_radio);
            RadioGroup presentAbsentRadioGroup = (RadioGroup) row.findViewById(R.id.radio_present_absent);

            if(presentFlagMap.size()>0)
            {
                Log.e("Size",presentFlagMap.size()+"");
                Log.e("Student Id",studentId+"");
                if(presentFlagMap.get(studentId)!=null && presentFlagMap.get(studentId).equals(new Integer(0)))
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
                    flagMap.put(studentRegNo,true);
                    student.setPresent(1);
                    Log.e("Updated to","Present "+student.getPresent());
                    Log.e("Putting True ",studentRegNo);

                }
            });

            absentRadio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flagMap.put(studentRegNo,false);
                    student.setPresent(0);
                    Log.e("Putting False ",studentRegNo);

                }
            });
        }

        if(showPreviousRecordCard)
        {


            previosRecordCard = (CardView) row.findViewById(R.id.previous_record_card);
            previosRecordCard.setVisibility(View.VISIBLE);

            pastRecordContainer = (LinearLayout) row.findViewById(R.id.card_view_container);
            ArrayList<StudentPreviousRecord> pastRecord = student.getPastRecord();
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            dpWidth-=52.0; //padding (12+12=24) card margin (8+8=16) dot margin extra left first + right last (6+6=12)
            int numOfDots = (int)Math.floor((double)dpWidth / 23)-1;
            int totalRecords = pastRecord.size();
            int numOfLinearLayout = (int)Math.ceil((double)totalRecords/numOfDots);
            int puttedDot = 0;
            Log.e("Student Past Record",student.getPastRecord().toString());
            if(previousRecordFlagMap.get(student.getStudentId())==null)
            {
                previousRecordFlagMap.put(student.getStudentId(),student.getPastRecord());
                pastRecord = student.getPastRecord();
            }
            else
            {
                pastRecord = (ArrayList<StudentPreviousRecord>) previousRecordFlagMap.get(student.getStudentId());

            }
            pastRecordContainer.removeAllViews();
            for(int i=0;i<numOfLinearLayout;i++)
            {

                LinearLayout linearLayout = new LinearLayout(context);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                for(int j=0;j<numOfDots;j++)
                {
                    LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    TextView textView = (TextView)inflater.inflate(R.layout.single_dot,linearLayout,false);
                    StudentPreviousRecord currentDot = pastRecord.get(puttedDot);
                    if(currentDot.getIsPresent()==0)
                    {
                        textView.setBackgroundResource(R.drawable.round_red);
                        textView.setText(currentDot.getClassWeight()+"");
                    }
                    else  {
                        textView.setBackgroundResource(R.drawable.round_green2);
                        textView.setText(currentDot.getClassWeight()+"");
                    }
                    linearLayout.addView(textView);
                    puttedDot++;
                    if(puttedDot>=totalRecords)
                        break;
                }
                pastRecordContainer.addView(linearLayout);
            }

            Log.e("Dimension","Width : "+dpWidth+" Height : "+dpHeight);


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
        Log.e("PrsentFlagMap",presentFlagMap.toString());
    }

    public boolean getShowPreviousRecordCard() {
        return showPreviousRecordCard;
    }

    public void setShowPreviousRecordCard(boolean showPreviousRecordCard) {
        this.showPreviousRecordCard = showPreviousRecordCard;
    }
}