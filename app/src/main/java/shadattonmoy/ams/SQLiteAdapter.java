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

import shadattonmoy.ams.attendance.ClassInstance;

/**
 * Created by Shadat Tonmoy on 11/13/2017.
 */

public class SQLiteAdapter {

    public SQLiteHelper sqLiteHelper;
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

        Log.e("Adding TO DB","Regular :"+student.isRegular());
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.STUDENT_NAME, student.getName());
        contentValues.put(SQLiteHelper.STUDENT_REG_NO,student.getRegNo());
        contentValues.put(SQLiteHelper.IS_REGULAR,student.isRegular());
        contentValues.put(SQLiteHelper.COURSE_ID, courseId);
        return db.insert(SQLiteHelper.STUDENT, null, contentValues);
    }

    public long addClassInstanceToDB(ClassInstance classInstance, long courseId,int classWeight)
    {

        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.CLASS_DATE, classInstance.getDate());
        contentValues.put(SQLiteHelper.COURSE_ID,courseId);
        contentValues.put(SQLiteHelper.CLASS_WEIGHT,classWeight);
        return db.insert(SQLiteHelper.CLASS_INSTANCE, null, contentValues);
    }

    public long addAttendanceToDB(int classId,long studentId, int isPresent)
    {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLiteHelper.CLASS_ID, classId);
        contentValues.put(SQLiteHelper.STUDENT_ID,studentId);
        contentValues.put(SQLiteHelper.IS_PRESENT,isPresent);
        return db.insert(SQLiteHelper.ATTENDANCE, null, contentValues);
    }


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
        String[] columns = {sqLiteHelper.STUDENT_ID,sqLiteHelper.STUDENT_NAME,sqLiteHelper.STUDENT_REG_NO,sqLiteHelper.COURSE_ID,sqLiteHelper.IS_REGULAR};
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

    public Cursor getClassInstances(String courseId)
    {

        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        Cursor cursor = null;
        try{
            cursor = db.rawQuery(SQLiteHelper.GET_CLASS_INSTANCE_QUERY, new String[]{courseId});
            Log.e("Course ID ",courseId);
            Log.e("Query ","Size "+cursor.getCount());

        } catch (Exception e)
        {
            Log.e("Exception ",e.getMessage());

        }
        return cursor;
    }

    public Cursor getAttendance(String classInstanceId)
    {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        Cursor cursor = null;
        String[] columns = {sqLiteHelper.STUDENT_ID,sqLiteHelper.IS_PRESENT,sqLiteHelper.ATTENDANCE_ID};
        String whereClause = sqLiteHelper.CLASS_ID+"=?";
        String whereArgs[] = {classInstanceId};
        try{
            cursor = db.query(sqLiteHelper.ATTENDANCE,columns,whereClause,whereArgs,null,null,null,null);

        } catch (Exception e)
        {

        }
        return cursor;
    }

    public Cursor getAttendance(String classInstanceId,String studentId)
    {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        Cursor cursor = null;
        String[] columns = {sqLiteHelper.STUDENT_ID,sqLiteHelper.IS_PRESENT,sqLiteHelper.ATTENDANCE_ID};
        String whereClause = sqLiteHelper.CLASS_ID+"=?"+" AND "+sqLiteHelper.STUDENT_ID+"=?";
        String whereArgs[] = {classInstanceId,studentId};
        try{
            cursor = db.query(sqLiteHelper.ATTENDANCE,columns,whereClause,whereArgs,null,null,null,null);

        } catch (Exception e)
        {

        }
        return cursor;
    }

    public int getPresentStudentNum(String classId)
    {

        int count = -1;
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        Cursor cursor = null;
        try{
            cursor = db.rawQuery(SQLiteHelper.GET_NUM_OF_PRESENT_STUDENT, new String[]{classId});
            cursor.moveToFirst();
            count = cursor.getInt(0);
            Log.e("Total Present ","For ClassID"+classId);

        } catch (Exception e)
        {
            Log.e("Exception ",e.getMessage());

        }
        return count;
    }

