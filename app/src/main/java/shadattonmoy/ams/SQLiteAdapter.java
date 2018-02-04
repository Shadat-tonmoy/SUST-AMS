package shadattonmoy.ams;

import android.content.ContentValues;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Shadat Tonmoy on 11/13/2017.
 */

public class SQLiteAdapter {

    SQLiteHelper sqLiteHelper;
    public SQLiteAdapter(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    public long addCourseToDB(String courseCode, String courseTitle, String session) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.COURSE_CODE, courseCode);
        contentValues.put(SQLiteHelper.COURSE_TITLE,courseTitle);
        contentValues.put(SQLiteHelper.COURSE_SESSION,session);
        return db.insert(SQLiteHelper.COURSE, null, contentValues);
    }

    public long addStudentToDB(Student student,long courseId)
    {

        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.STUDENT_NAME, student.getName());
        contentValues.put(SQLiteHelper.STUDENT_REG_NO,student.getRegNo());
        contentValues.put(SQLiteHelper.IS_REGULAR,student.isRegular());
        contentValues.put(SQLiteHelper.COURSE_ID, courseId);
        return db.insert(SQLiteHelper.STUDENT, null, contentValues);
    }

    /*public String getAllData()
    {
        StringBuffer stringBuffer = new StringBuffer();
        String detail = "";
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        String[] columns = {SQLiteHelper.UID,SQLiteHelper.NAME,SQLiteHelper.REG_NO};
        Cursor cursor = db.query(SQLiteHelper.TABLE_NAME,columns,null,null,null,null,null);

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(SQLiteHelper.UID);
            int nameIndex = cursor.getColumnIndex(SQLiteHelper.NAME);
            int regNoIndex = cursor.getColumnIndex(SQLiteHelper.REG_NO);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String regNo = cursor.getString(2);
            detail = detail + " "+ id + " " + name + " " + regNo + "\n";
            //stringBuffer.append(name + " " + regNo + "\n");
        }
        return detail;
    }*/

    public Cursor getCourse()
    {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        String[] columns = {sqLiteHelper.COURSE_ID,sqLiteHelper.COURSE_CODE,sqLiteHelper.COURSE_TITLE,sqLiteHelper.COURSE_SESSION};
        Cursor cursor = null;
        try{
            cursor = db.query(sqLiteHelper.COURSE,columns,null,null,null,null,null,null);

        } catch (Exception e)
        {

        }

        return cursor;
    }

    public Cursor getStudents(String courseId)
    {

        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        String[] columns = {sqLiteHelper.STUDENT_ID,sqLiteHelper.STUDENT_NAME,sqLiteHelper.STUDENT_REG_NO,sqLiteHelper.COURSE_ID};
        String whereClause = sqLiteHelper.COURSE_ID+"=?";
        String whereArgs[] = {courseId};
        Cursor cursor = null;
        try{
            cursor = db.query(sqLiteHelper.STUDENT,columns,whereClause,whereArgs,null,null,null,null);

        } catch (Exception e)
        {

        }
        return cursor;
    }

    public int update(Course course)
    {
        String courseId = Long.toString(course.getCourseId());
        String courseCode = course.getCourseCode();
        String courseTitle = course.getCourseTitle();
        String courseSession = course.getCourseSession();
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sqLiteHelper.COURSE_CODE,courseCode);
        contentValues.put(sqLiteHelper.COURSE_TITLE,courseTitle);
        contentValues.put(sqLiteHelper.COURSE_SESSION,courseSession);
        String whereClause = sqLiteHelper.COURSE_ID+"=?";
        String[] whereArgs = {courseId};
        int result = db.update(sqLiteHelper.COURSE,contentValues,whereClause,whereArgs);
        return result;
    }

    /*public int delete(String name)
    {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        String whereClause = sqLiteHelper.NAME+"=?";
        String[] whereArgs = {name};
        int result = db.delete(sqLiteHelper.TABLE_NAME,whereClause,whereArgs);
        return result;

    }*/

    static class SQLiteHelper extends SQLiteOpenHelper {

         static final String DATABASE_NAME = "PROJECT350DB";
         static final String STUDENT = "student";
         static final String COURSE = "course";
         static final String CLASS_INSTANCE = "class_instance";
         static final String ATTENDANCE = "attendance";
         static final int DATABASE_VERSION = 5;
         static final String STUDENT_ID = "student_id";
         static final String STUDENT_NAME = "student_name";
         static final String STUDENT_REG_NO = "student_reg_no";
         static final String IS_REGULAR = "isRegular";
         static final String COURSE_ID = "course_id";
         static final String COURSE_TITLE = "course_title";
         static final String COURSE_CODE = "course_code";
         static final String COURSE_SESSION = "course_session";
         static final String CLASS_ID = "class_id";
         static final String CLASS_DATE = "class_date";
         static final String ATTENDANCE_ID = "attendance_id";
         static final String IS_PRESENT = "isPresent";

        private static final String CREATE_TABLE_STUDENT = "create table "+STUDENT+"("+STUDENT_ID+" INTEGER primary key autoincrement,"+STUDENT_NAME+" varchar(255),"+STUDENT_REG_NO+" varchar(255),"+COURSE_ID+" INTEGER, "+IS_REGULAR+" INTEGER);";

        private static final String CREATE_TABLE_COURSE = "create table "+COURSE+"("+COURSE_ID+" INTEGER primary key autoincrement,"+COURSE_CODE+" varchar(255), "+COURSE_TITLE+" varchar(255),"+COURSE_SESSION+" varchar(255));";

        private static final String CREATE_TABLE_ATTENDANCE = "create table "+ATTENDANCE+"("+ATTENDANCE_ID+" INTEGER primary key autoincrement,"+CLASS_ID+" INTEGER,"+STUDENT_ID+" INTEGER,"+IS_PRESENT+" INTEGER);";

        private static final String CREATE_TABLE_CLASS_INSTANCE = "create table "+CLASS_INSTANCE+"("+CLASS_ID+" INTEGER primary key autoincrement,"+CLASS_DATE+" varchar(255),"+COURSE_ID+" INTEGER);";

        private static final String DROP_TABLE_STUDENT = "drop table if exists "+STUDENT+" ";
        private static final String DROP_TABLE_COURSE = "drop table if exists "+COURSE+"";

        private static final String DROP_TABLE_ATTENDANCE = "drop table if exists "+ATTENDANCE+"";

        private static final String DROP_TABLE_CLASS_INSTANCE = "drop table if exists "+CLASS_INSTANCE+"";

        private Context context;

        public SQLiteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
            Toast.makeText(context,"OOO Called",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Toast.makeText(context,"OnCreate was called",Toast.LENGTH_SHORT).show();
            try {
                db.execSQL(CREATE_TABLE_STUDENT);
                db.execSQL(CREATE_TABLE_COURSE);
                db.execSQL(CREATE_TABLE_CLASS_INSTANCE);
                db.execSQL(CREATE_TABLE_ATTENDANCE);
            } catch (SQLException e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                //e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Toast.makeText(context,"onUpgrade was called",Toast.LENGTH_SHORT).show();
            try {
                db.execSQL(DROP_TABLE_STUDENT);
                db.execSQL(DROP_TABLE_COURSE);
                db.execSQL(DROP_TABLE_CLASS_INSTANCE);
                db.execSQL(DROP_TABLE_ATTENDANCE);
                onCreate(db);
            } catch (SQLException e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                //dde.printStackTrace();
            }
        }
    }
}
