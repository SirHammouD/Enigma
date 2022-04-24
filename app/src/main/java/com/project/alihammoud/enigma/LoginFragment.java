package com.project.alihammoud.enigma;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginFragment extends Fragment implements View.OnClickListener{

    private static final String TAG = "LogIn";
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference users,reference;
    TextInputLayout edtPass, edtEmail;
    Button signUp, signIn;


    private DashboardFragment dashboardFragment;
    private AccountFragment accountFragment;
    private ExploreFragment exploreFragment;
    private LoginFragment loginFragment;
    private SignUpFragment signUpFragment;
    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        auth = FirebaseAuth.getInstance();

        dashboardFragment = new DashboardFragment();
        accountFragment = new AccountFragment();
        exploreFragment = new ExploreFragment();
        loginFragment = new LoginFragment();
        signUpFragment = new SignUpFragment();

        bottomNavigationView = getActivity().findViewById(R.id.nav_bar);
        bottomNavigationView.setVisibility(View.GONE);

        edtEmail = (TextInputLayout) view.findViewById(R.id.edtEmail);
        edtPass = (TextInputLayout) view.findViewById(R.id.edtPass);

        signUp = view.findViewById(R.id.signUp);
        signUp.setOnClickListener(this);

        signIn = view.findViewById(R.id.signIn);
        signIn.setOnClickListener(this);


        return view;
    }


    private void signIn(final String email, final String password) {
        Log.d(TAG, "signIn:" + email);

// [START sign_in_with_email]
        // [START sign_in_with_email]
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            auth = FirebaseAuth.getInstance();
                            user = auth.getCurrentUser();
                            database = FirebaseDatabase.getInstance();

                            FirebaseUser account = auth.getCurrentUser();
                            updateUI(account);


                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            Toast.makeText(getContext(), "Wrong E-mail or Password",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.signIn:
                String email = edtEmail.getEditText().getText().toString().trim();
                String pass = edtPass.getEditText().getText().toString().trim();
                signIn(email,pass);
                break;
            case R.id.signUp:
                SetFragment(signUpFragment,"login");
                break;

        }

    }

    private void SetFragment(Fragment fragment, String tag){

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, tag).addToBackStack(tag);
        fragmentTransaction.commit();

    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);

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