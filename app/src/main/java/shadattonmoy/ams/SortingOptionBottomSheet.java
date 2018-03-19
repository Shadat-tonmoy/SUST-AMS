package shadattonmoy.ams;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import shadattonmoy.ams.attendance.ClassInstance;

/**
 * Created by Shadat Tonmoy on 2/17/2018.
 */

public class SortingOptionBottomSheet extends BottomSheetDialogFragment {

    private LinearLayout bottomSheetRegNoMenu,bottomSheetNameMenu;
    private Course course;
    private ClassInstance classInstance;
    private TakeAttendanceStudentList takeAttendanceStudentList;
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.sorting_option_bottom_sheet, null);
        initNodes(contentView);
        setNodesTouchListener();
        dialog.setContentView(contentView);
    }

    /*
    * method to initialize bottom sheet nodes
    * */

    public void initNodes(View view)
    {
        bottomSheetRegNoMenu = (LinearLayout) view.findViewById(R.id.sort_bottom_sheet_reg_no);
        bottomSheetNameMenu = (LinearLayout) view.findViewById(R.id.sort_bottom_sheet_name);
    }




    /*
            * method to handle bottom sheet menu click
            * */
    public void setNodesTouchListener()
    {
        bottomSheetRegNoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity().getApplicationContext(),"Sort By RegNo ",Toast.LENGTH_SHORT).show();
                takeAttendanceStudentList.setSortingParameter("RegNo");
                //takeAttendanceStudentList.sortStudentList("RegNo");

            }
        });

        bottomSheetNameMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getActivity().getApplicationContext(),"Sort By Name ",Toast.LENGTH_SHORT).show();
                takeAttendanceStudentList.setSortingParameter("Name");
                //takeAttendanceStudentList.sortStudentList("Name");
            }
        });
    }

    public void setTakeAttendanceStudentList(TakeAttendanceStudentList takeAttendanceStudentList) {
        this.takeAttendanceStudentList = takeAttendanceStudentList;
    }
}