//    public Cursor getAttendanceInfo(String classInstanceId, String studentId)
//    {
//
//        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
//        Cursor cursor = null;
//        try{
//            cursor = db.rawQuery(SQLiteHelper.GET_STUDENT_PRESENT_INFO, new String[]{classInstanceId});
//            Log.e("Class instance ID ",classInstanceId);
//            Log.e("Query ","Size "+cursor.getCount());
//
//        } catch (Exception e)
//        {
//            Log.e("Exception ",e.getMessage());
//
//        }
//        return cursor;
//    }


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

    public int updateClassInstance(ClassInstance classInstance)
    {
        String classInstanceId = Long.toString(classInstance.getClassInstanceid());
        String classInstanceDate = classInstance.getDate();
        int classInstanceWeight = classInstance.getWeight();
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sqLiteHelper.CLASS_DATE,classInstanceDate);
        contentValues.put(sqLiteHelper.CLASS_WEIGHT,classInstanceWeight);
        String whereClause = sqLiteHelper.CLASS_ID+"=?";
        String[] whereArgs = {String.valueOf(classInstance.getClassInstanceid())};
        int result = db.update(sqLiteHelper.CLASS_INSTANCE,contentValues,whereClause,whereArgs);
        return result;
    }

    public int updateAttendance(int classId,long studentId, int isPresent)
    {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(sqLiteHelper.IS_PRESENT,isPresent);
        String whereClause = sqLiteHelper.CLASS_ID+"=? AND "+sqLiteHelper.STUDENT_ID+"=?";
        String[] whereArgs = {String.valueOf(classId),String.valueOf(studentId)};
        int result = db.update(sqLiteHelper.ATTENDANCE,contentValues,whereClause,whereArgs);
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

    public static class SQLiteHelper extends SQLiteOpenHelper {

         static final String DATABASE_NAME = "PROJECT350DB";
         static final String STUDENT = "student";
         static final String COURSE = "course";
         static final String CLASS_INSTANCE = "class_instance";
         static final String ATTENDANCE = "attendance";
         static final int DATABASE_VERSION = 11;
         static final String STUDENT_ID = "student_id";
         static final String STUDENT_NAME = "student_name";
         static final String STUDENT_REG_NO = "student_reg_no";
         static final String IS_REGULAR = "is_regular";
         static final String COURSE_ID = "course_id";
         static final String COURSE_TITLE = "course_title";
         static final String COURSE_CODE = "course_code";
         static final String COURSE_SESSION = "course_session";
         static final String CLASS_ID = "class_id";
         static final String CLASS_DATE = "class_date";
         static final String ATTENDANCE_ID = "attendance_id";
         static final String IS_PRESENT = "is_present";
        static final String CLASS_WEIGHT = "class_weight";


        private static final String CREATE_TABLE_STUDENT = "create table "+STUDENT+"("+STUDENT_ID+" INTEGER primary key autoincrement,"+STUDENT_NAME+" varchar(255),"+STUDENT_REG_NO+" varchar(255),"+COURSE_ID+" INTEGER,"+IS_REGULAR+" INTEGER);";

        private static final String CREATE_TABLE_COURSE = "create table "+COURSE+"("+COURSE_ID+" INTEGER primary key autoincrement,"+COURSE_CODE+" varchar(255), "+COURSE_TITLE+" varchar(255),"+COURSE_SESSION+" varchar(255));";

        private static final String CREATE_TABLE_ATTENDANCE = "create table "+ATTENDANCE+"("+ATTENDANCE_ID+" INTEGER primary key autoincrement,"+CLASS_ID+" INTEGER,"+STUDENT_ID+" INTEGER,"+IS_PRESENT+" INTEGER);";

        private static final String CREATE_TABLE_CLASS_INSTANCE = "create table "+CLASS_INSTANCE+"("+CLASS_ID+" INTEGER primary key autoincrement,"+CLASS_DATE+" varchar(255),"+COURSE_ID+" INTEGER, "+CLASS_WEIGHT+" INTEGER DEFAULT 1);";

        private static final String GET_CLASS_INSTANCE_QUERY = "SELECT * FROM class_instance WHERE course_id=?";

        private static final String GET_NUM_OF_PRESENT_STUDENT = "SELECT COUNT(*) FROM attendance WHERE class_id=? and is_present>=1";

        //private static final String GET_STUDENT_PRESENT_INFO = "SELECT student_id,is_present FROM class_instance JOIN attendance ON class_instance.class_id = attendance.class_id WHERE attendance.class_id=?";

        private static final String GET_STUDENT_PRESENT_INFO = "SELECT is_present FROM attendance WHERE class_id=? and student_id=?";

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
                //db.execSQL(CREATE_TABLE_STUDENT);
                //db.execSQL(CREATE_TABLE_COURSE);
                db.execSQL(CREATE_TABLE_CLASS_INSTANCE);
                //db.execSQL(CREATE_TABLE_ATTENDANCE);
            } catch (SQLException e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                //e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Toast.makeText(context,"onUpgrade was called",Toast.LENGTH_SHORT).show();
            try {
                //db.execSQL(DROP_TABLE_STUDENT);
                //db.execSQL(DROP_TABLE_COURSE);
                db.execSQL(DROP_TABLE_CLASS_INSTANCE);
                //db.execSQL(DROP_TABLE_ATTENDANCE);
                onCreate(db);
            } catch (SQLException e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                //dde.printStackTrace();
            }
        }
    }
}
