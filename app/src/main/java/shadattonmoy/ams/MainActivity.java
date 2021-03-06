package shadattonmoy.ams;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SQLiteAdapter sqLiteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SUST AMS");

        /*
        * call initSQLiteDB() method to initialize database
        * */
        initSQLiteDB();



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
    * method to open UserManualActivity activity
    * */
    public void openUserManualActivity(View view)
    {
        Intent intent = new Intent(this,UserManualActivity.class);
        startActivity(intent);
    }

    /*
    * method to initialize database connection
    * */
    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(MainActivity.this);
        //String res = sqLiteAdapter.getCourse();
        //Toast.makeText(MainActivity.this,res,Toast.LENGTH_SHORT).show();
    }
}
