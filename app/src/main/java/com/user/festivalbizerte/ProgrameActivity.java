package com.user.festivalbizerte;

import android.content.Context;
import android.content.Intent;
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

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ProgrameActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.news_rv)
    RecyclerView NewsRecyclerview;
    @BindView(R.id.search_input)
    EditText searchInput ;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ProgrameAdapter newsAdapter;
    List<ProgrameItem> mData= new ArrayList<>();
    CharSequence search="";
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_programe);
        ButterKnife.bind(this);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/raleway_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        context=this;
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) context);

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
            case R.id.programme:
                startActivity(new Intent(context, ProgrameActivity.class));
                break;
            case R.id.service:
                startActivity(new Intent(context, ServiceActivity.class));
                break;
            case R.id.Sponsor:
                startActivity(new Intent(context, ServiceActivity.class));
                break;
            case R.id.Quiz:
                startActivity(new Intent(context, ServiceActivity.class));
                break;
            case R.id.addamis:
                startActivity(new Intent(context, InviteAmisActivity.class));
                break;
            case R.id.info:
                startActivity(new Intent(context, ServiceActivity.class));
                break;
            case R.id.Profile:
                startActivity(new Intent(context, ProfileActivity.class));
                break;
            case R.id.Deconnexion:
                startActivity(new Intent(context, LoginActivity.class));
                finishAffinity();
                break;
        }
        return false;
    }


}
