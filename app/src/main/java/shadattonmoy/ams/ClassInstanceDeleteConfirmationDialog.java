package shadattonmoy.ams;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import shadattonmoy.ams.attendance.ClassInstance;
import shadattonmoy.ams.attendance.ClassInstanceAdapter;

/**
 * Created by Shadat Tonmoy on 3/26/2018.
 */

public class ClassInstanceDeleteConfirmationDialog extends DialogFragment {

    private View view;
    private ClassInstance classInstance;
    private SQLiteAdapter sqLiteAdapter;
    private int viewPosition;
    private ListView classInstanceList;
    private ArrayList<ClassInstance> classInstances;
    private ClassInstanceAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.course_delete_confirmation_dialog,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.confirmation_dialog_image);
        TextView textView = (TextView) view.findViewById(R.id.confirmation_dialog_msg);
        textView.setText("This operation will permanently delete the class instance and all the related information with the class instance. Are you sure to proceed? ");
        initSQLiteDB();

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
                deleteClassInstance();
            }
        });
        return builder.create();
    }

    private void deleteClassInstance()
    {


        int result = sqLiteAdapter.deleteClassInstance(String.valueOf(classInstance.getClassInstanceid()));
        if(result>0)
        {
            ClassInstanceAdapter.hideBottomSheet();
            classInstances.remove(viewPosition);
            adapter.notifyDataSetChanged();
            //Toast.makeText(getActivity().getApplicationContext(),"Class instance Records Deleted",Toast.LENGTH_SHORT).show();

        }
//
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(getActivity().getApplicationContext());
    }

    public int getViewPosition() {
        return viewPosition;
    }

    public void setViewPosition(int viewPosition) {
        this.viewPosition = viewPosition;
    }


    public void setClassInstance(ClassInstance classInstance) {
        this.classInstance = classInstance;
    }

    public void setClassInstanceList(ListView classInstanceList) {
        this.classInstanceList = classInstanceList;
    }

    public void setClassInstances(ArrayList<ClassInstance> classInstances) {
        this.classInstances = classInstances;
    }

    public void setAdapter(ClassInstanceAdapter adapter) {
        this.adapter = adapter;
    }
}