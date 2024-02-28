package fr.haronman.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.haronman.todolist.model.Todo;

public class TodoHandler extends SQLiteHelper{
    private SQLiteHelper dbHelper;

    public TodoHandler(Context context){
        super(context);
        dbHelper = new SQLiteHelper(context);
    }

    public int addTodo(Todo todo){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper.KEY_TITLE, todo.getTitre());
        values.put(dbHelper.KEY_DESC, todo.getDescription());
        values.put(dbHelper.KEY_DATE, todo.getDate());
        values.put(dbHelper.KEY_DONE, todo.getFait());
        long insertId = db.insert(dbHelper.TABLE_TODO, null, values);
        db.close();
        return (int) insertId;
    }

    public int updateTodo(Todo todo){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper.KEY_TITLE, todo.getTitre());
        values.put(dbHelper.KEY_DESC, todo.getDescription());
        values.put(dbHelper.KEY_DATE, todo.getDate());
        values.put(dbHelper.KEY_DONE, todo.getFait());
        long updateId=db.update(dbHelper.TABLE_TODO,values,
                dbHelper.KEY_ID + " = ?",
                new String[]{String.valueOf(todo.getId())});
        db.close();
        return (int) updateId;
    }

    public Todo getTodo(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(dbHelper.TABLE_TODO, new String[] { dbHelper.KEY_ID,
                        dbHelper.KEY_TITLE, dbHelper.KEY_DESC, dbHelper.KEY_DATE, dbHelper.KEY_DONE }, dbHelper.KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, dbHelper.KEY_DATE, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Todo todo = new Todo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        return todo;
    }

    public List<Todo> getAllTodo(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Todo> todoList = new ArrayList<Todo>();
        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_TODO;
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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Todo> todoList = new ArrayList<Todo>();
        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_TODO + "WHERE fait = 1";
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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Todo> todoList = new ArrayList<Todo>();
        String selectQuery = "SELECT * FROM " + dbHelper.TABLE_TODO + "WHERE fait = 0";
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
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selectQuery = "SELECT "+dbHelper.KEY_ID+" FROM " + dbHelper.TABLE_TODO +
                " ORDER BY "+dbHelper.KEY_ID+ " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            lastId = Integer.parseInt(cursor.getString(0));
        }else{
            lastId = 0;
        }
        return lastId;
    }

    public void deleteTodo(Todo todo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(dbHelper.TABLE_TODO, dbHelper.KEY_ID + " = ?",
                new String[] { String.valueOf(todo.getId()) });
        db.close();
    }
}
