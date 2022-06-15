package edu.skku.cs.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SecondActivity extends AppCompatActivity {

    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    Person info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        info = (Person) intent.getSerializableExtra("data");

        fragment1 = new Me();
        fragment2 = new Us();
        fragment3 = new mypage();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commitAllowingStateLoss();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.tab1:
                        if (fragment1 == null){
                            fragment1 = new Me();
                            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment1).commit();
                        }
                        if (fragment1 != null){
                            getSupportFragmentManager().beginTransaction().show(fragment1).commit();
                            getSupportFragmentManager().beginTransaction().replace( R.id.container, fragment1).commit();
                        }
                        if (fragment2!=null){
                            getSupportFragmentManager().beginTransaction().hide(fragment2).commit();
                        }
                        if (fragment3!=null){
                            getSupportFragmentManager().beginTransaction().hide(fragment3).commit();
                        }

                        return true;
                    case R.id.tab2:
                        if (fragment2 == null){
                            fragment2 = new Us();
                            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment2).commit();
                        }
                        if (fragment2 != null){
                            getSupportFragmentManager().beginTransaction().show(fragment2).commit();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment2).commit();
                        }
                        if (fragment1!=null){

                            getSupportFragmentManager().beginTransaction().hide(fragment1).commit();
                        }
                        if (fragment3!=null){
                            getSupportFragmentManager().beginTransaction().hide(fragment3).commit();
                        }


                        return true;
                    case R.id.tab3:
                        if (fragment3 == null){
                            fragment3 = new mypage();
                            getSupportFragmentManager().beginTransaction().add(R.id.container, fragment3).commit();
                        }
                        if (fragment3 != null){
                            getSupportFragmentManager().beginTransaction().show(fragment3).commit();
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment3).commit();
                        }
                        if (fragment2!=null){
                            getSupportFragmentManager().beginTransaction().hide(fragment2).commit();
                        }
                        if (fragment1!=null){
                            getSupportFragmentManager().beginTransaction().hide(fragment1).commit();
                        }

                        return true;
                }
                return false;
            }
        });


    }
}