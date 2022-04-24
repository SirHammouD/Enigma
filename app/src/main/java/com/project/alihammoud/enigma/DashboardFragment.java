package com.project.alihammoud.enigma;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;


public class DashboardFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference users,reference;
    private Query items;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        users.keepSynced(true);

        auth = FirebaseAuth.getInstance();

        reference = database.getReference("Info");
        reference.keepSynced(true);

        items = reference;

        bottomNavigationView = getActivity().findViewById(R.id.nav_bar);
        bottomNavigationView.setVisibility(View.VISIBLE);

        layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Data, DataViewHolder> firebaseRecyclerAdapter = new
                FirebaseRecyclerAdapter<Data, DataViewHolder>(Data.class,R.layout.cards_dashboard,DataViewHolder.class, items) {

            @Override
            protected void populateViewHolder(DataViewHolder viewHolder, Data model, int position) {
                final String post_key = getRef(position).getKey();


                viewHolder.setTitle(model.getTitle());
                viewHolder.setImage(model.getImg());
                viewHolder.setDesc(model.getDesc());

                viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArticleFragment articleFragment = new ArticleFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("TITLE",model.getTitle());
                        bundle.putString("IMG",model.getImg());
                        bundle.putString("DESC",model.getDesc());
                        articleFragment.setArguments(bundle);
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame, articleFragment).addToBackStack("article").commit();
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
        public  void setTitle(String title){
            TextView Title = (TextView)mView.findViewById(R.id.title);
            Title.setText((title));
        }
        public  void setDesc(String desc){
           // TextView Desc =(TextView)mView.findViewById(R.id.desc);
            //Desc.setText((desc));
        }
       public void setImage(String img){
            ImageView Image = (ImageView)mView.findViewById(R.id.image);
            Picasso.get().load(img).into(Image);
        }

    }
}