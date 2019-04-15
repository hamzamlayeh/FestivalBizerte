package com.user.festivalbizerte;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.user.festivalbizerte.Adapter.ServiceAdapter;
import com.user.festivalbizerte.Helper.RecyclerViewClickListener;
import com.user.festivalbizerte.Helper.RecyclerViewTouchListener;
import com.user.festivalbizerte.Model.ServiceItem;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ServiceActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    RecyclerView NewsRecyclerview;
    ServiceAdapter newsAdapter;
    List<ServiceItem> mData;
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
        setContentView(R.layout.activity_service);
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


//        mData.add(new ServiceItem("l'itinéraire Du Festival",R.drawable.itineraire));
        mData.add(new ServiceItem("Hôtel",R.drawable.hotel));
        mData.add(new ServiceItem("Restaurant",R.drawable.restaurant));
        mData.add(new ServiceItem("Parking",R.drawable.parkinp));
        mData.add(new ServiceItem("Tourisme",R.drawable.tourisme));
        mData.add(new ServiceItem("Cafés",R.drawable.cafes));
        mData.add(new ServiceItem("Hôpital et Clinique",R.drawable.clinique));
        mData.add(new ServiceItem("Pharmacie",R.drawable.pharmacie));
        mData.add(new ServiceItem("Médecin",R.drawable.medecin));
        mData.add(new ServiceItem("Distributeurs",R.drawable.distributeurs));
        mData.add(new ServiceItem("Carburant",R.drawable.carburant));
        mData.add(new ServiceItem("Police",R.drawable.polic));


        // adapter ini and setup

        newsAdapter = new ServiceAdapter(this,mData);
        NewsRecyclerview.setAdapter(newsAdapter);
        NewsRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        NewsRecyclerview.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), NewsRecyclerview, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                String Service =mData.get(position).getNomService();
                Uri gmmIntentUri = Uri.parse("geo:37.280310, 9.871321?q="+Service+"");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));

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


    public void itineraire(View view) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=Amphithéâtre de Bizerte");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
