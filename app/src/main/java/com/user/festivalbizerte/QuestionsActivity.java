package com.user.festivalbizerte;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.user.festivalbizerte.Model.QuestionReponse;
import com.user.festivalbizerte.Model.RSResponse;
import com.user.festivalbizerte.Utils.Helpers;
import com.user.festivalbizerte.WebService.WebService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class QuestionsActivity extends AppCompatActivity {

    @BindView(R.id.nb_question)
    TextView mNbQuestionView;
    @BindView(R.id.question)
    TextView mQuestionView;

    @BindView(R.id.choice1)
    Button mButtonChoice1;
    @BindView(R.id.choice2)
    Button mButtonChoice2;
    @BindView(R.id.choice3)
    Button mButtonChoice3;

    @BindView(R.id.rep1Is_valide)
    TextView mChoice1Valide;
    @BindView(R.id.rep2Is_valide)
    TextView mChoice2Valide;
    @BindView(R.id.rep3Is_valide)
    TextView mChoice3Valide;

    Context context;
    int Id_Quiz, mScore = 0, mQuestionNumber = 0;
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
                            //Log.i("test", listQuestion.get(0).getListeRep().size() + "/test");
                            updateQuestion();
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

    @OnClick(R.id.choice1)
    public void ChoiceReponse1() {
        Toast.makeText(context, mChoice1Valide.getText()+"", Toast.LENGTH_SHORT).show();

    }

    @OnClick(R.id.choice2)
    public void ChoiceReponse2() {
        Toast.makeText(context, mChoice1Valide.getText()+"", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.choice3)
    public void ChoiceReponse3() {
        Toast.makeText(context, mChoice1Valide.getText()+"", Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
//        Log.i("num", mQuestionNumber + "");
        if (mQuestionNumber < listQuestion.size()) {
            mQuestionView.setText(listQuestion.get(mQuestionNumber).getBody());
            //Reponse1
            mButtonChoice1.setText(listQuestion.get(mQuestionNumber).getListeRep().get(0).getBody());
            mChoice1Valide.setText(String.valueOf(listQuestion.get(mQuestionNumber).getListeRep().get(0).getIs_valide()));
            //Reponse2
            mButtonChoice2.setText(listQuestion.get(mQuestionNumber).getListeRep().get(1).getBody());
            mChoice2Valide.setText(String.valueOf(listQuestion.get(mQuestionNumber).getListeRep().get(1).getIs_valide()));
            //Reponse3
            mButtonChoice3.setText(listQuestion.get(mQuestionNumber).getListeRep().get(2).getBody());
            mChoice3Valide.setText(String.valueOf(listQuestion.get(mQuestionNumber).getListeRep().get(2).getIs_valide()));

//            mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
            mQuestionNumber++;
            mNbQuestionView.setText(String.format("%s/%s", String.valueOf(mQuestionNumber), String.valueOf(listQuestion.size())));

        } else {
            mButtonChoice1.setClickable(false);
            mButtonChoice2.setClickable(false);
            mButtonChoice3.setClickable(false);
        }
    }
    private void updateScore(int point) {
        //mScoreView.setText(String.format("%s", String.valueOf(point)));
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
