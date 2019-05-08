package ru.cs213.chess.logic;

import java.util.ArrayList;
import java.util.HashMap;

/**
* <h1>Board</h1>
* The Board Class creates the Chess Board and the graphic.
*
* @author  Shivan Modha & Nowshad Azimi
* @version 1.0
* @since   03-27-2019
*/
public class Board {
	/**
	* Has the 8 by 8 Cells for the chess board
	*/
    private Cell[][] innerBoard;
    /**
	* Contains the Hashmap of the class piece
	*/
    public HashMap<String, Piece> pieces = new HashMap<String, Piece>();
	/**
	* Instance of King that is black
	*/
	private King blackKing;
	/**
	* Instance of King that is white
	*/
	private King whiteKing;
    public Board() {
        this.initialize();
    }
    
    /**
	   * This method is used initialize all the cells before inputing info.
	   * @return Nothing.
	   */
    private void initialize() {
        this.innerBoard = new Cell[9][8];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 8; j++) {
                this.innerBoard[i][j] = new Cell(Cell.WHITE);
            }
        }
        this.clear();
        for (int i = 0; i < 8; i++) {
            this.innerBoard[8][i] = new Cell(" " + CPoint.NUM_TO_ALPH[i]);
        }
    }
    
    /**
	   * This method is used clean the cell after a pieces have been moved.
	   * @return Nothing.
	   */
    public void clear() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.innerBoard[i][j] = new Cell((i + j) % 2 == 0 ? Cell.WHITE : Cell.BLACK);
            }
        }
        pieces.clear();
    }
    /**
	   * This method is used set the value of whiteKing field.
	   * @param k Holds the instance of King class.
	   * @return Nothing.
	   */
    public void setWhiteKing(King k) {
        whiteKing = k;
    }
    /**
	   * This method is used get the value of whiteKing field.
	   * @return King Returns the value of whiteKing field.
	   */
    public King getWhiteKing() {
        return whiteKing;
    }
    /**
	   * This method is used set the value of blackKing field.
	   * @param k Holds the instance of King class.
	   * @return Nothing.
	   */
    public void setBlackKing(King k) {
        blackKing = k;
    }
    /**
	   * This method is used get the value of blackKing field.
	   * @return King Returns the value of blackKing field.
	   */
    public King getBlackKing() {
        return blackKing;
    }
    /**
 	   * This method is used determine if whiteKing is checked. 
 	   * @return boolean Returns true of false whether the whiteKing is checked.
 	   */
    public boolean isWhiteChecked() {
        ArrayList<String> blackAttacks = analyzePlayer('b');
        return blackAttacks.contains(whiteKing.getLocation().toString());
    }
    /**
	   * This method is used what would happen if the piece moved there, then
	   * checking to see if there is a check or checkmate
	   * @param player Holds the value of the player 
	   * @return boolean Returns true if there are available moves based on the simulation, else false.
	   */
    public boolean emulate(char player) {
        String[] ps = pieces.keySet().toArray(new String[pieces.keySet().size()]);
        for (String o : ps) {
            Piece p = pieces.get(o);
            if (p.getPlayer() == player && p.getVisible() == true) {
                if (p.emulateHasMove(this)) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
 	   * This method is used determine if blackKing is checked
 	   * @return boolean Returns true of false whether the blackKing is checked.
 	   */
    public boolean isBlackChecked() {
        ArrayList<String> whiteAttacks = analyzePlayer('w');
        return whiteAttacks.contains(blackKing.getLocation().toString());
    }
    /**
	   * This method is put the pieces in the right location
	   * @param piece Holds the value of individual piece
	   * @param Location Location in the cell of the piece
	   * @return Nothing.
	   */
    public void draw(Piece piece, CPoint Location) {
        this.innerBoard[Location.getY()][Location.getX()] = piece.getCell();
        pieces.put(Location.toString(), piece);
    }
    
    /**
	   * This method is draw each line.
	   * @return Nothing.
	   */
    public void render() {
        String renderString = "";
        for (int i = 0; i < this.innerBoard.length; i++) {
            String line = "";
            for (int j = 0; j < this.innerBoard[i].length; j++) {
                line += this.innerBoard[i][j].render() + " ";
            }
            line += "" + ((i != 8) ? (8 - i) : (""));
            renderString += line + "\n";
        }
        System.out.println(renderString);
    }
    
    /**
	   * This method is return the Piece at certain cell.
	   * @param pt This parameter holds the point that is to be searched
	   * @return Piece Contains which piece at this certain position.
	   */
    public Piece getPieceAtPoint(CPoint pt) {
        return (Piece)pieces.get(pt.toString());
    }
    
    /**
	   * This method is to determine which player is making the moves 
	   * and the moves they are allowed to make
	   * @param player This parameter contains the player who's turn is next
	   * @return ArrayList<String> This contains all the moves this certain player can make.
	   */
    public ArrayList<String> analyzePlayer(char player) {
        HashMap<String, Integer> set = new HashMap<String, Integer>();
        ArrayList<String> validMoves = new ArrayList<String>();
        String[] ps = pieces.keySet().toArray(new String[pieces.keySet().size()]);
        for (String o : ps) {
            Piece p = pieces.get(o);
            if (p.getPlayer() == player && p.getVisible() == true) {
                ArrayList<String> moves = p.getAttacks(this);
                for (String m : moves) {
                    set.putIfAbsent(m, 1);
                }
            }
        }
        Object[] keys = set.keySet().toArray();
        for (Object o : keys) {
            validMoves.add((String)o);
        }
        return validMoves;
    }
    
    /**
	   * This method does the function where the pieces actually moves
	   * @param player This parameter contains the player who's turn is next
	   * @param start This parameter holds the position the piece is located
	   * @param end	This parameter holds the position the piece is going to move
	   * @return Nothing.
	   * @exception IllegalMoveException On input error.
	   * @see IllegalMoveException
	   */
    public void move(char player, CPoint start, CPoint end) throws IllegalMoveException {
        Piece p = pieces.get(start.toString());
        if (p != null) {
            if (p.getPlayer() == player) {
                try {
                    p.move(this, end);
                } catch (IllegalMoveException e) {
                    throw e;
                }
            } else {
                throw new IllegalMoveException("Illegal move, try again");
            }
        } else {
            throw new IllegalMoveException("Illegal move, try again");
        }
    }
    public ArrayList<String> getMoves(char player, CPoint start) throws IllegalMoveException {
        Piece p = pieces.get(start.toString());
        if (p != null) {
            if (p.getPlayer() == player) {
                return p.getMoves(this);
            } else {
                throw new IllegalMoveException("Illegal move, try again");
            }
        } else {
            throw new IllegalMoveException("Illegal move, try again");
        }
    }
}