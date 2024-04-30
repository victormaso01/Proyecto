package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.proyecto.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
public class Menu extends AppCompatActivity {
    private BottomNavigationView bottomNav;

    private User user;
    private ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        bottomNav = findViewById(R.id.bottomNavigationView);

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        Intent intent = getIntent();
        if (intent != null){
            user = (User) intent.getSerializableExtra("usuario");
            if (user != null ){
                // Do something with the user object if needed
            }
        }



    }



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    if (item.getItemId() == R.id.miSearch) {
                        selectedFragment = new MarketFragment();
                    } else if (item.getItemId() == R.id.miHeart) {
                        selectedFragment = new FavouritesFragment();
                    } else if (item.getItemId() == R.id.miChat) {
                        selectedFragment = new ChatFragment();
                    } else if (item.getItemId() == R.id.miProfile) {
                        selectedFragment = new ProfileFragment();
                    } else if (item.getItemId() == R.id.miHome){
                        selectedFragment = new MenuFragment();
                    }

                    // Pasa el objeto usuario al fragmento seleccionado
                    if (user != null && selectedFragment != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("usuario", user);
                        selectedFragment.setArguments(bundle);
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,
                            selectedFragment).commit();

                    return true;
                }

            };

}


