package shadattonmoy.ams;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SUST AMS");



    }

    /*
    * method to open TakeAttendance activity
    * */
    public void openTakeAttendanceActivity(View view)
    {
        Intent intent = new Intent(this,TakeAttendance.class);
        startActivity(intent);
    }

    /*
    * method to open Setup activity
    * */
    public void openSetupActivity(View view)
    {
        Intent intent = new Intent(this,Setup.class);
        startActivity(intent);

    }

    /*
    * method to open PreviousRecord activity
    * */
    public void openPastRecordActivity(View view)
    {
        Intent intent = new Intent(this,PastRecord.class);
        startActivity(intent);

    }

    /*
    * method to open UserManual activity
    * */
    public void openUserManualActivity(View view)
    {
        Intent intent = new Intent(this,UserManual.class);
        startActivity(intent);

    }
}
