package com.project.alihammoud.enigma;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.BottomNavigationViewKt;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    private FrameLayout mMainFrame;
    private BottomNavigationView bottomNavigationView;
    private DashboardFragment dashboardFragment;
    private AccountFragment accountFragment;
    private ExploreFragment exploreFragment;
    private LoginFragment loginFragment;
    private SignUpFragment signUpFragment;
    boolean logged = false;

    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference users,reference;
    private Query items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Players");
        users.keepSynced(true);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        reference = database.getReference("Users");
        reference.keepSynced(true);

        items = users;

        bottomNavigationView = findViewById(R.id.nav_bar);
        bottomNavigationView.setOnItemSelectedListener(this);

        mMainFrame = (FrameLayout) findViewById(R.id.frame);

        dashboardFragment = new DashboardFragment();
        accountFragment = new AccountFragment();
        exploreFragment = new ExploreFragment();
        loginFragment = new LoginFragment();
        signUpFragment = new SignUpFragment();

        if(!logged){

            SetFragment(loginFragment,"login");
        }
        else {
            SetFragment(dashboardFragment,"dashboard");
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.account:
                SetFragment(accountFragment,"account");
                return true;
            case R.id.explore:
                SetFragment(exploreFragment,"explore");
                return true;
            case R.id.dashboard:
                SetFragment(dashboardFragment,"dashboard");
                return true;
        }
        return false;
    }

    private void SetFragment(Fragment fragment, String tag){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, tag);
        fragmentTransaction.commit();

    }
}