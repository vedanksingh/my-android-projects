package com.areeb.crossgame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 1;
    int[] gameCondition = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[][] winningPos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    boolean gameActive = true;
    int count = 0;
    Button playAgain;
    TextView winnerText, currentPlayerText;
    String winner = "";
    final String YELLOWS_TURN = "Yellow's turn";
    final String REDS_TURN = "Red's turn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing xml views
        playAgain = findViewById(R.id.playAgainButton);
        winnerText = findViewById(R.id.winnerTextView);
        currentPlayerText = findViewById(R.id.currentPlayerTextView);
    }

    public void clicked(View view)
    {

        ImageView imageView = (ImageView) view;

        int clickedTag;
        clickedTag = Integer.parseInt(imageView.getTag().toString());

        if (gameCondition[clickedTag] == 0 && gameActive) {

            count++;
            imageView.setTranslationY(-1500);
            imageView.animate().translationYBy(1500).rotation(3600).setDuration(300);
            gameCondition[clickedTag] = activePlayer;
            if (activePlayer == 1) {
                imageView.setImageResource(R.drawable.zero);
                currentPlayerText.setText(REDS_TURN);
                activePlayer = 2;
            } else {
                imageView.setImageResource(R.drawable.cross);
                currentPlayerText.setText(YELLOWS_TURN);
                activePlayer = 1;
            }

            for (int[] winningPosition : winningPos) {
                if (gameCondition[winningPosition[0]] != 0 && gameCondition[winningPosition[0]] == gameCondition[winningPosition[1]]
                        && gameCondition[winningPosition[1]] == gameCondition[winningPosition[2]]) {

                    gameActive = false;

                    if (activePlayer == 1) {
                        winner = "Red";
                    } else {
                        winner = "Yellow";
                    }

                    winner = winner + " Won!!";
                    winnerText.setText(winner);

                    currentPlayerText.setText("");
                    playAgain.setVisibility(View.VISIBLE);
                    winnerText.setVisibility(View.VISIBLE);
                }
            }
            if (count >= 9 && winner.equals(""))
            {
                winner = "Match draw!!";
                winnerText.setText(winner);

                playAgain.setVisibility(View.VISIBLE);
                winnerText.setVisibility(View.VISIBLE);
                currentPlayerText.setText("");
            }
        }
    }

    public void resetGame(View view)
    {
        Button playAgainButton = findViewById(R.id.playAgainButton);
        TextView winnerTextView = findViewById(R.id.winnerTextView);
        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        for (int i = 0; i < gridLayout.getChildCount(); i++)
        {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            if (imageView.getDrawable() != null)
                imageView.setImageDrawable(null);
        }

        currentPlayerText.setText(YELLOWS_TURN);

        activePlayer = 1;
        gameActive = true;
        count = 0;
        Arrays.fill(gameCondition, 0);
        winner = "";
    }
}