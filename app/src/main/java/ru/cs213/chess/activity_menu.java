package ru.cs213.chess;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class activity_menu extends AppCompatActivity {
    private static String TAG = "[activity][main]";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
    }
    public void newGame(View view) {
        Log.d(TAG, "newGame: Spawning activity");
        Intent intent = new Intent(this, activity_game.class);
        startActivity(intent);
    }
}
