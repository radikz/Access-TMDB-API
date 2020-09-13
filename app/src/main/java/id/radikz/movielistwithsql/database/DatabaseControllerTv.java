package id.radikz.movielistwithsql.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class DatabaseControllerTv extends SQLiteOpenHelper {
    private static final String LOGCAT = null;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "dbmovie.db";
    private static final String TABLE_Users = "tv";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_RATING = "rating";
    private static final String KEY_DATE = "date";
    private static final String KEY_PHOTO = "photo";

    public DatabaseControllerTv(Context applicationcontext) {
        super(applicationcontext, "tv", null, 1);
        Log.d(LOGCAT, "Created");
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String query = "CREATE TABLE " + TABLE_Users + "("
                + KEY_ID + " INTEGER PRIMARY KEY NOT NULL,"
                + KEY_TITLE + " TEXT UNIQUE,"
                + KEY_RATING + " TEXT,"
                + KEY_DATE + " TEXT, "
                + KEY_PHOTO + " TEXT) ";
//                + "PRIMARY KEY (title) )";
        database.execSQL(query);
    }

    public String InsertData(String title, String rating, String date, String photo) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cValues = new ContentValues();
            cValues.put(KEY_TITLE, title);
            cValues.put(KEY_RATING, rating);
            cValues.put(KEY_DATE, date);
            cValues.put(KEY_PHOTO, photo);
            // Insert the new row, returning the primary key value of the new row
            db.insert(TABLE_Users,null, cValues);
            db.close();
            return "Added Successfully Tv";
        } catch (Exception ex) {
            return ex.getMessage().toString();
        }

    }

    public void deleteData(int id){
        SQLiteDatabase database = getWritableDatabase();
        //query to delete record using id
        String sql = "DELETE FROM " + TABLE_Users +" WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double)id);

        statement.execute();
        database.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int version_old,
                          int current_version) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }

    public Cursor getCompanies() {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_Users + " order by " + KEY_ID + " desc";
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            return cursor;
        } catch (Exception ex) {
            return null;
        }
    }

    public Cursor getData() {
        try {
            String selectQuery = "SELECT id FROM " + TABLE_Users;
            SQLiteDatabase database = this.getReadableDatabase();
            Cursor cursor = database.rawQuery(selectQuery, null);
            return cursor;
        } catch (Exception ex) {
            return null;
        }
    }
}
