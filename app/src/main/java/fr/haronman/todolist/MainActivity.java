package fr.haronman.todolist;

import fr.haronman.todolist.database.SQLiteHelper;
import fr.haronman.todolist.model.Todo;

import android.content.Intent;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ArrayList<Todo> liste;
    private RecyclerView courseRV;
    private RecyclerViewAdapter recyclerViewAdapter;
    private TextView progressText;
    private ProgressBar progressBar;
    private SQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        liste = new ArrayList<>();
        db = new SQLiteHelper(getApplicationContext());

        EditText editText = findViewById(R.id.editTextText3);
        Button add = findViewById(R.id.add);
        add.setEnabled(false);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // Activer ou désactiver le bouton en fonction de si l'EditText est vide ou non
                add.setEnabled(charSequence.length() > 0);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        courseRV = findViewById(R.id.idRVCourse);
        affiche();

        progressText = findViewById(R.id.progress_text);
        progressBar = findViewById(R.id.progress_bar);
        updateProgress();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Todo deletedCourse = liste.get(viewHolder.getAdapterPosition());
                int position = viewHolder.getAdapterPosition();
                liste.remove(viewHolder.getAdapterPosition());
                db.deleteTodo(deletedCourse);
                updateProgress();
                recyclerViewAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                String text = "Tache \""+ deletedCourse.getTitre() +"\" supprimée";
                Snackbar.make(courseRV, text, Snackbar.LENGTH_LONG).setAction("Annuler", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        liste.add(position, deletedCourse);
                        updateProgress();
                        recyclerViewAdapter.notifyItemInserted(position);
                    }
                }).show();
            }
        }).attachToRecyclerView(courseRV);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Todo todo = liste.get(position);
                updateCheck(todo);
                updateProgress();
                recyclerViewAdapter.notifyItemChanged(position);
            }
        }).attachToRecyclerView(courseRV);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //Action menu
    public void deleteAll(MenuItem item){
        if(liste.size() == 0){
            Toast toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setText("Vous n'avez aucune tache");
            toast.show();
        }else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Confirmation");
            alert.setMessage("Etes-vous sur de vouloir tout supprimer ?");

            alert.setPositiveButton("Accepter", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    liste.clear();
                    db.deleteAllTodo();
                    Snackbar.make(courseRV, "Suppression de toutes les taches effectués", Snackbar.LENGTH_LONG).show();

                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });
            alert.setNegativeButton("Refuser", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();
        }
    }

    public void ajouter(View v) {
        courseRV = findViewById(R.id.idRVCourse);
        EditText e = findViewById(R.id.editTextText3);
        Todo todo = new Todo(e.getText().toString());
        liste.add(todo);
        int id = db.addTodo(todo);
        todo.setId(id);
        todo.setDate(Calendar.getInstance().getTime().toString());
        db.updateTodo(todo);
        updateProgress();
        affiche();
    }

    public void affiche() {
        liste = db.getAllTodo();
        recyclerViewAdapter = new RecyclerViewAdapter(liste, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setLayoutManager(manager);
        courseRV.setAdapter(recyclerViewAdapter);
    }

    public void updateCheck(Todo todo){
        todo.setFait(!todo.getFait());
        db.updateTodo(todo);
    }

    public void updateProgress(){
        if(liste.size() > 0) {
            int nbrValidee = cptTacheValidee(), taille = liste.size();

            progressText.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            String text = "Progression des tâches actuelles ("+nbrValidee+"/" +taille+ ")";
            progressText.setText(text);

            int progression = Math.round((float) nbrValidee/taille * 100);
            progressBar.setProgress(progression, true);
        }else{
            progressText.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public int cptTacheValidee(){
        int cpt = 0;
        for (Todo todo : liste) {
            if(todo.getFait()){
                cpt++;
            }
        }
        return cpt;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}