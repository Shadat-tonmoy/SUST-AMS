package shadattonmoy.ams;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;

/**
 * Created by Shadat Tonmoy on 11/13/2017.
 */

public class CourseAddBottomSheet extends BottomSheetDialogFragment {

    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.course_add_bottom_sheet, null);
        dialog.setContentView(contentView);
    }
}
