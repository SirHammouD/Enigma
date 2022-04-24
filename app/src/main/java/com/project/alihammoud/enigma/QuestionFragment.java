package com.project.alihammoud.enigma;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class QuestionFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference users,reference;
    private Query items;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    TextView questionE, H1,H2,H3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_question, container, false);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        users.keepSynced(true);

        auth = FirebaseAuth.getInstance();

        reference = database.getReference("Questions");
        reference.keepSynced(true);

        items = reference;


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        questionE = getActivity().findViewById(R.id.question);
        H1 = getActivity().findViewById(R.id.hint1);
        H2 = getActivity().findViewById(R.id.hint2);
        H3 = getActivity().findViewById(R.id.hint3);



                reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String question = snapshot.child("Q1").child("question").getValue().toString();
                String h1 = snapshot.child("Q1").child("h1").getValue().toString();
                String h2 = snapshot.child("Q1").child("h2").getValue().toString();
                String h3 = snapshot.child("Q1").child("h3").getValue().toString();

                questionE.setText(question);
                H1.setText(h1);
                H2.setText(h2);
                H3.setText(h3);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}