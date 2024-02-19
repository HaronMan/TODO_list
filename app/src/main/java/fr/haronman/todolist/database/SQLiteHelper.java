package fr.haronman.todolist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MySQLite";
    protected static final String TABLE_TODO = "TODOList";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "titre";
    public static final String KEY_DESC = "description";
    public static final String KEY_DATE = "date";
    public static final String KEY_DONE = "fait";

    public SQLiteHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_TODO +
                " (" + KEY_ID + "INTEGER PRIMARY KEY, " +
                KEY_TITLE + "TEXT, " +
                KEY_DESC + "TEXT, " +
                KEY_DATE + "TEXT, " +
                KEY_DONE + "INTEGER) ";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }
}
