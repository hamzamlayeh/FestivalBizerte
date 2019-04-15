package com.user.festivalbizerte;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.user.festivalbizerte.Adapter.ProgrameAdapter;
import com.user.festivalbizerte.Model.ProgrameItem;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ProgrameActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    RecyclerView NewsRecyclerview;
    ProgrameAdapter newsAdapter;
    List<ProgrameItem> mData;
    ConstraintLayout rootLayout;
    EditText searchInput ;
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
        setContentView(R.layout.activity_programe);
        // ini view
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


        rootLayout = findViewById(R.id.root_layout);
        searchInput = findViewById(R.id.search_input);
        NewsRecyclerview = findViewById(R.id.news_rv);
        mData = new ArrayList<>();

        // load theme state



        // fill list news with data
        // just for testing purpose i will fill the news list with random data
        // you may get your data from an api / firebase or sqlite database ...
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));
        mData.add(new ProgrameItem("spectacle ","15D","15","Avrile","23h","la soirée internationale de la magie"));


        // adapter ini and setup

        newsAdapter = new ProgrameAdapter(this,mData);
        NewsRecyclerview.setAdapter(newsAdapter);
        NewsRecyclerview.setLayoutManager(new LinearLayoutManager(this));



//        ***init font***
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/raleway_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                newsAdapter.getFilter().filter(s);
                search = s;


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



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
            case R.id.menu1:

                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

                break;
            case R.id.menu2:
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();


                break;
            case R.id.menu3:
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

                break;
            case R.id.menu4:
                Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();

                break;
        }
        return false;
    }


}
