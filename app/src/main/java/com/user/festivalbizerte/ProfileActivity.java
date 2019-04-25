package com.user.festivalbizerte;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.google.gson.Gson;
import com.user.festivalbizerte.Model.UserInfos;
import com.user.festivalbizerte.Utils.FileCompressor;
import com.user.festivalbizerte.WebService.Urls;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class ProfileActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.nomPrenom)
    TextView NomPrenom;
    @BindView(R.id.Email)
    TextView Email;
    @BindView(R.id.tel)
    TextView Tel;
    @BindView(R.id.score)
    TextView Score;
    @BindView(R.id.img_profil)
    SimpleDraweeView photo;
    SharedPreferences pref;
    UserInfos userInfos;
    AlertDialog alertDialog;
    View popupInputDialogView = null;
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
        context=this;
        ButterKnife.bind(this);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/raleway_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
//        ImagePipeline imagePipeline = Fresco.getImagePipeline();
//        imagePipeline.clearMemoryCaches();
//        imagePipeline.clearDiskCaches();
//        imagePipeline.clearCaches();
        pref = getApplicationContext().getSharedPreferences("Users", MODE_PRIVATE);
        userInfos = new Gson().fromJson(pref.getString("User", null), UserInfos.class);
        if (userInfos != null) {
            setUser(userInfos);
        }

    }

    private void setUser(UserInfos userInfos) {
        NomPrenom.setText(String.format("%s %s", userInfos.getNom(), userInfos.getPrenom()));
        Email.setText(userInfos.getEmail());
        Tel.setText(userInfos.getTel());
        Score.setText(String.valueOf(userInfos.getScore_final()));
        if (userInfos.getPhoto() != null) {
            photo.setImageURI(Urls.IMAGE_PROFIL + userInfos.getPhoto());
        } else {
            photo.setImageResource(R.drawable.userphoto);
        }
    }

    public void retour(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @OnClick(R.id.modifier)
    public void Modifer() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(true);
        initPopupViewControls();
        alertDialogBuilder.setView(popupInputDialogView);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /* Initialize popup dialog view and ui controls in the popup dialog. */
    private void initPopupViewControls() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        popupInputDialogView = layoutInflater.inflate(R.layout.profil_dialog_update, null);

//        nomEditText = popupInputDialogView.findViewById(R.id.nom);
//        prenomEditText = popupInputDialogView.findViewById(R.id.prenom);
//        adresseEditText = popupInputDialogView.findViewById(R.id.adress);
//        telEditText = popupInputDialogView.findViewById(R.id.tel);
//        passwordEditText = popupInputDialogView.findViewById(R.id.password);
//        Img_ProfileModifier = popupInputDialogView.findViewById(R.id.imageProfile);
//        LoadUserInfo(ID_user, Constants.EDITE_PROFIL);
    }
}
