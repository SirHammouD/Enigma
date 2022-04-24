package com.project.alihammoud.enigma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AnswerFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference users,reference;
    private Query items;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    TextView  A1,A2,A3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_answer, container, false);


        A1 = view.findViewById(R.id.hint1);
        A2 = view.findViewById(R.id.hint2);
        A3 = view.findViewById(R.id.hint3);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Players");
        users.keepSynced(true);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        reference = database.getReference("Questions");
        reference.keepSynced(true);

        items = reference;

        A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Try Again!", Toast.LENGTH_SHORT).show();
            }
        });

        A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Noice!", Toast.LENGTH_LONG).show();
                users.child(user.getUid()).child("type").setValue("waiting");
                ExploreFragment exploreFragment = new ExploreFragment();
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, exploreFragment).commit();
            }
        });

        A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Try Again!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();




        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String h1 = snapshot.child("Q1").child("A1").getValue().toString();
                String h2 = snapshot.child("Q1").child("A2").getValue().toString();
                String h3 = snapshot.child("Q1").child("A3").getValue().toString();

                A1.setText(h1);
                A2.setText(h2);
                A3.setText(h3);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}