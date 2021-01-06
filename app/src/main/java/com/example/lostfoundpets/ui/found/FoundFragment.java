package com.example.lostfoundpets.ui.found;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lostfoundpets.MyAdapter;
import com.example.lostfoundpets.R;
import com.example.lostfoundpets.ui.addpost.AddPostFragment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FoundFragment extends Fragment {
    private final CollectionReference posts = FirebaseFirestore.getInstance().collection("posts");
    private final List<Map<String,Object>> toShowPosts = new ArrayList<>();
    private MyAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_found, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.posts_found);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        adapter = new MyAdapter(this.getActivity(), (ArrayList<Map<String, Object>>) toShowPosts);
        recyclerView.setAdapter(adapter);

        posts.whereEqualTo("category", "FOUND")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if(task.getResult().isEmpty()){
                            TextView noPosts = root.findViewById(R.id.no_posts);
                            noPosts.setVisibility(View.VISIBLE);
                            LinearLayout filterBar = root.findViewById(R.id.filter_bar);
                            filterBar.setVisibility(View.GONE);
                        }
                        else {
                            for (QueryDocumentSnapshot post : task.getResult()) {
                                toShowPosts.add(post.getData());
                            }
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        TextView errorRetrieving = root.findViewById(R.id.error_retrieving);
                        errorRetrieving.setVisibility(View.VISIBLE);
                        LinearLayout filterBar = root.findViewById(R.id.filter_bar);
                        filterBar.setVisibility(View.GONE);
                    }
                });

        ImageButton addButton = root.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.home_fragment, AddPostFragment.class,null)
                .addToBackStack(null)
                .commit());

        return root;
    }
}