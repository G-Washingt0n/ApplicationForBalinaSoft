package com.pgmail.martsulg.applicationforbalinasoft;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class EntryActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);


        if(savedInstanceState==null){
            showFragment(getSupportFragmentManager(), new LogInFragment());
        }

        findViewById(R.id.LogInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(getSupportFragmentManager(),new LogInFragment());
            }
        });

        findViewById(R.id.RegistryButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(getSupportFragmentManager(), new RegistryFragment());
            }
        });

    }

    public static void showFragment(FragmentManager fragmentManager, Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.entryContainer,fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
