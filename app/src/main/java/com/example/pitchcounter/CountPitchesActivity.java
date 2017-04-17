package com.example.pitchcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Button;


public class CountPitchesActivity extends AppCompatActivity {

    public static int ball, ball4, hitByPitch;

    public static int strikeSwinging, strikeLooking, strikeFoul;
    public static int strikeOutSwinging, strikeOutLooking;
    public static int outFly, outGround, onBaseError, onBaseHit;

    public static int batterBalls, batterStrikes, inningOuts, inning;

    public static int ballShadow, ball4Shadow, hitByPitchShadow;

    public static int strikeSwingingShadow, strikeLookingShadow, strikeFoulShadow;
    public static int strikeOutSwingingShadow, strikeOutLookingShadow;
    public static int outFlyShadow, outGroundShadow, onBaseErrorShadow, onBaseHitShadow;

    public static int batterBallsShadow, batterStrikesShadow, inningOutsShadow, inningShadow=1;

    public static int scoreUs, scoreThem;
    public static int scoreUsShadow, scoreThemShadow;

    public static boolean firstTimeCreate=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_pitches);

        if (firstTimeCreate) {
            reallyClearData();
            firstTimeCreate = false;
        }  else {
            reallyUpdateAll();
        }
    }

    private void updateTotalPitches() {
        int totalPitches;

        totalPitches = ball + ball4 + hitByPitch + strikeSwinging + strikeLooking + strikeFoul;
        totalPitches += strikeOutSwinging + strikeOutLooking + outFly + outGround + onBaseError
                     + onBaseHit;

        TextView totalPitchesValue = (TextView) findViewById(R.id.textView_TotalPitchesValue);

        totalPitchesValue.setText(String.format("%d", totalPitches));
    }

    private void updateTotalBalls() {
        int totalBalls;

        totalBalls = ball + ball4 + hitByPitch;

        TextView totalBallsValue = (TextView) findViewById(R.id.textView_TotalBallsValue);

        String totalBallsString = String.format("%d", totalBalls);

        totalBallsValue.setText(totalBallsString);

        updateTotalPitches();
    }

    private void updateTotalStrikes() {
        int totalStrikes;

        totalStrikes = strikeSwinging + strikeLooking + strikeFoul
                       + strikeOutSwinging + strikeOutLooking
                       + outFly + outGround + onBaseError + onBaseHit;


        TextView totalStrikesValue = (TextView) findViewById(R.id.textView_TotalStrikesValue);

        totalStrikesValue.setText(String.format("%d", totalStrikes));

        updateTotalPitches();
    }

    // function to query the up/down spinner and decide if we want to increment or decrement.
    private int upOrDownValue() {
        // get the handle for the color spinner
        Spinner upDownSpinner = (Spinner) findViewById(R.id.spinner_UpDown);
        String upDownString = String.valueOf(upDownSpinner.getSelectedItem());

        if(upDownString.equals("up")) {
            return 1;
        } else {
            return -1;
        }

    }

    private void updateBatterBalls() {
        TextView batterBallsView = (TextView) findViewById(R.id.textView_BatterBallsValue);

        batterBallsView.setText(String.format("%d", batterBalls));
    }

    private void updateBatterStrikes() {
        TextView batterStrikesView = (TextView) findViewById(R.id.textView_BatterStrikesValue);

        batterStrikesView.setText(String.format("%d", batterStrikes));
    }

    private void updateInningOuts() {
        TextView inningOutsView = (TextView) findViewById(R.id.textView_InningOutsValue);

        inningOutsView.setText(String.format("%d", inningOuts));

    }

    private void updateInning() {
        TextView inningView = (TextView) findViewById(R.id.textView_InningValue);

        inningView.setText(String.format("%d", inning));

    }

    private boolean pitchWasBall() {
        batterBalls += upOrDownValue();

        if (batterBalls == 4) {
            batterGotOnBase();

            return true;
        } else {
            updateBatterBalls();

            return false;
        }

    }

    private boolean pitchWasStrike() {
        batterStrikes += upOrDownValue();

        if (batterStrikes == 3) {
            batterWasOut();

            return true;
        } else {
            updateBatterStrikes();

            return false;
        }

    }

    private void pitchWasFoul() {
        batterStrikes ++;

        if (batterStrikes == 3) {
            batterStrikes = 2;
        } else {
            updateBatterStrikes();
        }

    }

    private void batterWasOut() {
        inningOuts++;

        if (inningOuts >=3) {
            inningOuts = 0;
            inning++;
        }
        batterBalls = batterStrikes = 0;

        updateBatterBalls();
        updateBatterStrikes();
        updateInningOuts();
        updateInning();
    }

    private void batterGotOnBase() {
        batterBalls = batterStrikes = 0;

        updateBatterBalls();
        updateBatterStrikes();

    }

    private void updateOutFly() {
        Button outFlyView = (Button) findViewById(R.id.button_OutFly);
        outFlyView.setText(String.format("%d",outFly));

    }

    // ball in play
    public void onClickOutFly(View view) {
        Button outFlyView = (Button) findViewById(R.id.button_OutFly);

        pushShadowRegisters();
        outFly+= upOrDownValue();

        outFlyView.setText(String.format("%d",outFly));
        updateTotalStrikes();

        batterWasOut();
    }

    private void updateOutGround() {
        Button outFlyView = (Button) findViewById(R.id.button_OutGround);
        outFlyView.setText(String.format("%d",outGround));
    }

    public void onClickOutGround(View view) {
        Button outGroundView = (Button) findViewById(R.id.button_OutGround);

        pushShadowRegisters();
        outGround+=upOrDownValue();

        outGroundView.setText(String.format("%d",outGround));
        updateTotalStrikes();
        batterWasOut();
    }

    public void onClickPlusOut(View view) {
        Button outGroundView = (Button) findViewById(R.id.button_OutGround);

        pushShadowRegisters();

        batterWasOut();
    }

    private void updateError() {
        Button outFlyView = (Button) findViewById(R.id.button_Error);
        outFlyView.setText(String.format("%d",onBaseError));
    }
    public void onClickError(View view) {
        Button onBaseErrorView = (Button) findViewById(R.id.button_Error);

        pushShadowRegisters();
        onBaseError+=upOrDownValue();

        onBaseErrorView.setText(String.format("%d",onBaseError));
        updateTotalStrikes();

        batterGotOnBase();
    }
    private void updateHit() {
        Button outFlyView = (Button) findViewById(R.id.button_Hit);
        outFlyView.setText(String.format("%d",onBaseHit));
    }

    public void onClickHit(View view) {
        Button onBaseHitView = (Button) findViewById(R.id.button_Hit);

        pushShadowRegisters();
        onBaseHit+=upOrDownValue();

        onBaseHitView.setText(String.format("%d",onBaseHit));
        updateTotalStrikes();

        batterGotOnBase();
    }

    private void updateStrikeOutLooking() {
        Button outFlyView = (Button) findViewById(R.id.button_StrikeOutLooking);
        outFlyView.setText(String.format("%d",strikeOutLooking));
    }

    // Strike outs
    public void onClickStrikeOutLooking(View view) {
        Button strikeOutLookingView = (Button) findViewById(R.id.button_StrikeOutLooking);

        pushShadowRegisters();
        strikeOutLooking+=upOrDownValue();

        strikeOutLookingView.setText(String.format("%d",strikeOutLooking));
        updateTotalStrikes();

        batterWasOut();
    }

    private void updateStrikeOutSwinging() {
        Button outFlyView = (Button) findViewById(R.id.button_StrikeOutSwinging);
        outFlyView.setText(String.format("%d",strikeOutSwinging));
    }

    public void onClickStrikeOutSwinging(View view) {
        Button strikeOutSwingingView = (Button) findViewById(R.id.button_StrikeOutSwinging);

        pushShadowRegisters();
        strikeOutSwinging+=upOrDownValue();

        strikeOutSwingingView.setText(String.format("%d",strikeOutSwinging));
        updateTotalStrikes();

        batterWasOut();
    }

    private void updateStrikeSwing() {
        Button outFlyView = (Button) findViewById(R.id.button_StrikeSwing);
        outFlyView.setText(String.format("%d",strikeSwinging));
    }

    public void onClickStrikeSwing(View view) {
        Button strikeSwingingView = (Button) findViewById(R.id.button_StrikeSwing);

        pushShadowRegisters();
        if (pitchWasStrike() ) {
            inningOuts--;
            onClickStrikeOutSwinging(view);
        } else {
            strikeSwinging+=upOrDownValue();

            strikeSwingingView.setText(String.format("%d", strikeSwinging));
            updateTotalStrikes();
        }

    }

    private void updateStrikeLook() {
        Button outFlyView = (Button) findViewById(R.id.button_StrikeLooking);
        outFlyView.setText(String.format("%d",strikeLooking));
    }

    public void onClickStrikeLook(View view) {
        Button strikeLookingView = (Button) findViewById(R.id.button_StrikeLooking);

        pushShadowRegisters();
        if (pitchWasStrike()) {
            inningOuts--;
            onClickStrikeOutLooking(view);
        } else {
            strikeLooking+=upOrDownValue();

            strikeLookingView.setText(String.format("%d", strikeLooking));
            updateTotalStrikes();
        }
    }

    private void updateStrikeFoul() {
        Button outFlyView = (Button) findViewById(R.id.button_StrikeFoul);
        outFlyView.setText(String.format("%d",strikeFoul));
    }

    public void onClickStrikeFoul(View view) {
        Button strikeFoulView = (Button) findViewById(R.id.button_StrikeFoul);

        pushShadowRegisters();
        strikeFoul+=upOrDownValue();

        strikeFoulView.setText(String.format("%d",strikeFoul));
        updateTotalStrikes();

        pitchWasFoul();
    }

    private void updateBall() {
        Button outFlyView = (Button) findViewById(R.id.button_Ball);
        outFlyView.setText(String.format("%d",ball));
    }

    public void onClickBall(View view) {
        Button ballView = (Button) findViewById(R.id.button_Ball);

        pushShadowRegisters();
        if (pitchWasBall()) {
            onClickBall4(view);
        } else {
            ball+=upOrDownValue();

            String ballAsString = String.format("%d", ball);
            ballView.setText(ballAsString);

            updateTotalBalls();
        }
    }

    private void updateBall4() {
        Button outFlyView = (Button) findViewById(R.id.button_Ball4);
        outFlyView.setText(String.format("%d",ball4));
    }

    public void onClickBall4(View view) {
        Button ball4View = (Button) findViewById(R.id.button_Ball4);

        pushShadowRegisters();
        ball4+=upOrDownValue();

        String ballAsString = String.format("%d", ball4);
        ball4View.setText(ballAsString);

        updateTotalBalls();

        batterGotOnBase();
    }

    private void updateHitByPitch() {
        Button outFlyView = (Button) findViewById(R.id.button_HitByPitch);
        outFlyView.setText(String.format("%d",hitByPitch));
    }

    public void onClickHitByPitch(View view) {
        Button hitByPitchView = (Button) findViewById(R.id.button_HitByPitch);

        pushShadowRegisters();
        hitByPitch+=upOrDownValue();

        String ballAsString = String.format("%d", hitByPitch);
        hitByPitchView.setText(ballAsString);

        updateTotalBalls();

        batterGotOnBase();
    }

    private void reallyUpdateAll() {
        updateTotalBalls();
        updateTotalStrikes();

        updateOutFly();
        updateOutGround();
        updateError();
        updateHit();
        updateStrikeOutLooking();
        updateStrikeOutSwinging();
        updateStrikeSwing();
        updateStrikeLook();
        updateStrikeFoul();
        updateBall();
        updateBall4();
        updateHitByPitch();
        updateBatterBalls();
        updateBatterStrikes();
        updateInningOuts();
        updateInning();
        updateScoreUs();
        updateScoreThem();

    }

    private void reallyClearData() {
        ball = ball4 = hitByPitch = 0;
        strikeSwinging = strikeLooking = strikeFoul = 0;
        strikeOutSwinging = strikeOutLooking = 0;
        outFly = outGround = onBaseError = onBaseHit = 0;
        batterBalls = batterStrikes = inningOuts = 0;
        inning = 1;
        scoreUs=scoreThem=0;

        reallyUpdateAll();
    }

    public void onClickClear(View view) {
        pushShadowRegisters();
        reallyClearData();

    }

    public void onClickUndo(View view) {
        popShadowRegisters();
        reallyUpdateAll();
    }

    private void updateScoreUs() {
        Button scoreUsView = (Button) findViewById(R.id.button_ScoreUs);
        scoreUsView.setText(String.format("%d",scoreUs));
    }

    public void onClickScoreUs(View view) {
        pushShadowRegisters();
        scoreUs += upOrDownValue();
        updateScoreUs();
    }

    private void updateScoreThem() {
        Button scoreThemView = (Button) findViewById(R.id.button_ScoreThem);
        scoreThemView.setText(String.format("%d",scoreThem));
    }

    public void onClickScoreThem(View view) {
        pushShadowRegisters();
        scoreThem += upOrDownValue();
        updateScoreThem();
    }


    private void pushShadowRegisters() {
        ballShadow = ball;
        ball4Shadow = ball4;
        hitByPitchShadow = hitByPitch;

        strikeSwingingShadow = strikeSwinging;
        strikeLookingShadow = strikeLooking;
        strikeFoulShadow = strikeFoul;
        strikeOutSwingingShadow = strikeOutSwinging;
        strikeOutLookingShadow = strikeOutLooking;
        outFlyShadow = outFly;
        outGroundShadow = outGround;
        onBaseErrorShadow = onBaseError;
        onBaseHitShadow = onBaseHit;

        batterBallsShadow = batterBalls;
        batterStrikesShadow = batterStrikes;
        inningOutsShadow = inningOuts;
        inningShadow = inning;

        scoreUsShadow = scoreUs;
        scoreThemShadow = scoreThem;

    }

    private void popShadowRegisters() {
        ball = ballShadow;
        ball4 = ball4Shadow;
        hitByPitch = hitByPitchShadow;

        strikeSwinging = strikeSwingingShadow;
        strikeLooking = strikeLookingShadow;
        strikeFoul = strikeFoulShadow;
        strikeOutSwinging = strikeOutSwingingShadow;
        strikeOutLooking = strikeOutLookingShadow;
        outFly = outFlyShadow;
        outGround = outGroundShadow;
        onBaseError = onBaseErrorShadow;
        onBaseHit = onBaseHitShadow;

        batterBalls = batterBallsShadow;
        batterStrikes = batterStrikesShadow;
        inningOuts = inningOutsShadow;
        inning = inningShadow;

        scoreThem = scoreThemShadow;
        scoreUs = scoreUsShadow;

    }
}
