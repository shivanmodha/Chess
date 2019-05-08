package ru.cs213.chess.logic;

import android.util.Log;

import java.util.ArrayList;

/**
* <h1>Piece</h1>
* This Piece class is used for defining different functionality for each pieces of the board.
*
* @author  Shivan Modha & Nowshad Azimi
* @version 1.0
* @since   03-27-2019
*/
public abstract class Piece {
	/**
	* CPoint field that holds a point in the board, initialized with a1 
	*/
	public CPoint location = new CPoint('a', 1);
	/**
	* Holds the player info, initialized with white 
	*/
	private char player = 'w';
	/**
	* Tells whether the piece is visible 
	*/
	private boolean visible = true;
	/**
	* Number of moves a player can make 
	*/
	private int numberOfMoves = 0;
	/**
	* Tells whether enpassant move can be made 
	*/
	private boolean enpassant = false;
	/**
	* Instance of Cell class 
	*/
	public Cell cell;
	public Piece(char _player, CPoint _location, Cell _cell) {
		this.player = _player;
		this.location = _location;
		this.cell = _cell;
	}
	/**
	   * This method is used retrieve the location of certain piece
	   * @return CPoint This returns CPoint instance that holds the location.
	   */
	public CPoint getLocation() {
		return this.location;
	}
	/**
	   * This method is used retrieve the player field
	   * @return char This returns w or b, indicating the player. 
	   */
	public char getPlayer() {
		return this.player;
	}
	/**
	   * This method is used retrieve the visible field
	   * @return boolean This returns true or false, indicating whether the piece is visible.
	   */
	public boolean getVisible() {
		return this.visible;
	}
	/**
	   * This method is initialize the value of the field visible
	   * @return Nothing.
	   */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	/**
	   * This method is used retrieve the enpassant field
	   * @return boolean This returns true or false, indicating enpassant is possible.
	   */
	public boolean getEnpassant() {
		return this.enpassant;
	}
	/**
	   * This method is initialize the value of the enpassant visible
	   * @return Nothing.
	   */
	public void setEnpassant(boolean enpassant) {
		this.enpassant = enpassant;
	}
	/**
	   * This method is used retrieve the cell
	   * @return Cell This returns the Cell of certain instance.
	   */
	public Cell getCell() {
		return cell;
	}
	/**
	   * This method is implemented in other classes that extends Piece.java,
	   * it is used to determine the possible moves each pieces can make. 
	   */
	public abstract ArrayList<String> getMoves(Board env);
	/**
	   * This method is implemented in other classes that extends Piece.java,
	   * helper method to getMoves. 
	   */
	public abstract ArrayList<String> getAttacks(Board env);
	/**
	 * Simulates all the possible moves that this piece can do
	 * @param env Handle to the board
	 * @return Boolean value depicting whether or not this piece has a valid move
	 */
	public boolean emulateHasMove(Board env) {
		ArrayList<String> validLocations = this.getMoves(env);
		for (String loc : validLocations) {
			int toI = -1;
			try {
				toI = Integer.parseInt("" + loc.charAt(1));
			} catch(Exception e) {

			}
			CPoint to = new CPoint(loc.charAt(0), toI);
			CPoint oldLoc = this.location;
			Piece test = env.getPieceAtPoint(to);
			env.pieces.remove(location.toString());
			this.location = to;
			env.pieces.put(location.toString(), this);
			if (test == null || test.getPlayer() != this.getPlayer()) {
				if (test != null) {
					test.setVisible(false);
				}
			}
			if (player == 'w' && env.isWhiteChecked()) {
				if (test != null) {
					env.pieces.put(location.toString(), test);
					test.setVisible(true);
				} else {
					env.pieces.remove(location.toString());
				}
				this.location = oldLoc;
				env.pieces.put(location.toString(), this);
			} else if (player == 'b' && env.isBlackChecked()) {
				if (test != null) {
					env.pieces.put(location.toString(), test);
					test.setVisible(true);
				} else {
					env.pieces.remove(location.toString());
				}
				this.location = oldLoc;
				env.pieces.put(location.toString(), this);
			} else {
				if (test != null) {
					env.pieces.put(location.toString(), test);
					test.setVisible(true);
				} else {
					env.pieces.remove(location.toString());
				}
				this.location = oldLoc;
				env.pieces.put(location.toString(), this);
				return true;
			}
		}
		return false;
	}
	 /**
	   * This method does the function where the pieces actually moves
	   * @param env This parameter holds how the Board is at certain point
	   * @param to	This parameter holds the position the piece is going to move
	   * @return Nothing.
	   * @exception IllegalMoveException On input error.
	   * @see IllegalMoveException
	   */
	public void move(Board env, CPoint to) throws IllegalMoveException {
		ArrayList<String> validLocations = this.getMoves(env);
		if (validLocations != null && validLocations.contains(to.toString())) {
			boolean wC = env.isWhiteChecked();
			boolean bC = env.isBlackChecked();
			// emulate the move
			CPoint oldLoc = this.location;
			Piece test = env.getPieceAtPoint(to);
			env.pieces.remove(location.toString());
			this.location = to;
			env.pieces.put(location.toString(), this);
			if (test == null || test.getPlayer() != this.getPlayer()) {
				if (test != null) {
					test.setVisible(false);
				}
			}
			if (player == 'w' && env.isWhiteChecked()) {
				if (test != null) {
					env.pieces.put(location.toString(), test);
					test.setVisible(true);
				} else {
					env.pieces.remove(location.toString());
				}
				this.location = oldLoc;
				env.pieces.put(location.toString(), this);
				throw new IllegalMoveException("Illegal move, try again");
			} else if (player == 'b' && env.isBlackChecked()) {
				if (test != null) {
					env.pieces.put(location.toString(), test);
					test.setVisible(true);
				} else {
					env.pieces.remove(location.toString());
				}
				this.location = oldLoc;
				env.pieces.put(location.toString(), this);
				throw new IllegalMoveException("Illegal move, try again");
			} else {
				if (test != null) {
					env.pieces.put(location.toString(), test);
					test.setVisible(true);
				} else {
					env.pieces.remove(location.toString());
				}
				this.location = oldLoc;
				env.pieces.put(location.toString(), this);
			}
			// enpassant
			if (this instanceof Pawn) {
				int y = this.getLocation().getRawY();
				if (this.getPlayer() == 'w' && y == 5) {
					if (this.getLocation().getX() + 1 < 8) {
						CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() + 1], y);
						Piece p = env.getPieceAtPoint(pt);
						if (p != null && p.getPlayer() != this.getPlayer() && p.getEnpassant() == true) {
							p.visible = false;
						}
					}
					if (this.getLocation().getX() - 1 >= 0) {
						CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() - 1], y);
						Piece p = env.getPieceAtPoint(pt);
						if (p != null && p.getPlayer() != this.getPlayer() && p.getEnpassant() == true) {
							p.visible = false;
						}
					}
				}
				if (this.getPlayer() == 'b' && y == 4) {
					if (this.getLocation().getX() + 1 < 8) {
						CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() + 1], y);
						Piece p = env.getPieceAtPoint(pt);
						if (p != null && p.getPlayer() != this.getPlayer() && p.getEnpassant() == true) {
							p.visible = false;
						}
					}
					if (this.getLocation().getX() - 1 >= 0) {
						CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() - 1], y);
						Piece p = env.getPieceAtPoint(pt);
						if (p != null && p.getPlayer() != this.getPlayer() && p.getEnpassant() == true) {
							p.visible = false;
						}
					}
				}
			}
			Piece p = env.getPieceAtPoint(to);
			if (p == null || p.getPlayer() != this.getPlayer()) {
				this.location = to;
				if (p != null) {
					p.setVisible(false);
				}
			}
			if (this instanceof Pawn && numberOfMoves == 0 && (this.location.getRawY() == 5) || (this.location.getRawY() == 4)) {
				this.enpassant = true;
			}
			if (this instanceof King && numberOfMoves == 0 && (this.location.getRawX() == 'g')) {
				CPoint pos = new CPoint('h', this.location.getRawY());
				Piece rook = env.getPieceAtPoint(pos);
				if (rook != null) {
					rook.location = new CPoint('f', this.location.getRawY());
				}
			}
			if (this instanceof King && numberOfMoves == 0 && (this.location.getRawX() == 'b')) {
				CPoint pos = new CPoint('a', this.location.getRawY());
				Piece rook = env.getPieceAtPoint(pos);
				if (rook != null) {
					rook.location = new CPoint('c', this.location.getRawY());
				}
			}
			numberOfMoves++;
		} else {
			throw new IllegalMoveException("Illegal move, try again");
		}
	}
	 /**
	   * This method retrieve the numberOfMoves field
	   * @return int That has the value of numberOfMoves.
	   */
	public int getNumberOfMoves() {
		return numberOfMoves;
	}

	/**
	 * Sets the current number of moves
	 * @param _in Number of moves
	 */
	public void setNumberOfMoves(int _in) {
		numberOfMoves = _in;
	}
	 /**
	   * This method helps draw in a cell that is visible, it also checks 
	   * if enpassant is possible with a pawn.
	   * @param _base Holds the instance of the Board
	   * @param player Has the value of the player
	   * @return Nothing.
	   */
	public void render(Board _base, char player) {
		if (this.visible) {
			_base.draw(this, location);
		}
		if (player == this.player) {
			enpassant = false;
		}
	}
	 /**
	   * This toString method returns the drawing as a string
	   * @return String representing the value of a string.
	   */
	public String toString() {
		return this.getCell().render();
	}
}
