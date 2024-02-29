package fr.haronman.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import fr.haronman.todolist.model.Todo;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todolist.db";
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
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_TITLE + " TEXT, " +
                KEY_DESC + " TEXT, " +
                KEY_DATE + " TEXT, " +
                KEY_DONE + " INTEGER) ";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    public int addTodo(Todo todo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, todo.getTitre());
        values.put(KEY_DESC, todo.getDescription());
        values.put(KEY_DATE, todo.getDate());
        values.put(KEY_DONE, todo.getFait());
        long insertId = db.insert(TABLE_TODO, null, values);
        db.close();
        return (int) insertId;
    }

    public int updateTodo(Todo todo){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, todo.getTitre());
        values.put(KEY_DESC, todo.getDescription());
        values.put(KEY_DATE, todo.getDate());
        values.put(KEY_DONE, todo.getFait());
        long updateId=db.update(TABLE_TODO,values,
                KEY_ID + " = ?",
                new String[]{String.valueOf(todo.getId())});
        db.close();
        return (int) updateId;
    }

    public Todo getTodo(int id){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABLE_TODO, new String[] { KEY_ID,
                        KEY_TITLE, KEY_DESC, KEY_DATE, KEY_DONE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, KEY_DATE, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Todo todo = new Todo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return todo;
    }

    public ArrayList<Todo> getAllTodo(){
        SQLiteDatabase db =  getWritableDatabase();
        ArrayList<Todo> todoList = new ArrayList<Todo>();
        String selectQuery = "SELECT * FROM " +  TABLE_TODO;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Todo todo = new Todo(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),cursor.getString(2),cursor.getString(3));
                todoList.add(todo);
            } while (cursor.moveToNext());
        }
        return todoList;
    }

    public List<Todo> getAllTodoDone(){
        SQLiteDatabase db =  getWritableDatabase();
        List<Todo> todoList = new ArrayList<Todo>();
        String selectQuery = "SELECT * FROM " +  TABLE_TODO + "WHERE fait = 1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Todo todo = new Todo(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),cursor.getString(2),cursor.getString(3));
                todoList.add(todo);
            } while (cursor.moveToNext());
        }
        return todoList;
    }

    public List<Todo> getAllTodoUndone(){
        SQLiteDatabase db =  getWritableDatabase();
        List<Todo> todoList = new ArrayList<Todo>();
        String selectQuery = "SELECT * FROM " +  TABLE_TODO + "WHERE fait = 0";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Todo todo = new Todo(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),cursor.getString(2),cursor.getString(3));
                todoList.add(todo);
            } while (cursor.moveToNext());
        }
        return todoList;
    }

    public int getLastId(){
        int lastId;
        SQLiteDatabase db =  getWritableDatabase();
        String selectQuery = "SELECT "+ KEY_ID+" FROM " +  TABLE_TODO +
                " ORDER BY "+ KEY_ID+ " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            lastId = Integer.parseInt(cursor.getString(0));
        }else{
            lastId = 0;
        }
        return lastId;
    }

    public void deleteTodo(Todo todo) {
        SQLiteDatabase db =  getWritableDatabase();
        db.delete( TABLE_TODO,  KEY_ID + " = ?",
                new String[] { String.valueOf(todo.getId()) });
        db.close();
    }
}
