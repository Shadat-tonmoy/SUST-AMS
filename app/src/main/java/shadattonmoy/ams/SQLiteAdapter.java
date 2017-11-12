package shadattonmoy.ams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Shadat Tonmoy on 11/13/2017.
 */

public class SQLiteAdapter {

    SQLiteHelper sqLiteHelper;
    public SQLiteAdapter(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
    }

    /*public long insertData(String name, String regNo) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.NAME, name);
        contentValues.put(SQLiteHelper.REG_NO,regNo);
        return db.insert(SQLiteHelper.TABLE_NAME, null, contentValues);
    }

    public String getAllData()
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
        String[] columns = {sqLiteHelper.COURSE_ID,sqLiteHelper.COURSE_TITLE,sqLiteHelper.COURSE_SESSION};
        Cursor cursor = db.query(sqLiteHelper.COURSE,columns,null,null,null,null,null,null);
        String result = "";
        while (cursor.moveToNext())
        {
            int indexOfCourseId = cursor.getColumnIndex(sqLiteHelper.COURSE_ID);
            int indexOfCourseTitle = cursor.getColumnIndex(sqLiteHelper.COURSE_TITLE);
            int indexOfCourseSession = cursor.getColumnIndex(sqLiteHelper.COURSE_SESSION);
            int courseId = cursor.getInt(indexOfCourseId);
            String courseTitle = cursor.getString(indexOfCourseTitle);
            String courseSession = cursor.getString(indexOfCourseSession);
            result = result + " ID : " + courseId + " Title " + courseTitle+ " Session : "+courseSession+"\n";
        }
        return cursor;
    }

    /*public int update(String oldName, String newName)
    {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sqLiteHelper.NAME,newName);
        String whereClause = sqLiteHelper.NAME+"=?";
        String[] whereArgs = {oldName};
        int result = db.update(sqLiteHelper.TABLE_NAME,contentValues,whereClause,whereArgs);
        return result;
    }

    public int delete(String name)
    {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        String whereClause = sqLiteHelper.NAME+"=?";
        String[] whereArgs = {name};
        int result = db.delete(sqLiteHelper.TABLE_NAME,whereClause,whereArgs);
        return result;

    }*/

    static class SQLiteHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "PROJECT350DB";
        private static final String STUDENT = "student";
        private static final String COURSE = "course";
        private static final String CLASS_INSTANCE = "class_instance";
        private static final String ATTENDANCE = "attendance";
        private static final int DATABASE_VERSION = 1;
        private static final String STUDENT_ID = "student_id";
        private static final String STUDENT_NAME = "student_name";
        private static final String STUDENT_REG_NO = "student_reg_no";
        private static final String IS_REGULAR = "isRegular";
        private static final String COURSE_ID = "course_id";
        private static final String COURSE_TITLE = "course_title";
        private static final String COURSE_SESSION = "course_session";
        private static final String CLASS_ID = "class_id";
        private static final String CLASS_DATE = "class_date";
        private static final String ATTENDANCE_ID = "attendance_id";
        private static final String IS_PRESENT = "isPresent";

        private static final String CREATE_TABLE_STUDENT = "create table "+STUDENT+"("+STUDENT_ID+" INTEGER primary key autoincrement,"+STUDENT_NAME+" varchar(255),"+STUDENT_REG_NO+" varchar(255),"+COURSE_ID+" INTEGER, "+IS_REGULAR+" BOOLEAN);";

        private static final String CREATE_TABLE_COURSE = "create table "+COURSE+"("+COURSE_ID+" INTEGER primary key autoincrement,"+COURSE_TITLE+" varchar(255),"+COURSE_SESSION+" varchar(255));";

        private static final String CREATE_TABLE_ATTENDANCE = "create table "+ATTENDANCE+"("+ATTENDANCE_ID+" INTEGER primary key autoincrement,"+CLASS_ID+" INTEGER,"+STUDENT_ID+" INTEGER,"+IS_PRESENT+" BOOLEAN);";

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
