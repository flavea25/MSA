package com.example.lostfoundpets;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lostfoundpets.ui.details.DetailsFragment;

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
        holder.phoneNumber = (String)post.get("phoneNumber");
        holder.color = (String)post.get("color");
        holder.pet = (String)post.get("pet");
        holder.sex = (String)post.get("sex");
        //TODO get image from Firebase Storage
        holder.image.setImageResource(R.mipmap.ic_test_pet_image_foreground);
        if(post.containsKey("status") && post.get("status").equals("SOLVED")){
            holder.image.setBackgroundColor(Color.argb(37,47,255,0));
        }
        else {
            holder.image.setBackgroundColor(Color.argb(37,255,235,59));
        }

        holder.itemView.setOnClickListener(v ->{
            ((FragmentActivity) v.getContext())
                .getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.home_fragment, new DetailsFragment(holder.location.getText().toString(), holder.description.getText().toString(), holder.color, holder.pet, holder.sex, holder.phoneNumber),null)
                .addToBackStack(null)
                .commit();
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView location, description;
        String color, pet, sex, phoneNumber;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.pet_image);
            location = itemView.findViewById(R.id.location_content);
            description = itemView.findViewById(R.id.description_content);
            color = ((TextView)itemView.findViewById(R.id.post_color)).getText().toString();
            pet = ((TextView)itemView.findViewById(R.id.post_pet)).getText().toString();
            sex = ((TextView)itemView.findViewById(R.id.post_sex)).getText().toString();
            phoneNumber = ((TextView)itemView.findViewById(R.id.post_phone)).getText().toString();
        }
    }
}
