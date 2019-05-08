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
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import ru.cs213.chess.logic.Bishop;
import ru.cs213.chess.logic.CPoint;
import ru.cs213.chess.logic.IllegalMoveException;
import ru.cs213.chess.logic.King;
import ru.cs213.chess.logic.Knight;
import ru.cs213.chess.logic.Pawn;
import ru.cs213.chess.logic.Piece;
import ru.cs213.chess.logic.Queen;
import ru.cs213.chess.logic.Rook;

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
    private static String BOARD_EMPTY_STATE = "0000000000000000000000000000000000000000000000000000000000000000";
    // Variables to maintain board state
    private String mBoardState = "9BA87AB9CCCCCCCC000000000000000000000000000000006666666635421453";
    private ArrayList<String> mBoardSelections = new ArrayList<String>();
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
    private Paint paintSelected;
    private Bitmap[] mPieceImages = new Bitmap[13];
    private boolean mInitializeFailed = false;
    private ru.cs213.chess.logic.Board mChessBoard = new ru.cs213.chess.logic.Board();
    private ArrayList<Piece> pieces = new ArrayList<Piece>();
    private King blackKing;
    private King whiteKing;
    private boolean mIsPlayable;
    private char currentPlayer = 'w';
    private CPoint selectedCell = null;
    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Board,0, 0);
        try {
            mIsPlayable = a.getBoolean(R.styleable.Board_isPlayable, true);
        } finally {
            a.recycle();
        }
        initializePaint();
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    onClick(event.getX(), event.getY());
                }
                return false;
            }
        });
        constructChessBoardFromState();
    }
    private void constructChessBoardFromState() {
        pieces.clear();
        for (int i = 0; i < mBoardState.length(); i++) {
            int pieceType = getPieceTypeFromCharIndex(i);
            Piece p = null;
            switch (pieceType) {
                case 1:
                    p = new King('w', charIndexToCPoint(i));
                    whiteKing = (King)p;
                    mChessBoard.setWhiteKing(whiteKing);
                    break;
                case 2:
                    p = new Queen('w', charIndexToCPoint(i));
                    break;
                case 3:
                    p = new Rook('w', charIndexToCPoint(i));
                    break;
                case 4:
                    p = new Bishop('w', charIndexToCPoint(i));
                    break;
                case 5:
                    p = new Knight('w', charIndexToCPoint(i));
                    break;
                case 6:
                    p = new Pawn('w', charIndexToCPoint(i));
                    break;
                case 7:
                    p = new King('b', charIndexToCPoint(i));
                    blackKing = (King)p;
                    mChessBoard.setWhiteKing(blackKing);
                    break;
                case 8:
                    p = new Queen('b', charIndexToCPoint(i));
                    break;
                case 9:
                    p = new Rook('b', charIndexToCPoint(i));
                    break;
                case 10:
                    p = new Bishop('b', charIndexToCPoint(i));
                    break;
                case 11:
                    p = new Knight('b', charIndexToCPoint(i));
                    break;
                case 12:
                    p = new Pawn('b', charIndexToCPoint(i));
                    break;
            }
            if (p != null) {
                pieces.add(p);
            }
        }
        mChessBoard.clear();
        for (Piece p : pieces) {
            p.render(mChessBoard, currentPlayer);
        }
    }
    private void constructStateFromBoard() {
        mBoardState = BOARD_EMPTY_STATE;
        for (Piece p : pieces) {
            CPoint point = p.getLocation();
            Piece piece = mChessBoard.getPieceAtPoint(point);
            if (piece != null) {
                int offset = 0;
                if ((piece.getPlayer() + "").equals("b")) {
                    offset = 6;
                }
                int pieceType = 0;
                if (piece instanceof King) {
                    pieceType = 1;
                } else if (piece instanceof Queen) {
                    pieceType = 2;
                } else if (piece instanceof Rook) {
                    pieceType = 3;
                } else if (piece instanceof Bishop) {
                    pieceType = 4;
                } else if (piece instanceof Knight) {
                    pieceType = 5;
                } else if (piece instanceof Pawn) {
                    pieceType = 6;
                }
                pieceType += offset;
                String pT = pieceType + "";
                if (pieceType == 10) {
                    pT = "A";
                } else if (pieceType == 11) {
                    pT = "B";
                } else if (pieceType == 12) {
                    pT = "C";
                }
                int charIndex = point.getY() * 8 + point.getX();
                mBoardState = mBoardState.substring(0, charIndex) + pT + mBoardState.substring(charIndex + 1);
            }
        }
    }
    private void render() {
        mChessBoard.clear();
        for (int i = pieces.size() - 1; i >= 0; i--) {
            Piece p = pieces.get(i);
            if (p instanceof Pawn && (p.getLocation().getRawY() == 1 || p.getLocation().getRawY() == 8)) {
                pieces.add(new Queen(p.getPlayer(), p.getLocation()));
                pieces.remove(p);
            }
        }
        for (Piece p : pieces) {
            if (p instanceof Pawn) {

            } else {
                if (p.getEnpassant() == true) {
                    p.setEnpassant(false);
                }
            }
        }
        for (Piece p : pieces) {
            p.render(mChessBoard, currentPlayer);
        }
        if (mIsPlayable) {
            // TODO Implement me
        }
        constructStateFromBoard();
        mBoardSelections.clear();
        invalidate();
    }
    private void initializePaint() {
        paintBlack = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBlack.setColor(Color.argb(50, 100, 100, 100));
        paintWhite = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintWhite.setColor(Color.argb(0, 235, 235, 235));
        paintTransparent = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSelected = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintSelected.setColor(Color.argb(50, 0, 0, 100));
    }
    private void initializeImages() {
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
        int width = mWidth / 8 - 15;
        int height = mHeight / 8 - 15;
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
    private CPoint charIndexToCPoint(int idx) {
        int y = idx % 8;
        int x = 8 - (idx / 8);
        return new CPoint(CPoint.NUM_TO_ALPH[y], x);
    }
    private void onClick(float x, float y) {
        int cellX = (int)(x * 8 / mWidth);
        int cellY = (int)(y * 8 / mHeight);
        int charIndex = cellY * 8 + cellX;
        CPoint point = charIndexToCPoint(charIndex);
        if (!mBoardSelections.contains(point.toString())) {
            selectedCell = point;
            try {
                mBoardSelections = mChessBoard.getMoves(currentPlayer, point);
            } catch (IllegalMoveException e) {
                mBoardSelections.clear();
            }
            invalidate();
        } else {
            try {
                mChessBoard.move(currentPlayer, selectedCell, point);
                render();
            } catch (IllegalMoveException e) {
                Log.d(TAG, "onClick: " + e.getMessage());
            }
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
        Log.d(TAG, "onDraw: " + mBoardState);
        if (mPieceImages[1] == null && !mInitializeFailed) {
            initializeImages();
        }
        if (!mInitializeFailed) {
            int w = mWidth / 8;
            int h = mHeight / 8;
            boolean flippy = false;
            for (String i : mBoardSelections) {
                int toI = -1;
                try {
                    toI = Integer.parseInt("" + i.charAt(1));
                } catch(Exception e) {

                }
                CPoint to = new CPoint(i.charAt(0), toI);
                int x = to.getX() * w;
                int y = to.getY() * h;
                canvas.drawRect(x, y, x + w, y + h, paintSelected);
            }
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    int charIndex = y * 8 + x;
                    int rX = x * w;
                    int rY = y * h;
                    if (!mBoardSelections.contains(charIndexToCPoint(charIndex).toString())) {
                        if (flippy) {
                            canvas.drawRect(rX, rY, rX + w, rY + h, paintBlack);
                        } else {
                            canvas.drawRect(rX, rY, rX + w, rY + h, paintWhite);
                        }
                    }
                    int pieceType = getPieceTypeFromCharIndex(charIndex);
                    if (pieceType != 0) {
                        canvas.drawBitmap(mPieceImages[pieceType], rX + 7.5f, rY + 7.5f, paintTransparent);
                    }
                    flippy = !flippy;
                }
                flippy = !flippy;
            }
        }
    }
}
