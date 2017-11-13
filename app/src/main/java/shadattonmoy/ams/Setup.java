package shadattonmoy.ams;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Setup extends AppCompatActivity {

    private SQLiteAdapter sqLiteAdapter;
    private Cursor courseCursor;
    private RelativeLayout noCourseFoundMsg;
    private FloatingActionButton courseAddFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        initialize();
        getCourse();


    }

    public void initialize()
    {
        /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Setup System");

        /*
        * set toolbar navigation Icon
        * */
        toolbar.setNavigationIcon(R.drawable.back);


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
        * find fab by id and set click event listener
        * */
        courseAddFab = (FloatingActionButton) findViewById(R.id.course_add_fab);
        courseAddFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Setup.this,CourseAddActivity.class);
                startActivity(intent);

            }
        });

        /*
        * initialize sqite database
        * */
        initSQLiteDB();



        /*
        * find the no course found UI from xml for toggle visibility
        * */
        noCourseFoundMsg = (RelativeLayout) findViewById(R.id.no_course_found_msg);
    }

    public void initSQLiteDB()
    {
        sqLiteAdapter = new SQLiteAdapter(Setup.this);
        //String res = sqLiteAdapter.getCourse();
        //Toast.makeText(MainActivity.this,res,Toast.LENGTH_SHORT).show();
    }

    public void getCourse()
    {
        courseCursor = sqLiteAdapter.getCourse();
        Toast.makeText(Setup.this,"FOund "+courseCursor.getCount(),Toast.LENGTH_LONG).show();
        if(courseCursor.getCount()<=0)
        {
            noCourseFoundMsg.setVisibility(View.VISIBLE);
        }
        else
        {
            noCourseFoundMsg.setVisibility(View.GONE);
        }
    }


}
