package com.example.lostfoundpets;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    List<Map<String,Object>> posts;
    Context context;

    public MyAdapter(Context c, ArrayList<Map<String,Object>> p){
        context = c;
        posts = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.post, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Map<String,Object> post = posts.get(position);
        holder.location.setText((String)post.get("location"));
        holder.description.setText((String)post.get("details"));

        holder.image.setImageResource(R.mipmap.ic_test_pet_image_foreground); //TODO get from Firebase
        if(post.containsKey("status") && post.get("status").equals("SOLVED")){
            holder.image.setBackgroundColor(Color.argb(37,47,255,0));
        }
        else {
            holder.image.setBackgroundColor(Color.argb(37,255,235,59));
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView location, description;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            image = itemView.findViewById(R.id.pet_image);
            location = itemView.findViewById(R.id.location_content);
            description = itemView.findViewById(R.id.description_content);
        }
    }
}
