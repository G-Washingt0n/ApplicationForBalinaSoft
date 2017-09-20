package com.pgmail.martsulg.applicationforbalinasoft;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class EntryActivity extends FragmentActivity {
    static SharedPreferences preferences;

    public static final String SHARED_PREF_NAME = "filename";
    public static final String TOKEN_NAME = "Token";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        preferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);


        if(savedInstanceState==null) {
           /* try { //автологин
                if (!preferences.getString(TOKEN_NAME, null).isEmpty()) {
                    Intent intent = new Intent(EntryActivity.this, ContentActivity.class);
                    startActivity(intent);
                }
            } catch(Exception e) {
            }*/

                showFragment(getSupportFragmentManager(), new LogInFragment());
        }

       /* try { //автологин
            if (!preferences.getString(TOKEN_NAME, null).isEmpty()) {

                Intent intent = new Intent(EntryActivity.this, ContentActivity.class);
                startActivity(intent);

            }
        } catch(Exception e){
        }*/


            findViewById(R.id.LogInButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showFragment(getSupportFragmentManager(), new LogInFragment());
                }
            });


            findViewById(R.id.RegistryButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showFragment(getSupportFragmentManager(), new RegistryFragment());
                }
            });

    }

    public static void setPreferences(String key, String value ){
        preferences.edit()
                .putString(key, value)
                .apply();
    }

    public static void showFragment(FragmentManager fragmentManager, Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.entryContainer,fragment, fragment.getClass().getName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
