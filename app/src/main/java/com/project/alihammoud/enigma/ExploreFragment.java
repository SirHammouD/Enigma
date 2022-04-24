package com.project.alihammoud.enigma;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ExploreFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference users,reference;
    private Query items;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    SwitchMaterial switchMaterial;
    Button play;
    String type;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Players");
        users.keepSynced(true);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        reference = database.getReference("Users");
        reference.keepSynced(true);

        items = users;

        play= view.findViewById(R.id.button);
        switchMaterial = view.findViewById(R.id.switch1);

        users.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    type = snapshot.child("type").getValue().toString();
                    if (type.equals("ready")) {
                        play.setBackgroundColor(play.getContext().getResources().getColor(R.color.green));
                        //Toast.makeText(getContext(),"WORKS?", Toast.LENGTH_SHORT).show();

                    }
                    if (type.equals("waiting")){
                        switchMaterial.setChecked(true);
                        play.setBackgroundColor(play.getContext().getResources().getColor(R.color.main));
                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type.equals("ready")){
                    AnswerFragment answerFragment = new AnswerFragment();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, answerFragment).commit();

                }
            }
        });

        switchMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchMaterial.isChecked()){
                    reference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String name = snapshot.child("name").getValue().toString();
                            final Player info = new Player(name, user.getUid(),"waiting" ,snapshot.child("img").getValue().toString());

                            users.child(user.getUid()).setValue(info);


                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else {
                    users.child(user.getUid()).removeValue();
                }

            }
        });




        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);


        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<User, ExploreFragment.DataViewHolder> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<User, ExploreFragment.DataViewHolder>(User.class,R.layout.cards_player, ExploreFragment.DataViewHolder.class, items) {
                    @Override
                    protected void populateViewHolder(ExploreFragment.DataViewHolder viewHolder, User model, int position) {
                        final String post_key = getRef(position).getKey();


                        viewHolder.setName(model.getName());
                        viewHolder.setImage(model.getImg());
                        /*  if (post_key.equals(user.getUid())){
                                viewHolder.mView.setVisibility(View.GONE);
                           }*/

                        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                QuestionFragment questionFragment = new QuestionFragment();
                                users.child(user.getUid()).child("type").setValue("create");
                                users.child(post_key).child("type").setValue("ready");
                                Bundle bundle = new Bundle();
                                bundle.putString("NAME",model.getName());
                                bundle.putString("IMG",model.getImg());
                                questionFragment.setArguments(bundle);
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, questionFragment).addToBackStack("question").commit();
                            }
                        });




                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class DataViewHolder extends RecyclerView.ViewHolder{

        View mView;
        CardView cardView;

        public DataViewHolder(View itemView){

            super(itemView);
            mView = itemView;
            cardView= mView.findViewById(R.id.card);

        }
        public void setName(String name){
            TextView Name = (TextView)mView.findViewById(R.id.name);
            Name.setText((name));
        }

        public void setImage(String img){
            ImageView Image = (ImageView)mView.findViewById(R.id.image);
            Picasso.get().load(img).into(Image);
        }

    }
}