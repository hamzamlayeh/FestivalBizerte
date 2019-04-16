package com.user.festivalbizerte;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.user.festivalbizerte.Adapter.ArtisteAdapter;
import com.user.festivalbizerte.Model.ArtistesItem;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ArtistesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView NewsRecyclerview;
    ArtisteAdapter newsAdapter;
    List<ArtistesItem> mData;
    CharSequence search="";
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_artistes);

        context=this;
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) context);


        NewsRecyclerview = findViewById(R.id.news_rv);
        mData = new ArrayList<>();

        // load theme state



        // fill list news with data
        // just for testing purpose i will fill the news list with random data
        // you may get your data from an api / firebase or sqlite database ...
        mData.add(new ArtistesItem("15 jun 2018","khaled hizawi","20dt","51dt"));
        mData.add(new ArtistesItem("15 jun 2018","khaled hizawi","20dt","51dt"));
        mData.add(new ArtistesItem("15 jun 2018","khaled hizawi","20dt","51dt"));
        mData.add(new ArtistesItem("15 jun 2018","khaled hizawi","20dt","51dt"));   mData.add(new ArtistesItem("15 jun 2018","khaled hizawi","20dt","51dt"));
        mData.add(new ArtistesItem("15 jun 2018","khaled hizawi","20dt","51dt"));
        mData.add(new ArtistesItem("15 jun 2018","khaled hizawi","20dt","51dt"));
        mData.add(new ArtistesItem("15 jun 2018","khaled hizawi","20dt","51dt"));   mData.add(new ArtistesItem("15 jun 2018","khaled hizawi","20dt","51dt"));
        mData.add(new ArtistesItem("15 jun 2018","khaled hizawi","20dt","51dt"));
        mData.add(new ArtistesItem("15 jun 2018","khaled hizawi","20dt","51dt"));
        mData.add(new ArtistesItem("15 jun 2018","khaled hizawi","20dt","51dt"));


        // adapter ini and setup

        newsAdapter = new ArtisteAdapter(this,mData);
        NewsRecyclerview.setAdapter(newsAdapter);
        NewsRecyclerview.setLayoutManager(new GridLayoutManager(this,2));



//        ***init font***
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/raleway_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());




    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);

    }


    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.programme:

                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

                break;
            case R.id.service:
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();


                break;
            case R.id.Sponsor:
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

                break;
            case R.id.Quiz:
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

                break;
        }
        return false;
    }


}
