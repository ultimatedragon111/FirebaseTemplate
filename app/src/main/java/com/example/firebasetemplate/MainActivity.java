package com.example.firebasetemplate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.example.firebasetemplate.databinding.ActivityMainBinding;
import com.example.firebasetemplate.databinding.NavHeaderMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavHeaderMainBinding navHeaderMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());
        navHeaderMainBinding = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0));
        FirebaseFirestore.getInstance().setFirestoreSettings(new FirebaseFirestoreSettings.Builder().setPersistenceEnabled(false).build());

        setSupportActionBar(binding.toolbar);

        navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.postsHomeFragment, R.id.postsLikeFragment, R.id.postsMyFragment)
                .setOpenableLayout(binding.drawerLayout)
                .build();

        NavigationUI.setupWithNavController(binding.bottomNavView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(destination.getId() == R.id.signInFragment) {
                binding.toolbar.setVisibility(View.GONE);
                binding.bottomNavView.setVisibility(View.GONE);
            } else {
                binding.toolbar.setVisibility(View.VISIBLE);
                binding.bottomNavView.setVisibility(View.VISIBLE);
            }
        });

        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            if (firebaseAuth.getCurrentUser() != null) {
                Glide.with(this).load(firebaseAuth.getCurrentUser().getPhotoUrl()).circleCrop().into(navHeaderMainBinding.photo);
                navHeaderMainBinding.name.setText(firebaseAuth.getCurrentUser().getDisplayName());
                navHeaderMainBinding.email.setText(firebaseAuth.getCurrentUser().getEmail());
                Log.e("sdfdfs","USER:" + firebaseAuth.getCurrentUser().getEmail());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}