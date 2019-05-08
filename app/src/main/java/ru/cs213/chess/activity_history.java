package ru.cs213.chess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class activity_history extends AppCompatActivity {
    String[] mHistory;
    Board mChessBoard;
    TextView moves;
    TextView player;
    TextView title;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the activity to fullscreen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_history);
        Intent intent = getIntent();
        mHistory = intent.getStringArrayExtra("HISTORY");
        mChessBoard = findViewById(R.id.l_history_board);
        moves = findViewById(R.id.l_history_moves);
        player = findViewById(R.id.l_history_player);
        title = findViewById(R.id.l_history_title);
        title.setText(intent.getStringExtra("NAME"));
        setBoard();
    }
    public void setBoard() {
        String state = mHistory[i];
        mChessBoard.setState(state);
        moves.setText("moves: " + (i + 1) + "/" + mHistory.length);
        if (i % 2 == 0) {
            player.setText("white");
        } else {
            player.setText("black");
        }
    }
    public void onBack(View view) {
        this.finish();
    }
    public void onNext(View view) {
        i++;
        if (i > mHistory.length - 1) {
            i = mHistory.length - 1;
        }
        setBoard();
    }
    public void onPrevious(View view) {
        i--;
        if (i < 0) {
            i = 0;
        }
        setBoard();
    }
}
