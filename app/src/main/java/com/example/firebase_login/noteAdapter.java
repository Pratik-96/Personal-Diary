package com.example.firebase_login;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.w3c.dom.Text;

public class noteAdapter extends FirestoreRecyclerAdapter<noteModel, noteAdapter.viewHolder> {
Context context;

    public noteAdapter(@NonNull FirestoreRecyclerOptions<noteModel> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull noteModel model) {
        holder.title.setText(model.TITLE);
        holder.content.setText(model.CONTEXT);
        getItemCount();


      holder.timestamp.setText((model.timestamp));
        holder.itemView.setOnClickListener((view ->
        {
            Intent intent = new Intent(context,Note.class);
            intent.putExtra("title",model.TITLE);
            intent.putExtra("content",model.CONTEXT);
            String docId = this.getSnapshots().getSnapshot(position).getId();
            intent.putExtra("docId",docId);

            context.startActivity(intent);

        }));
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list,parent,false);
        return new viewHolder(view);        //Create Object of View And Return
    }

    class viewHolder extends RecyclerView.ViewHolder{

    TextView title,content,timestamp;

    public viewHolder(@NonNull View itemView) {
        super(itemView);
        title=itemView.findViewById(R.id.title);
        content=itemView.findViewById(R.id.content);
  timestamp=itemView.findViewById(R.id.timestamp);

    }





}

}
