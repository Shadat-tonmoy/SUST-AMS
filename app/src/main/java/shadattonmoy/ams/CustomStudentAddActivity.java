package shadattonmoy.ams;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CustomStudentAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_student_add);
        initialize();
    }

    public void initialize()
    {
     /*
        * find toolbar by id and set title
        * */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Custom Student");
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

    }
}
