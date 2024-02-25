package fr.haronman.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;

public class Main_Activity extends AppCompatActivity {
    private ArrayList<Todo> liste;
    private RecyclerView courseRV;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        liste = new ArrayList<Todo>();
        courseRV = findViewById(R.id.idRVCourse);
        affiche();


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
                recyclerViewAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                Snackbar.make(courseRV, deletedCourse.getTitre(), Snackbar.LENGTH_LONG).setAction("Annuler", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        liste.add(position, deletedCourse);
                        recyclerViewAdapter.notifyItemInserted(position);
                    }
                }).show();
            }
        }).attachToRecyclerView(courseRV);
    }

    public void ajouter(View v) {
        courseRV = findViewById(R.id.idRVCourse);
        EditText e = findViewById(R.id.editTextText3);
        liste.add(new Todo(e.getText().toString()));
        affiche();
    }

    public void affiche() {
        recyclerViewAdapter = new RecyclerViewAdapter(liste, this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setLayoutManager(manager);
        courseRV.setAdapter(recyclerViewAdapter);
    }

//    public void consulter(View v, @NonNull RecyclerView.ViewHolder viewHolder) {
//        Todo deletedCourse = liste.get(viewHolder.getAdapterPosition());
//        int position = viewHolder.getAdapterPosition();
//        String text = String.valueOf(position);
//        int duration = Toast.LENGTH_SHORT;
//
//        Toast toast = Toast.makeText(this, text, duration);
//        toast.show();
//    }
//    public void consulter2(View v) {
////        Todo deletedCourse = liste.get(v.);
//        String text = String.valueOf(v.getId());
//        int duration = Toast.LENGTH_SHORT;
//
//        Toast toast = Toast.makeText(this, text, duration);
//        toast.show();
//        int value = v.getId();
//        Intent i = new Intent(getApplicationContext(), Main_Activity2.class);
//        i.putExtra("id",value);
//        startActivity(i);
//    }

    public void consulter(View v) {
//        Todo deletedCourse = liste.get(v.);
//        String text = String.valueOf(position);
        int duration = Toast.LENGTH_SHORT;

//        Toast toast = Toast.makeText(this, text, duration);
//        toast.show();
//        int value = v.getId();
//        Intent i = new Intent(getApplicationContext(), Main_Activity2.class);
//        i.putExtra("id",value);
//        startActivity(i);
    }
}
