package com.project.alihammoud.enigma;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class AccountFragment extends Fragment implements View.OnClickListener{

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    FirebaseDatabase database;
    TextView setEmail, setName;
    LoginFragment loginFragment;
    VideoView videoView;
    Button rick;
    ImageView imageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        imageView = view.findViewById(R.id.profile_pic);

        videoView = view.findViewById(R.id.rickroll);
        videoView.setVisibility(View.GONE);
        rick = (Button) view.findViewById(R.id.rick);
        loginFragment = new LoginFragment();

        setName = (TextView) view.findViewById(R.id.setName);
        setEmail = (TextView) view.findViewById(R.id.setEmail);
        setEmail.setText(user.getEmail());

        view.findViewById(R.id.logout).setOnClickListener(this);
        view.findViewById(R.id.rick).setOnClickListener(this);
        reference = database.getReference("Users").child(user.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstname = snapshot.child("name").getValue().toString();
                String img = snapshot.child("img").getValue().toString();

                Picasso.get().load(img).into(imageView);
                setName.setText(firstname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.logout:
                auth.signOut();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, loginFragment, "login");
                fragmentTransaction.commit();
                break;
            case R.id.rick:
                rick.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                String uriPath = "android.resource://" + getContext().getPackageName() + "/raw/rickroll";
                Uri uri = Uri.parse(uriPath);
                videoView.setVideoURI(uri);
                videoView.start();



        }
    }
}