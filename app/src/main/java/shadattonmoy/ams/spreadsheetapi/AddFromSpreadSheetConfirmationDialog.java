package shadattonmoy.ams.spreadsheetapi;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.zip.CheckedOutputStream;

import shadattonmoy.ams.*;

/**
 * Created by Shadat Tonmoy on 2/4/2018.
 */

public class AddFromSpreadSheetConfirmationDialog extends DialogFragment {

    private View view;
    private Course course;
    private List<Student> studentList;
    private SQLiteAdapter sqLiteAdapter;
    private View tabContentView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initSQLiteDB();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.course_delete_confirmation_dialog,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.confirmation_dialog_image);
        TextView textView = (TextView) view.findViewById(R.id.confirmation_dialog_msg);
        textView.setText("This operation will add all the student from the sheet. Are you sure to proceed? ");
        //textView.setBackgroundColor(getResources().getColor(R.color.warningGreen));

        builder.setView(view);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isAdded = false;
                for(Student student:studentList)
                {

                    long id = sqLiteAdapter.addStudentToDB(student,course.getCourseId());
                    if(id>0)
                        isAdded = true;
                    //Toast.makeText(getActivity().getApplicationContext(),"Student Added with ID "+id+" against course ID "+course.getCourseId(),Toast.LENGTH_SHORT).show();
                }
                if(isAdded)
                {
                    String snackBarMessage = studentList.size()+" Students are added For "+course.getCourseCode();
                    Snackbar snackbar = Snackbar.make(tabContentView,snackBarMessage,Snackbar.LENGTH_LONG);
                    snackbar.show();
                }


            }
        });
        return builder.create();
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(getActivity().getApplicationContext());
    }

    public View getTabContentView() {
        return tabContentView;
    }

    public void setTabContentView(View tabContentView) {
        this.tabContentView = tabContentView;
    }
}
