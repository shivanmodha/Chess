package ru.cs213.chess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class activity_game extends AppCompatActivity {
    private static String TAG = "[activity][game]";
    private Board mBoard;
    private Button mUndo;
    private TextView mPlayer;
    private ArrayList<String> mHistory = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        mBoard = findViewById(R.id.l_game_board);
        mHistory.add(mBoard.getState());
        mUndo = findViewById(R.id.l_game_undo);
        mBoard.setOnStateChangedListener(new OnStateChangedListener() {
            @Override
            public void onStateChanged(String newState, char newPlayer, char winner, char check) {
                mHistory.add(newState);
                if (mHistory.size() > 2) {
                    mUndo.setEnabled(true);
                }
                setCurrentPlayer(newPlayer);
            }
        });
        mPlayer = findViewById(R.id.l_game_player);
        setCurrentPlayer('w');
    }
    public void onUndo(View view) {
        mHistory.remove(mHistory.size() - 1);
        mHistory.remove(mHistory.size() - 1);
        mBoard.setState(mHistory.get(mHistory.size() - 1));
        mHistory.remove(mHistory.size() - 1);
        mUndo.setEnabled(false);
    }
    public void onHistory(View view) {

    }
    private void setCurrentPlayer(char player) {
        if (player == 'w') {
            mPlayer.setText(R.string.turn_w);
        } else if (player == 'b') {
            mPlayer.setText(R.string.turn_b);
        }
    }
}