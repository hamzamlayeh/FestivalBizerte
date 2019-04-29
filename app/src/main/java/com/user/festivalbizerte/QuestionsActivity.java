package com.user.festivalbizerte;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.user.festivalbizerte.Model.QuestionReponse;
import com.user.festivalbizerte.Model.RSResponse;
import com.user.festivalbizerte.Utils.Helpers;
import com.user.festivalbizerte.WebService.WebService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionsActivity extends AppCompatActivity {
    Context context;
    int Id_Quiz;
    List<QuestionReponse> listQuestion = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_questions);
        ButterKnife.bind(this);
        context = this;
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("font/raleway_light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        Id_Quiz = getIntent().getExtras().getInt("Id_quiz", 0);
//        System.out.println(Id_Quiz);
        loadQuestions(Id_Quiz);

    }

    private void loadQuestions(int id_quiz) {
        if (Helpers.isConnected(context)) {
            Call<RSResponse> callUpload = WebService.getInstance().getApi().loadQuestion(id_quiz);
            callUpload.enqueue(new Callback<RSResponse>() {
                @Override
                public void onResponse(Call<RSResponse> call, Response<RSResponse> response) {
                    if (response.body() != null) {
                        if (response.body().getStatus() == 1) {
                            QuestionReponse[] tab = new Gson().fromJson(new Gson().toJson(response.body().getData()), QuestionReponse[].class);
                            listQuestion = Arrays.asList(tab);
                            Log.i("test",listQuestion.get(0).getListeRep().size()+"/test");

                        } else if (response.body().getStatus() == 0) {
                            Toast.makeText(getApplicationContext(), "Pas de Quiz pour le moument ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<RSResponse> call, Throwable t) {
                    Log.d("err", t.getMessage());
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Helpers.ShowMessageConnection(context);
        }
    }
}
