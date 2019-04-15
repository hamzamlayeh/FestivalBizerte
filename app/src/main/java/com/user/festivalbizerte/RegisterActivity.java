package com.user.festivalbizerte;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.user.festivalbizerte.Model.RSResponse;
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

public class RegisterActivity extends AppCompatActivity {
    Context context;
    @BindView(R.id.regName)
    EditText Nom;
    @BindView(R.id.regPrenom)
    EditText Prenom;
    @BindView(R.id.regMail)
    EditText Email;
    @BindView(R.id.tel)
    EditText Tel;
    @BindView(R.id.regPassword)
    EditText Password;
    @BindView(R.id.regPassword2)
    EditText ConfiremPassword;
    String email, password, nom, prenom, tel, confiremPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        context = this;
        ButterKnife.bind(this);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/raleway_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build())).build());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.txtlogin)
    public void LoginPage() {
        startActivity(new Intent(context, LoginActivity.class));
    }

    @OnClick(R.id.regBtn)
    public void Inscrire() {
        email = Email.getText().toString().trim();
        password = Password.getText().toString().trim();
        nom = Nom.getText().toString().trim();
        prenom = Prenom.getText().toString().trim();
        confiremPassword = ConfiremPassword.getText().toString().trim();
        tel = Tel.getText().toString().trim();
        if (Valider()) {
            if (Helpers.isConnected(context)) {
                UserInfos userInfos = new UserInfos(nom, prenom, email,password, tel);
                Call<RSResponse> callUpload = WebService.getInstance().getApi().inscrireUser(userInfos);
                callUpload.enqueue(new Callback<RSResponse>() {
                    @Override
                    public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                        if (response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                startActivity(new Intent(context, LoginActivity.class));
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
        if (nom.isEmpty()) {
            Nom.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (prenom.isEmpty()) {
            Prenom.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (tel.isEmpty()) {
            Tel.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (confiremPassword.isEmpty()) {
            ConfiremPassword.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (password.isEmpty()) {
            Password.setError(getString(R.string.champs_obligatoir));
            valide = false;
        }
        if (!password.isEmpty() && !confiremPassword.isEmpty() && !password.equals(confiremPassword)) {
            ConfiremPassword.setError("password n ai pas identique ");
            valide = false;
        }
        return valide;
    }
}
