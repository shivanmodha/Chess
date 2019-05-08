package ru.cs213.chess;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.VectorDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
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
    // Static variables for class differentiation
    private static String TAG = "[Board]";
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
    private Paint paintBlack;
    private Paint paintWhite;
    private Paint paintTransparent;
    private Bitmap[] mPieceImages = new Bitmap[13];
    private boolean mInitializeFailed = false;
    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Board,0, 0);
        try {

        } finally {
            a.recycle();
        }
        initializePaint();
        
    }
    private void initializePaint() {
        paintBlack = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBlack.setColor(Color.argb(50, 100, 100, 100));
        paintWhite = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintWhite.setColor(Color.argb(0, 235, 235, 235));
        paintTransparent = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
    private void initializeImages() {
        Resources res = getResources();
        mPieceImages[0] = null;
        try {
            mPieceImages[1] = generateBitmap(R.drawable.cp_1);
            mPieceImages[2] = generateBitmap(R.drawable.cp_2);
            mPieceImages[3] = generateBitmap(R.drawable.cp_3);
            mPieceImages[4] = generateBitmap(R.drawable.cp_4);
            mPieceImages[5] = generateBitmap(R.drawable.cp_5);
            mPieceImages[6] = generateBitmap(R.drawable.cp_6);
            mPieceImages[7] = generateBitmap(R.drawable.cp_7);
            mPieceImages[8] = generateBitmap(R.drawable.cp_8);
            mPieceImages[9] = generateBitmap(R.drawable.cp_9);
            mPieceImages[10] = generateBitmap(R.drawable.cp_10);
            mPieceImages[11] = generateBitmap(R.drawable.cp_11);
            mPieceImages[12] = generateBitmap(R.drawable.cp_12);
            mInitializeFailed = false;
        } catch (Exception e) {
            mInitializeFailed = true;
        }
    }
    private Bitmap generateBitmap(int drawableId) {
        int width = mWidth / 8 - 10;
        int height = mHeight / 8 - 10;
        VectorDrawable drawable = (VectorDrawable) ContextCompat.getDrawable(getContext(), drawableId);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    private int getPieceTypeFromCharIndex(int idx) {
        try {
            return Integer.parseInt(mBoardState.charAt(idx) + "", 16);
        } catch (Exception e) {
            return 0;
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
        if (mPieceImages[1] == null && !mInitializeFailed) {
            initializeImages();
        }
        if (!mInitializeFailed) {
            int w = mWidth / 8;
            int h = mHeight / 8;
            boolean flippy = false;
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    int charIndex = y * 8 + x;
                    int rX = x * w;
                    int rY = y * h;
                    if (flippy) {
                        canvas.drawRect(rX - 5, rY - 5, rX + w, rY + h, paintBlack);
                    } else {
                        canvas.drawRect(rX - 5, rY - 5, rX + w, rY + h, paintWhite);
                    }
                    int pieceType = getPieceTypeFromCharIndex(charIndex);
                    if (pieceType != 0) {
                        canvas.drawBitmap(mPieceImages[pieceType], rX, rY, paintTransparent);
                    }
                    flippy = !flippy;
                }
                flippy = !flippy;
            }
        }
    }

}
