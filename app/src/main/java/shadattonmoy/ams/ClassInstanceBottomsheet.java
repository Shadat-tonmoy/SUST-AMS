package shadattonmoy.ams;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import shadattonmoy.ams.attendance.ClassInstance;

/**
 * Created by Shadat Tonmoy on 2/15/2018.
 */

public class ClassInstanceBottomsheet extends BottomSheetDialogFragment {

    private LinearLayout bottomSheetEditMenu,bottomSheetDeleteMenu;
    private Course course;
    private ClassInstance classInstance;
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
                Toast.makeText(getActivity().getApplicationContext(),"Delete Class Instance "+classInstance.getDate(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
