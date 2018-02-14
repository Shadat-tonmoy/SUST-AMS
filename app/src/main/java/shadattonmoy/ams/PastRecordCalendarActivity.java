package shadattonmoy.ams;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import shadattonmoy.ams.spreadsheetapi.StudentAdapter;

public class PastRecordCalendarActivity extends AppCompatActivity {

    private Student student;
    private SQLiteAdapter sqLiteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_record_calendar);
        student = (Student) getIntent().getSerializableExtra("Student");
        initialize();

    }

    public void initialize()
    {

        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(student.getRegNo());

        /*
        * set toolbar navigation Icon
        * */
        toolbar.setNavigationIcon(R.drawable.back);

        setSupportActionBar(toolbar);


        /*
        * set toolbar navigation Icon Click Listener
        * */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        /*
        * initialize sqite database
        * */
        initSQLiteDB();
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(PastRecordCalendarActivity.this);
    }
}
