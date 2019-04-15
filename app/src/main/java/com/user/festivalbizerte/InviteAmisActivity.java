package com.user.festivalbizerte;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.user.festivalbizerte.Adapter.ContactTelAdapter;
import com.user.festivalbizerte.Helper.RecyclerViewClickListener;
import com.user.festivalbizerte.Helper.RecyclerViewTouchListener;
import com.user.festivalbizerte.Model.ContactTelItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class InviteAmisActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    final static int MY_PERMISSIONS_REQUEST = 2;
    RecyclerView NewsRecyclerview;
    ContactTelAdapter newsAdapter;
    List<ContactTelItem> mData;
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

        setContentView(R.layout.activity_invite_amis);
        context=this;

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        ***init font***
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/raleway_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) context);


        rootLayout = findViewById(R.id.root_layout);
        searchInput = findViewById(R.id.search_input);
        NewsRecyclerview = findViewById(R.id.news_rv);
        mData = new ArrayList<>();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSMSPermission();
        }

        recupContact();
        // adapter ini and setup

        newsAdapter = new ContactTelAdapter(this,mData);
        NewsRecyclerview.setAdapter(newsAdapter);
        NewsRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        NewsRecyclerview.addOnItemTouchListener(new RecyclerViewTouchListener(getApplicationContext(), NewsRecyclerview, new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                String nom =mData.get(position).getName();
                String num =mData.get(position).getDesc();
                String msg = "Lien de l aplication";
                try {
                    SmsManager sms = SmsManager.getDefault();
//                    sms.sendTextMessage(num, null, msg, null, null);
                }catch (Exception e){
                    Toast.makeText(InviteAmisActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(InviteAmisActivity.this, "Invitation  envoiye par SMS a :"+nom+" \nNuméro :"+ num, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onLongClick(View view, int position) {

            }
        }));



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

    public void recupContact() {
        ContentResolver contentProvider = this.getContentResolver();

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE,
                        ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
        if (cursor == null) {
            Toast.makeText(getApplicationContext(), "Erreur", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_ALTERNATIVE));
                String num = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                mData.add(new ContactTelItem(name, num));
                Collections.sort(mData, new Comparator<ContactTelItem>() {
                    @Override
                    public int compare(ContactTelItem o1, ContactTelItem o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
            }
            cursor.close();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS,
                            Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkSMSPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.SEND_SMS,
                    Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST);

        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
