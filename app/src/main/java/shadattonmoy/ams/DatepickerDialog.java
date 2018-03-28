package shadattonmoy.ams;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import shadattonmoy.ams.attendance.ClassInstance;

/**
 * Created by Shadat Tonmoy on 2/17/2018.
 */

public class DatepickerDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private TextView editDateView;
    private ClassInstance classInstance;
    private String rawDate;


    public DatepickerDialog() {
        super();

    }

    String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DAY_OF_MONTH);
        int day = c.get(Calendar.DAY_OF_WEEK);

        return new DatePickerDialog(getActivity(), this, year, month, date);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onDateSet(DatePicker view, int year, int month, int date) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        rawDate = date+","+months[month]+","+year+","+days[day];
        String chosen = days[day].substring(0,3)+", "+date + " " + months[month].substring(0, 3) + " " + year;
        Log.e("Raw Date",rawDate);
        ClassInstanceEditActivity.classInstance.setDate(rawDate);
        classInstance.setDate(rawDate);
        //Toast.makeText(getActivity().getApplicationContext(),chosen,Toast.LENGTH_SHORT).show();
        editDateView.setText(chosen);
    }

    public TextView getEditDateView() {
        return editDateView;
    }

    public void setEditDateView(TextView editDateView) {
        this.editDateView = editDateView;
    }

    public ClassInstance getClassInstance() {
        return classInstance;
    }

    public void setClassInstance(ClassInstance classInstance) {
        this.classInstance = classInstance;
    }

    public String getRawDate() {
        return rawDate;
    }

    public void setRawDate(String rawDate) {
        this.rawDate = rawDate;
    }
}
