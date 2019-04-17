package com.user.festivalbizerte;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.user.festivalbizerte.Model.RSResponse;
import com.user.festivalbizerte.Model.User;
import com.user.festivalbizerte.Model.UserInfos;
import com.user.festivalbizerte.Utils.Helpers;
import com.user.festivalbizerte.WebService.WebService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.user.festivalbizerte.InviteAmisActivity.MY_PERMISSIONS_REQUEST;

public class LoginActivity extends AppCompatActivity {
    Context context;
    @BindView(R.id.email)
    EditText Email;
    @BindView(R.id.password)
    EditText Password;
    String email, password;
    SharedPreferences pref;
    SharedPreferences.Editor editors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        context = this;
        ButterKnife.bind(this);
        pref = getApplicationContext().getSharedPreferences("Users", MODE_PRIVATE);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/raleway_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkFineLocationPermission();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.txtInscrire)
    public void InscrirePage() {
        startActivity(new Intent(context, RegisterActivity.class));
    }

    @OnClick(R.id.loginBtn)
    public void Login() {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        if (Valider()) {
            if (Helpers.isConnected(context)) {
                User user = new User(email, password);
                Call<RSResponse> callUpload = WebService.getInstance().getApi().loginUser(user);
                callUpload.enqueue(new Callback<RSResponse>() {
                    @Override
                    public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                UserInfos user = new Gson().fromJson(new Gson().toJson(response.body().getData()), UserInfos.class);
                                editors = pref.edit();
                                editors.putString("User", new Gson().toJson(user));
                                editors.apply();
                                startActivity(new Intent(context, MainActivity.class));
                            } else if (response.body().getStatus() == 0) {
                                Toast.makeText(context, "err", Toast.LENGTH_SHORT).show();
                            } else if (response.body().getStatus() == 2) {
                                Toast.makeText(context, getString(R.string.EmailOuMotDePasseInvalide), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RSResponse> call, Throwable t) {
                        Log.i("err", t.getMessage());
                    }
                });
            } else {
                Helpers.ShowMessageConnection(context);
            }
        }
    }

    private boolean Valider() {
        boolean valide = true;
        if (email.isEmpty()) {
            Email.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (!email.isEmpty() && (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            Email.setError(getString(R.string.email_invalide));
            valide = false;
        }
        if (password.isEmpty()) {
            Password.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        return valide;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkFineLocationPermission() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST);
        }
    }
}
