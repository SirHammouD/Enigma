package com.project.alihammoud.enigma;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;


public class SignUpFragment extends Fragment implements View.OnClickListener {

    FirebaseAuth auth;
    FirebaseDatabase database;
    TextInputLayout edtFirstname, edtPass, edtConfirmPass, edtEmail;
    DatabaseReference users;
    BottomNavigationView bottomNavigationView;

    private DashboardFragment dashboardFragment;
    private AccountFragment accountFragment;
    private ExploreFragment exploreFragment;
    private LoginFragment loginFragment;
    private SignUpFragment signUpFragment;

    Button signUp, signIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        auth = FirebaseAuth.getInstance();

        dashboardFragment = new DashboardFragment();
        accountFragment = new AccountFragment();
        exploreFragment = new ExploreFragment();
        loginFragment = new LoginFragment();
        signUpFragment = new SignUpFragment();

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        edtFirstname = (TextInputLayout) view.findViewById(R.id.edtName);
        edtEmail = (TextInputLayout) view.findViewById(R.id.edtEmail);
        edtPass = (TextInputLayout) view.findViewById(R.id.edtPass);
        edtConfirmPass = (TextInputLayout) view.findViewById(R.id.edtConfirmPass);

        bottomNavigationView = getActivity().findViewById(R.id.nav_bar);
        bottomNavigationView.setVisibility(View.GONE);

//Buttons
        signUp = view.findViewById(R.id.signUp);
        signUp.setOnClickListener(this);

        signIn = view.findViewById(R.id.signIn);
        signIn.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.signIn:
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, loginFragment, "login");
                fragmentTransaction.commit();
                break;
            case R.id.signUp:
                String email = edtEmail.getEditText().getText().toString().trim();
                String pass = edtPass.getEditText().getText().toString().trim();
                String name = edtFirstname.getEditText().getText().toString().trim();
                createAccount(email,pass,name);
                break;

        }

    }
    private void createAccount(String email, String password, String name) {
        Log.d(TAG, "createAccount:" + email);


        // [START create_user_with_email]
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            final FirebaseUser account = auth.getCurrentUser();

                            final User info = new User(name, password, email );

                            // users.child(info.getUsername()).setValue(info);
                            users.child(account.getUid()).setValue(info);

                            account.sendEmailVerification().addOnCompleteListener( new OnCompleteListener() {
                                public void onComplete(@NonNull Task task) {

                                }
                            });

                            updateUI(account);
                        } else {

                            // If sign in fails, display a message to the user.
                             Toast.makeText(getContext(),"Email Already in Use",Toast.LENGTH_SHORT).show();
                             updateUI(null);
                        }

                    }
                });
        // [END create_user_with_email]
    }

    private void updateUI(FirebaseUser account) {

        if (account != null) {
            //DO THE SIGNIN METHODBY GETTING USERNAME AND PASSWORD FROM THE USER CLASS
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, dashboardFragment, "LOGGED");
            fragmentTransaction.commit();

        }
    }

}