package com.nexuswawe.wownews;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.MyViewHolder> {


    private Context context;

    private ArrayList titledb, image_urldb, post_urldb, datedb;
    private List<Post> posts;


    SavedAdapter(Context context,
                 ArrayList titledb,
                 ArrayList image_urldb,
                 ArrayList post_urldb,
                 ArrayList datedb) {
        this.context = context;
        this.titledb = titledb;
        this.image_urldb = image_urldb;
        this.post_urldb = post_urldb;
        this.datedb = datedb;

        posts = new ArrayList<>();
        for (int i = 0; i < titledb.size(); i++) {
            Post post = new Post(
                    String.valueOf(titledb.get(i)),
                    String.valueOf(image_urldb.get(i)),
                    String.valueOf(post_urldb.get(i)),
                    String.valueOf(datedb.get(i))
            );
            posts.add(post);
        }

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rc_saved_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titledb.setText(String.valueOf(titledb.get(position)));

        Glide.with(holder.itemView.getContext())
                .load(image_urldb.get(position))
                .into(holder.image_urldb);

        // holder.image_urldb.setText(String.valueOf(titledb.get(position)));
        // holder.post_urldb.setText(String.valueOf(titledb.get(position)));
        holder.datedb.setText(String.valueOf(datedb.get(position)));
        holder.post_urldb = String.valueOf(post_urldb.get(position));


    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        int id;
        TextView titledb, datedb;
        String post_urldb;
        ImageView image_urldb;
        ImageButton deletebtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titledb = itemView.findViewById(R.id.titledb);
            image_urldb = itemView.findViewById(R.id.image_urldb);
            //post_urldb= itemView.findViewById(R.id.title);
            datedb = itemView.findViewById(R.id.datedb);
            deletebtn = itemView.findViewById(R.id.deletebtn);


            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHandler db = new DBHandler(context);
                    // Delete the row based on the adapter position
                    db.delete(posts.get(getAdapterPosition()).getTitle(),
                            posts.get(getAdapterPosition()).getDate(),
                            posts.get(getAdapterPosition()).getPostUrl(),
                            posts.get(getAdapterPosition()).getImageUrl());
                    // Remove the item from the list
                    posts.remove(getAdapterPosition());
                    // Notify the adapter of the item removal
                    notifyItemRemoved(getAdapterPosition());
                    // Notify the adapter of the data set change
                    notifyItemRangeChanged(getAdapterPosition(), posts.size());


                }
            });

            image_urldb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(post_urldb));
                    v.getContext().startActivity(intent);
                }
            });
            titledb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(post_urldb));
                    v.getContext().startActivity(intent);
                }
            });

        }
    }


}
