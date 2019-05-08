package ru.cs213.chess;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Board extends View {
    /**
     * The board's state is represented as a simple string, where each piece is represented as a hex number in string format
     *
     *  |--------|-------|-------|
     *  | Piece  | White | Black |
     *  |--------|-------|-------|
     *  | King   | 1     | 7     |
     *  | Queen  | 2     | 8     |
     *  | Rook   | 3     | 9     |
     *  | Bishop | 4     | A     |
     *  | Knight | 5     | B     |
     *  | Pawn   | 6     | C     |
     *  |--------|-------|-------|
     *
     * Therefore, a match history is essentially simply an array of strings
     */
    // Variables to maintain board state
    private String mBoardState = "9BA87AB9CCCCCCCC000000000000000000000000000000006666666635421453";
    /*
        This string is the board:
                9BA87AB9
                CCCCCCCC
                00000000
                00000000
                00000000
                00000000
                66666666
                35421453
        Which is the initial standard board in chess
    */
    // Variables to maintain the view rendering state
    private int mWidth = 300;
    private int mHeight = 300;
    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Board,0, 0);
        try {

        } finally {
            a.recycle();
        }
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);
        int minh = MeasureSpec.getSize(w) + getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);
        mWidth = w;
        mHeight = h;
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        int w = mWidth / 8;
        int h = mHeight / 8;
        for (int x = 0; x < mWidth; x += 2 * w) {
            for (int y = 0; y < mHeight; y += 2 * h) {
                canvas.drawRect(x, y, x + w, y + h, paint);
            }
        }
        canvas.drawText("Test", 0, 100, paint);
    }
}
