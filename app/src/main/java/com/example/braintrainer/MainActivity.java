package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    Button start;
    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    TextView status;
    int score = 0;
    int numberOfQuestions = 0;
    TextView scoreText;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    TextView question;
    TextView timer;
    Button playAgain;
    TextView percentageScore;
    ConstraintLayout gameLayout;
    int initialTime=30100;
    long timerMilliseconds =initialTime;
    CountDownTimer countDown= createTimer(timerMilliseconds);




    @SuppressLint("SetTextI18n")
    public void playAgain(View view){//restarting the game
        score=0;
        numberOfQuestions=0;
        scoreText.setText(score + "/" + numberOfQuestions);
        newQuestion();
        resetGameState("", false);

        if(view.getId() == R.id.playAgain){
            resetTimer(initialTime);
        }

       countDown.start();

    }//restarting the game
    public CountDownTimer createTimer(long timeChange){
        return
        new CountDownTimer(timeChange, 1000){   

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                timerMilliseconds =millisUntilFinished;
                timer.setText((millisUntilFinished / 1000) +"s");
            }

            @Override
            public void onFinish() {
                timerMilliseconds = 0;
                resetGameState("Game Complete", true);
            }
        };
    }//create timer
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public void chooseAns(View view) {//user picks answer and the question gets refreshed
        if (Integer.toString(locationOfCorrectAnswer).equals(view.getTag().toString())) {
            status.setText("Correct!");
            score++;
            resetTimer(1000);
           setBackgroundColor(Color.GREEN);
        } else {
            status.setText("Wrong!");
            resetTimer(-2000);
            setBackgroundColor(Color.RED);


        }
        status.setVisibility(View.VISIBLE);
        numberOfQuestions++;
        scoreText.setText(score + "/" + numberOfQuestions);
        newQuestion();
        double finalPercentageScore=(((score*1.0)/numberOfQuestions)*100);
        percentageScore.setText(String.format("%.2f %% correct answers",finalPercentageScore));
      if(finalPercentageScore>=90)
          percentageScore.setBackgroundColor(Color.GREEN);
        else if(finalPercentageScore>=80)
            percentageScore.setBackgroundColor(Color.BLUE);
        else if(finalPercentageScore>=70)
            percentageScore.setBackgroundColor(Color.YELLOW);
        else if(finalPercentageScore>=65)
            percentageScore.setBackgroundColor(Color.parseColor("#FFA500"));
        else
            percentageScore.setBackgroundColor(Color.RED);

    }//user picks answer and the question gets refreshed

    private void resetTimer(int change) {
        countDown.cancel();
        countDown= createTimer(timerMilliseconds +change);
        countDown.start();
    }
    private void setBackgroundColor(int color) {
        gameLayout.setBackgroundColor(color);
        gameLayout.postDelayed(() -> {
            gameLayout.setBackgroundColor(Color.TRANSPARENT);

        }, 100);
    }
    public void start(View view) {//user presses "GO" button to start the game


    start.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility((View.VISIBLE));
        playAgain(findViewById(R.id.timeLeft));

    }//user presses "GO" button to start the game

    @SuppressLint("SetTextI18n")
    public void newQuestion() {//providing a new question
        Random rand = new Random();

        int a;
        int b;
        int operation;
        operation = rand.nextInt(2);//if 0 then addition, if 1 then multiplication
        if(operation==0){//addition

        a = rand.nextInt(21);
        b = rand.nextInt(21);
        question.setText(a + " + " + b);

        locationOfCorrectAnswer = rand.nextInt(4);

        answers.clear();

        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {
                answers.add(a + b);

            } else {
                int wrongAnswer = rand.nextInt(41);
                while (wrongAnswer == a + b) {
                    wrongAnswer = rand.nextInt(41);
                }
                answers.add(wrongAnswer);
            }
        }
        }else{//multiplication
            a = rand.nextInt(21);
            b = rand.nextInt(21);
            question.setText(a + " X " + b);

            locationOfCorrectAnswer = rand.nextInt(4);

            answers.clear();

            for (int i = 0; i < 4; i++) {
                if (i == locationOfCorrectAnswer) {
                    answers.add(a * b);

                } else {
                    int wrongAnswer = rand.nextInt(401);
                    while (wrongAnswer == a * b) {
                        wrongAnswer = rand.nextInt(401);
                    }
                    answers.add(wrongAnswer);
                }
            }

        }

        button1.setText(Integer.toString(answers.get(0)));
        button2.setText(Integer.toString(answers.get(1)));
        button3.setText(Integer.toString(answers.get(2)));
        button4.setText(Integer.toString(answers.get(3)));
    }//new question(either addition or multiplication)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        question = findViewById(R.id.question);
        button1 = findViewById(R.id.answer1);
        button2 = findViewById(R.id.answer2);
        button3 = findViewById(R.id.answer3);
        button4 = findViewById(R.id.answer4);
        status = findViewById(R.id.status);
        scoreText = findViewById(R.id.score);
        timer = findViewById(R.id.timeLeft);
        playAgain = findViewById(R.id.playAgain);
        gameLayout=findViewById(R.id.gameLayout);
        percentageScore=findViewById(R.id.percentageScore);
     //   answerChoices = findViewById(R.id.answerChoices);


        start = (Button) findViewById(R.id.startGame);
        start.setVisibility(View.VISIBLE);
        gameLayout.setVisibility((View.INVISIBLE));


    }

    private void resetGameState(String gameStatus, boolean hide) {
        int gridState;
        int playAgainState;
        if(hide) {
            gridState = View.GONE;
            playAgainState = View.VISIBLE;
        } else {
            gridState = View.VISIBLE;
            playAgainState = View.INVISIBLE;
        }
        status.setText(gameStatus);
        button1.setVisibility(gridState);
        button2.setVisibility(gridState);
        button3.setVisibility(gridState);
        button4.setVisibility(gridState);
        playAgain.setVisibility(playAgainState);
        percentageScore.setVisibility(playAgainState);
        question.setVisibility(gridState);
    }

}