package com.user.festivalbizerte;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.user.festivalbizerte.Adapter.ArtistesAdapter;
import com.user.festivalbizerte.Model.ArtistesItem;
import com.user.festivalbizerte.Utils.Constants;
import com.user.festivalbizerte.Utils.Helpers;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

import static com.user.festivalbizerte.Utils.Constants.MY_PERMISSIONS_REQUEST;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    Context context;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.news_rv)
    RecyclerView NewsRecyclerview;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.description)
    TextView Description;
    ActionBarDrawerToggle actionBarDrawerToggle;
    ArtistesAdapter newsAdapter;
    List<ArtistesItem> ListArtiste = new ArrayList<>();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/raleway_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        SupportMapFragment mapfrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        String Url = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/x8AfdXvBOtc\" frameborder=\"0\" allowfullscreen></iframe>";

        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkFineLocationPermission();
        }
        if (Helpers.isConnected(context)) {
            GetArtistes();
        } else {
            Helpers.ShowMessageConnection(context);
        }

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
        });
        webView.loadData(Url, "text/html", "utf-8");

        mapfrag.getMapAsync(this);

    }

    private void GetArtistes() {
        ListArtiste.add(new ArtistesItem("15 ", "Dimanche", "khale hizawi"));
        ListArtiste.add(new ArtistesItem("15 ", "Dimanche", "khale hizawi"));
        ListArtiste.add(new ArtistesItem("15 ", "Dimanche", "khale hizawi"));

        // adapter ini and setup
        newsAdapter = new ArtistesAdapter(context, ListArtiste);
        NewsRecyclerview.setAdapter(newsAdapter);
        NewsRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.programme:
                startActivity(new Intent(context, ProgrameActivity.class));
                break;
            case R.id.artiste:
                startActivity(new Intent(context, ArtistesActivity.class));
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkFineLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS,
                    Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(37.280428, 9.871297);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Festival Bizerte"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        float zoomLevel = 14.0f; //This goes up to 21
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
    }

    @OnClick(R.id.webview)
    public void GoSiteWeb() {

    }

    @OnClick(R.id.fb)
    public void GoFb() {

    }
}
