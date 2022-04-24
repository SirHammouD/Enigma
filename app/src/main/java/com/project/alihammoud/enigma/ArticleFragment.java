package com.project.alihammoud.enigma;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class ArticleFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    FirebaseDatabase database;
    TextView setTitle, setDesc;
    ImageView setImg;

    public String title, desc, img;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        title = getArguments().getString("TITLE");
        desc = getArguments().getString("DESC");
        img = getArguments().getString("IMG");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        setTitle = (TextView) view.findViewById(R.id.title);
        setDesc = (TextView) view.findViewById(R.id.desc);
        setImg = (ImageView) view.findViewById(R.id.img);

        setTitle.setText(title);
        setDesc.setText(desc);
        Picasso.get().load(img).into(setImg);


        return view;
    }
}