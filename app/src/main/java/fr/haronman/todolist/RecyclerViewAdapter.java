package fr.haronman.todolist;

import fr.haronman.todolist.model.Todo;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    // creating a variable for our array list and context.
    private ArrayList<Todo> courseDataArrayList;
    private MainActivity ma;
//    private int id;

    // creating a constructor class.
    public RecyclerViewAdapter(ArrayList<Todo> recyclerDataArrayList, MainActivity ma) {
        this.courseDataArrayList = recyclerDataArrayList;
        this.ma = ma;
//        this.id = id;
    }

    public ArrayList<Todo> getCourseDataArrayList() {
        return courseDataArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
//        view.setId();
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview from our modal class.
        Todo recyclerData = courseDataArrayList.get(position);
        holder.courseNameTV.setText(recyclerData.getTitre());
        holder.courseDescTV.setText(recyclerData.getDescription());
        int visibility = recyclerData.getFait() ? View.VISIBLE : View.GONE;
        holder.courseImgTV.setVisibility(visibility);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ma, MainActivity2.class);
                i.putExtra("id",recyclerData.getId());
                ma.startActivity(i);
            }
        });
//        holder.courseNameTV.setId(recyclerData.getId());
//        holder.courseDescTV.setText(recyclerData.getDescription());
    }

    @Override
    public int getItemCount() {
        // this method returns
        // the size of recyclerview
        return courseDataArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        // creating a variable for our text view.
        private TextView courseNameTV;
        private TextView courseDescTV;
        private ImageView courseImgTV;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
            courseDescTV = itemView.findViewById(R.id.idTVCourseDesc);
            courseImgTV = itemView.findViewById(R.id.idTVCourseImg);
        }
    }
}
