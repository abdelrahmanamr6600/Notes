package com.example.notes.MyAdapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.Model.NotesModel;
import com.example.notes.OnRecyclerViewItemClickListener;
import com.example.notes.databinding.NotesRecyclerviewBinding;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyHolder> {
    List<NotesModel> notesList;
    private OnRecyclerViewItemClickListener listener ;


    public NotesAdapter(List notesList,OnRecyclerViewItemClickListener listener) {
        this.notesList = notesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotesRecyclerviewBinding binding = NotesRecyclerviewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        NotesModel note = notesList.get(position);
        holder.binding.recyclerViewTitie.setText(note.getTitle());
        holder.binding.recyclerViewTime.setText(note.getTimestamp());
        holder.binding.CardView.setTag(position);

    }

    @Override
    public int getItemCount() {

        return notesList.size();
    }


class MyHolder extends RecyclerView.ViewHolder{
     NotesRecyclerviewBinding binding;
    public MyHolder(@NonNull NotesRecyclerviewBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) binding.CardView.getTag();
                listener.OnItemClick(id);
            }
        });
        binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int id = (int) binding.CardView.getTag();
                listener.onLongclickIteam(id);
                return false;
            }
        });
    }

}


}
