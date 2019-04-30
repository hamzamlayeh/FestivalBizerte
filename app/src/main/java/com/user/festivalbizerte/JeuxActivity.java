package com.user.festivalbizerte;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.user.festivalbizerte.Utils.Constants;
import com.user.festivalbizerte.Utils.Helpers;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class JeuxActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context context;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    View DialogBottomView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_questions);
        setContentView(R.layout.activity_jeux);
        ButterKnife.bind(this);
        context = this;
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/raleway_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
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
                startActivity(new Intent(context, SponsorActivity.class));
                break;
            case R.id.Quiz:
                startActivity(new Intent(context, JeuxActivity.class));
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

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.cardeview)
    public void Selfi() {
        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
        initPopupViewControls();
        mBottomSheetDialog.setContentView(DialogBottomView);
        mBottomSheetDialog.show();
    }

    private void initPopupViewControls() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        DialogBottomView = layoutInflater.inflate(R.layout.bottom_dialog, null);

        LinearLayout layout_take_pic = DialogBottomView.findViewById(R.id.layout_take_pic);
        LinearLayout layout_choose_pic = DialogBottomView.findViewById(R.id.layout_choose_pic);
    }

    @OnClick(R.id.cardeview2)
    public void GoQuiz() {
        startActivity(new Intent(this, StartQuiz.class));
    }

    @OnClick(R.id.imgInfo)
    public void DialogInfo() {
        //startActivity(new Intent(this,StartQuiz.class));
    }

    public void Camera(View view) {
        if (Helpers.isConnected(context))
            onTakePictureClicked();
        else
            Helpers.ShowMessageConnection(context);
    }

    public void Gallery(View view) {
        if (Helpers.isConnected(context))
            onChoosePictureClicked();
        else
            Helpers.ShowMessageConnection(context);
    }

    public void onTakePictureClicked() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.REQUEST_PERMISSION_CAMERA);
            }
        } else {
            Intent intent = new Intent(context, SelfiActivity.class);
            intent.putExtra("Methode", Constants.CAMERA);
            startActivity(intent);
        }
    }

    public void onChoosePictureClicked() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.REQUEST_PERMISSION_STORAGE);
            }
        } else {
            Intent intent = new Intent(context, SelfiActivity.class);
            intent.putExtra("Methode", Constants.GALLERY);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_PERMISSION_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.i("camera", "ok");
                Intent intent = new Intent(context, SelfiActivity.class);
                intent.putExtra("Methode", Constants.CAMERA);
                startActivity(intent);
            }
        } else if (requestCode == Constants.REQUEST_PERMISSION_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(context, SelfiActivity.class);
                intent.putExtra("Methode", Constants.GALLERY);
                startActivity(intent);
            }
        }
    }

}
