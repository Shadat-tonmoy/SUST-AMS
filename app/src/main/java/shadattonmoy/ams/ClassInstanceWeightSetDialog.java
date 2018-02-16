package shadattonmoy.ams;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import shadattonmoy.ams.attendance.ClassInstance;
import shadattonmoy.ams.attendance.ClassInstanceAdapter;

/**
 * Created by Shadat Tonmoy on 2/15/2018.
 */

public class ClassInstanceWeightSetDialog extends DialogFragment {

    private View view;
    private Spinner weightSpinner;
    private ArrayList<String> weights;
    private String selectedWeight;
    private ClassInstance classInstance;
    private ClassInstanceAdapter classInstanceAdapter;
    private ListView classInstanceList;
    private ArrayList<ClassInstance> classInstances;
    private SQLiteAdapter sqLiteAdapter;
    private Course course;
    private ArrayList<Student> students;
    private RelativeLayout noClassInstancFoundMsg;

    public ClassInstanceWeightSetDialog() {

    }

    public ClassInstanceWeightSetDialog(ClassInstance classInstance) {
        this.classInstance= classInstance;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        init();

        //textView.setBackgroundColor(getResources().getColor(R.color.warningGreen));

        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
                long id = sqLiteAdapter.addClassInstanceToDB(classInstance,course.getCourseId(),classInstance.getWeight());
                Toast.makeText(getActivity().getApplicationContext(),"Class instance added...",Toast.LENGTH_SHORT).show();
                classInstance.setClassInstanceid((int) id);
                if(classInstanceAdapter==null)
                {
                    Log.e("IDDD",classInstance.getClassInstanceid()+"");
                    classInstances.add(classInstance);
                    classInstanceAdapter = new ClassInstanceAdapter(getActivity().getApplicationContext(),R.layout.class_instance_single_row,R.id.numeric_date_view,classInstances);
                    classInstanceList.setAdapter(classInstanceAdapter);
                    classInstanceList.setOnItemClickListener(new ClassInstanceClickHandler(getActivity().getApplicationContext(),course,students));
                    noClassInstancFoundMsg.setVisibility(View.GONE);

                }
                else classInstanceAdapter.add(classInstance);
//                Snackbar snackbar = Snackbar.make(coordinatorLayout,"Class Instance Added",Snackbar.LENGTH_LONG);
//                snackbar.show();
            }
        });
        Log.e("Array Size",students.size()+"");
        return builder.create();
    }

    private void deleteCourse()
    {
        //logic of deleting course goes here....
    }

    private void init()
    {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.class_weight_set_dialog,null);
        weightSpinner = (Spinner) view.findViewById(R.id.course_weight_spinner);
        weights = new ArrayList<String>();
        weights.add("1");
        weights.add("2");
        weights.add("3");
        weights.add("4");
        weights.add("5");
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),R.array.class_instance_weight,R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        weightSpinner.setAdapter(adapter);
//        Log.e("String from dialog",this.selectedWeight.hashCode()+"");

        weightSpinner.setOnItemSelectedListener(new SpinnerItemSelection(classInstance));
    }

    public void setClassInstance(ClassInstance classInstance) {
        this.classInstance = classInstance;
    }

    public void setClassInstanceAdapter(ClassInstanceAdapter classInstanceAdapter) {
        this.classInstanceAdapter = classInstanceAdapter;
    }

    public void setClassInstanceList(ListView classInstanceList) {
        this.classInstanceList = classInstanceList;
    }

    public void setClassInstances(ArrayList<ClassInstance> classInstances) {
        this.classInstances = classInstances;
    }

    public void setSqLiteAdapter(SQLiteAdapter sqLiteAdapter) {
        this.sqLiteAdapter = sqLiteAdapter;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void setNoClassInstancFoundMsg(RelativeLayout noClassInstancFoundMsg) {
        this.noClassInstancFoundMsg = noClassInstancFoundMsg;
    }
}
class SpinnerItemSelection implements AdapterView.OnItemSelectedListener{

    private ClassInstance classInstance;

    public SpinnerItemSelection(ClassInstance classInstance) {
        this.classInstance = classInstance;
        Log.e("String From Listener",this.classInstance.hashCode()+"");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String weight = (String) parent.getItemAtPosition(position);
        classInstance.setWeight(Integer.parseInt(weight));
        Log.e("Selected",this.classInstance.hashCode()+"");

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
