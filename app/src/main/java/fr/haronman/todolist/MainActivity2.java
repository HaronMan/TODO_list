package fr.haronman.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import fr.haronman.todolist.database.SQLiteHelper;
import fr.haronman.todolist.model.Todo;

public class MainActivity2 extends AppCompatActivity {
    private EditText titre;
    private EditText description;
    private SQLiteHelper db;
    private Todo todo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        db = new SQLiteHelper(getApplicationContext());
        todo = db.getTodo(id);

        this.titre = findViewById(R.id.editTextText);
        this.description = findViewById(R.id.editTextText2);
        titre.setText(todo.getTitre());
        description.setText(todo.getDescription());

    }

    public void modifier() {
        todo.setTitre(this.titre.toString());
        todo.setDescription(this.description.toString());
        int id = db.updateTodo(todo);
        todo.setId(id);
        Intent i = new Intent(MainActivity2.this, MainActivity.class);
        MainActivity2.this.startActivity(i);
    }
}