package com.trabelsi.malek.topquiz.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.trabelsi.malek.topquiz.R;
import com.trabelsi.malek.topquiz.model.Question;
import com.trabelsi.malek.topquiz.model.QuestionBank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mQuestion;
    private Button mAnswer1;
    private Button mAnswer2;
    private Button mAnswer3;
    private Button mAnswer4;

    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mNumberOfQuestions;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mQuestionBank = this.generateQuestions();

        mScore = 0;
        mNumberOfQuestions = 4;

        mQuestion = (TextView) findViewById(R.id.activity_game_question);
        mAnswer1 = (Button) findViewById(R.id.activity_game_answer_1);
        mAnswer2 = (Button) findViewById(R.id.activity_game_answer_2);
        mAnswer3 = (Button) findViewById(R.id.activity_game_answer_3);
        mAnswer4 = (Button) findViewById(R.id.activity_game_answer_4);

        mAnswer1.setTag(0);
        mAnswer2.setTag(1);
        mAnswer3.setTag(2);
        mAnswer4.setTag(3);

        mAnswer1.setOnClickListener(this);
        mAnswer2.setOnClickListener(this);
        mAnswer3.setOnClickListener(this);
        mAnswer4.setOnClickListener(this);

        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);
    }

    @Override
    public void onClick(View view) {
        int responseIndex = (int) view.getTag();

        if (responseIndex == mCurrentQuestion.getAnswerIndex()){
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        }

        if (--mNumberOfQuestions==0){
            endGame();
        }
        else {
            mCurrentQuestion=mQuestionBank.getQuestion();
            displayQuestion(mCurrentQuestion);
        }
    }

    private void endGame(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well Done !")
                .setMessage("Your score is: " + mScore)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick (DialogInterface dialog, int which){
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    private void displayQuestion(final Question question){
        mQuestion.setText(question.getQuestion());
        mAnswer1.setText(question.getChoiceList().get(0));
        mAnswer2.setText(question.getChoiceList().get(1));
        mAnswer3.setText(question.getChoiceList().get(2));
        mAnswer4.setText(question.getChoiceList().get(3));
}
private QuestionBank generateQuestions() {
    Question question1 = new Question("Qui est le président actuel de la France?",
            Arrays.asList("François Hollande", "Emmanuel Macron", "Jacques Chirac", "François Mitterand"), 1);

    Question question2 = new Question("Combien de pays y'a-t-il dans l'Union Européenne?",
            Arrays.asList("15", "24", "28", "32"), 2);

    Question question3 = new Question("Combien d'étoiles y'a-t-il sur le drapeau des USA?",
            Arrays.asList("30", "40", "50", "60"), 2);

    Question question4 = new Question("Quelle couleur obtenez-vous quand vous mélangez du bleu et du jaune?",
            Arrays.asList("vert", "marron", "violet", "bleu foncé"), 0);

    Question question5 = new Question("Quelle est la longueur de la grande muraille de la Chine?",
            Arrays.asList("1000 miles", "2000 miles", "3000 miles", "4000 miles"), 3);

    Question question6 = new Question("Quelle est la capitale de la l'Espagne?",
            Arrays.asList("Madrid", "Barcelone", "Valence", "Séville"), 0);

    return new QuestionBank(Arrays.asList(question1,question2,question3,question4,question5,question6));
    }
}
