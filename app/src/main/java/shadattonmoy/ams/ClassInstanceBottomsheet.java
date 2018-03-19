package shadattonmoy.ams;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import shadattonmoy.ams.attendance.ClassInstance;
import shadattonmoy.ams.attendance.ClassInstanceAdapter;

/**
 * Created by Shadat Tonmoy on 2/15/2018.
 */

public class ClassInstanceBottomsheet extends BottomSheetDialogFragment {

    private LinearLayout bottomSheetEditMenu,bottomSheetDeleteMenu;
    private Course course;
    private ClassInstance classInstance;
    private ListView classInstanceList;
    private ArrayList<ClassInstance> classInstances;
    private ClassInstanceAdapter classInstanceAdapter;
    private SQLiteAdapter sqLiteAdapter;
    private Context context;
    private int position;
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.class_instance_bottom_sheet, null);
        initNodes(contentView);
        setNodesTouchListener();
        dialog.setContentView(contentView);
    }

    /*
    * method to initialize bottom sheet nodes
    * */

    public void initNodes(View view)
    {
        bottomSheetEditMenu = (LinearLayout) view.findViewById(R.id.class_instance_bottom_sheet_edit_menu);
        bottomSheetDeleteMenu = (LinearLayout) view.findViewById(R.id.class_instance_bottom_sheet_delete_menu);
        initSQLiteDB();
    }


    public void setCourse(Course course) {
        this.course = course;
    }

    public void setClassInstance(ClassInstance classInstance) {
        this.classInstance = classInstance;
    }

    /*
            * method to handle bottom sheet menu click
            * */
    public void setNodesTouchListener()
    {
        bottomSheetEditMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"Edit Class Instance "+classInstance.getDate(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(),ClassInstanceEditActivity.class);
                ClassInstanceEditActivity.setClassInstance(classInstance);
                startActivity(intent);
            }
        });

        bottomSheetDeleteMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CourseDeleteConfirmationDialog courseDeleteConfirmationDialog = new CourseDeleteConfirmationDialog();
//                courseDeleteConfirmationDialog.show(getActivity().getFragmentManager(),"Confirmation");
                Toast.makeText(getActivity().getApplicationContext(),"Delete Class Instance "+classInstance.getDate()+" ID "+classInstance.getClassInstanceid(),Toast.LENGTH_SHORT).show();
                classInstances.remove(position);
                classInstanceAdapter.notifyDataSetChanged();

            }
        });
    }

    public ClassInstance getClassInstance() {
        return classInstance;
    }

    public ListView getClassInstanceList() {
        return classInstanceList;
    }

    public void setClassInstanceList(ListView classInstanceList) {
        this.classInstanceList = classInstanceList;
    }

    public ArrayList<ClassInstance> getClassInstances() {
        return classInstances;
    }

    public void setClassInstances(ArrayList<ClassInstance> classInstances) {
        this.classInstances = classInstances;
    }

    public ClassInstanceAdapter getClassInstanceAdapter() {
        return classInstanceAdapter;
    }

    public void setClassInstanceAdapter(ClassInstanceAdapter classInstanceAdapter) {
        this.classInstanceAdapter = classInstanceAdapter;
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(context);
    }

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
