package shadattonmoy.ams;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Shadat Tonmoy on 2/5/2018.
 */

public class StudentBottomSheet extends BottomSheetDialogFragment {

    private LinearLayout bottomSheetEditMenu,bottomSheetDeleteMenu;
    private Student student;
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.student_bottom_sheet, null);
        initNodes(contentView);
        setNodesTouchListener();
        dialog.setContentView(contentView);
    }

    /*
    * method to initialize bottom sheet nodes
    * */

    public void initNodes(View view)
    {
        bottomSheetEditMenu = (LinearLayout) view.findViewById(R.id.student_bottom_sheet_edit_menu);
        bottomSheetDeleteMenu = (LinearLayout) view.findViewById(R.id.student_bottom_sheet_delete_menu);
    }


    public void setStudent(Student student) {
        this.student = student;
    }

    /*
        * method to handle bottom sheet menu click
        * */
    public void setNodesTouchListener()
    {
        bottomSheetEditMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(),"Edit Student",Toast.LENGTH_SHORT).show();

//                Intent intent = new Intent(getActivity(),CourseEditActivity.class);
//                CourseEditActivity.setCourse(course);
//                startActivity(intent);
            }
        });

        bottomSheetDeleteMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                CourseDeleteConfirmationDialog courseDeleteConfirmationDialog = new CourseDeleteConfirmationDialog();
//                courseDeleteConfirmationDialog.show(getActivity().getFragmentManager(),"Confirmation");
            }
        });


    }
}
