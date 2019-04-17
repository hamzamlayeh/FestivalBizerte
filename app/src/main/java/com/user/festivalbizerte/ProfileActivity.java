package com.user.festivalbizerte;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.user.festivalbizerte.Model.UserInfos;
import com.user.festivalbizerte.Utils.FileCompressor;
import com.user.festivalbizerte.WebService.Urls;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.nomPrenom)
    TextView NomPrenom;
    @BindView(R.id.Email)
    TextView Email;
    @BindView(R.id.tel)
    TextView Tel;
    @BindView(R.id.img_profil)
    SimpleDraweeView photo;
    SharedPreferences pref;
    UserInfos userInfos;
    private FileCompressor mCompressor;
    private File mPhotoFile;
    Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        pref = getApplicationContext().getSharedPreferences("Users", MODE_PRIVATE);
        userInfos = new Gson().fromJson(pref.getString("User", null), UserInfos.class);
        if (userInfos != null) {
            setUser(userInfos);
//            Log.i("nom", userInfos.getNom());
//            Log.i("prenom", userInfos.getPrenom());
//            Log.i("email", userInfos.getEmail());
//            Log.i("tel", userInfos.getTel());
//            Log.i("tel", userInfos.getPhoto());
        }

    }

    private void setUser(UserInfos userInfos) {
        NomPrenom.setText(String.format("%s %s", userInfos.getNom(), userInfos.getPrenom()));
        Email.setText(userInfos.getEmail());
        Tel.setText(userInfos.getTel());
        if (userInfos.getPhoto() != null) {
            photo.setImageURI(Urls.IMAGE_PROFIL+userInfos.getPhoto());
        }else {
            photo.setImageResource(R.drawable.userphoto);
        }
    }

    public void retour(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @OnClick(R.id.modifier)
    public void Modifer() {

    }
}
