package fr.haronman.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import fr.haronman.todolist.database.SQLiteHelper;
import fr.haronman.todolist.model.Todo;

public class MainActivity2 extends AppCompatActivity {
    private EditText titre;
    private EditText description;
    private Switch check;
    private Button valider;
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

        this.titre = findViewById(R.id.edit_name);
        this.description = findViewById(R.id.edit_desc);
        this.check = findViewById(R.id.check);
        this.valider = findViewById(R.id.validate);

        valider.setEnabled(false);
        titre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Activer ou désactiver le bouton en fonction de si l'EditText est vide ou non
                valider.setEnabled(charSequence.length() > 0);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        titre.setText(todo.getTitre());
        description.setText(todo.getDescription());

    }

    public void modifier(View view) {
        todo.setTitre(this.titre.getText().toString());
        todo.setDescription(this.description.getText().toString());
        todo.setFait(check.isChecked());
        int id = db.updateTodo(todo);
        todo.setId(id);
        Intent i = new Intent(MainActivity2.this, MainActivity.class);
        MainActivity2.this.startActivity(i);
    }
}