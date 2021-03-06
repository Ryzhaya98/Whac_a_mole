package com.example.whac_a_mole;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class Field extends LinearLayout {
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final SquareButton[] circles = new SquareButton[9];
    private int currentCircle;
    private Listener listener;
    MainActivity mainActivity;


    private int score;
    private Mole mole;

    private boolean isInGameSession = false;
    private SquareButton lastClickedButton = null;

    private final int ACTIVE_TAG_KEY = 873374234;

    public Field(Context context) {
        super(context);
        initializeViews(context);
    }

    public Field(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public Field(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    public Field(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeViews(context);
    }

    public int totalCircles() {
        return circles.length;
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_field, this, true);

        circles[0] = (SquareButton) findViewById(R.id.hole1);
        circles[1] = (SquareButton) findViewById(R.id.hole2);
        circles[2] = (SquareButton) findViewById(R.id.hole3);
        circles[3] = (SquareButton) findViewById(R.id.hole4);
        circles[4] = (SquareButton) findViewById(R.id.hole5);
        circles[5] = (SquareButton) findViewById(R.id.hole6);
        circles[6] = (SquareButton) findViewById(R.id.hole7);
        circles[7] = (SquareButton) findViewById(R.id.hole8);
        circles[8] = (SquareButton) findViewById(R.id.hole9);

    }

    private void resetScore() {
        score = 0;
    }

    public void startGame() {
        isInGameSession = true;
        lastClickedButton = null;
        mainActivity = new MainActivity();

        resetScore();
        resetCircles();
        for (SquareButton squareButton : circles) {
            squareButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isInGameSession) return;
                    lastClickedButton = squareButton;

                    boolean active = (boolean) view.getTag(ACTIVE_TAG_KEY);
                    if (active) {
                        score += mole.getCurrentLevel();
                        listener.updateScore(score);
                    } else {

                        endGame();
                        squareButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.orange_oval));
                    }
                }
            });
        }

        mole = new Mole(this);
        mole.startHopping();
    }

    public void endGame() {
        isInGameSession = false;
        mole.stopHopping();
        listener.onGameEnded(score);



    }

    public int getCurrentCircle() {
        return currentCircle;
    }

    private void resetCircles() {
        for (SquareButton squareButton : circles) {
            squareButton.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hole_inactive));
            squareButton.setTag(ACTIVE_TAG_KEY, false);
        }
    }

    public void setActive(int index) {
        if (lastClickedButton != null && circles[getCurrentCircle()] != lastClickedButton) {
            endGame();

            return;
        }
        mainHandler.post(() -> {
            resetCircles();
            SquareButton currentActive = circles[index];
            currentActive.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.hole_active));
            currentActive.setTag(ACTIVE_TAG_KEY, true);
            currentCircle = index;
        });
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onGameEnded(int score);

        void updateScore(int score);

        void onLevelChange(int level);
    }
}
