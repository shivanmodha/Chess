package ru.cs213.chess.logic;

import java.util.ArrayList;

/**
* <h1>Pawn</h1>
* Extends of Piece class, this Pawn class is for the Pawn piece, used to determine the movement of it.
*
* @author  Shivan Modha & Nowshad Azimi
* @version 1.0
* @since   03-27-2019
*/
public class Pawn extends Piece {
	public Pawn(char _player, CPoint _location) {
		super(_player, _location, new Cell(_player + "p"));
	}
	public Pawn(char _player, CPoint _location, int _moves, boolean _enpassant) {
		this(_player, _location);
		this.setEnpassant(_enpassant);
		this.setNumberOfMoves(_moves);
	}
	/**
	   * This method is used determine possible moves the Pawn piece can make
	   * @param env This parameter is a Board type that has the current Board information
	   * @return ArrayList<String> This has all the possible moves the piece can make.
	   */
	public ArrayList<String> getMoves(Board env) {
		/**
		   * Holds of the value of number of moves the piece can do
		   */
		int moves = this.getNumberOfMoves();
		/**
		   * Holds of the array of possible moves the piece can do
		   */
		ArrayList<String> validMoves = new ArrayList<String>();
		/**
		* Pawn's current y/horizontal point in the cell
		*/
		int y = this.getLocation().getRawY();
		// First time move
		if (moves == 0) {
			int lB = (this.getPlayer() == 'w') ? y : y - 2;
			int hB = (this.getPlayer() == 'w') ? y + 2 : y;
			for (int i = lB; i <= hB; i++) {
				CPoint pt = new CPoint(this.getLocation().getRawX(), i);
				Piece p = env.getPieceAtPoint(pt);
				if (p == null) {
					validMoves.add(pt.toString());
				}
			}
		} else { // Regular Move
			int c = (this.getPlayer() == 'w') ? y + 1 : y - 1;
			CPoint pt = new CPoint(this.getLocation().getRawX(), c);
			Piece p = env.getPieceAtPoint(pt);
			if (p == null) {
				validMoves.add(pt.toString());
			}
		}
		// Kill Diagonally
		int nY = (this.getPlayer() == 'w') ? y + 1 : y - 1;
		if (this.getLocation().getX() + 1 < 8) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() + 1], nY);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != this.getPlayer()) {
				validMoves.add(pt.toString());
			}
		}
		if (this.getLocation().getX() - 1 >= 0) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() - 1], nY);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != this.getPlayer()) {
				validMoves.add(pt.toString());
			}
		}
		// Enpassant
		if (this.getPlayer() == 'w' && y == 5) {
			if (this.getLocation().getX() + 1 < 8) {
				CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() + 1], y);
				Piece p = env.getPieceAtPoint(pt);
				if (p != null && p.getPlayer() != this.getPlayer() && p.getEnpassant() == true) {
					pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() + 1], y + 1);
					validMoves.add(pt.toString());
				}
			}
			if (this.getLocation().getX() - 1 >= 0) {
				CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() - 1], y);
				Piece p = env.getPieceAtPoint(pt);
				if (p != null && p.getPlayer() != this.getPlayer() && p.getEnpassant() == true) {
					pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() - 1], y + 1);
					validMoves.add(pt.toString());
				}
			}
		}
		if (this.getPlayer() == 'b' && y == 4) {
			if (this.getLocation().getX() + 1 < 8) {
				CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() + 1], y);
				Piece p = env.getPieceAtPoint(pt);
				if (p != null && p.getPlayer() != this.getPlayer() && p.getEnpassant() == true) {
					pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() + 1], y - 1);
					validMoves.add(pt.toString());
				}
			}
			if (this.getLocation().getX() - 1 >= 0) {
				CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() - 1], y);
				Piece p = env.getPieceAtPoint(pt);
				if (p != null && p.getPlayer() != this.getPlayer() && p.getEnpassant() == true) {
					pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() - 1], y - 1);
					validMoves.add(pt.toString());
				}
			}
		}

		return validMoves;
	}
	/**
	   * This helper method is used determine possible moves the Pawn piece can make,
	   * while making sure the Pawn pieces attacks differently than it moves
	   * @param env This parameter is a Board type that has the current Board information
	   * @return ArrayList<String> This has all the possible moves the piece can make.
	   */
	public ArrayList<String> getAttacks(Board env) {
		ArrayList<String> validMoves = new ArrayList<String>();
		int y = this.getLocation().getRawY();
		int nY = (this.getPlayer() == 'w') ? y + 1 : y - 1;
		if (this.getLocation().getX() + 1 < 8) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() + 1], nY);
			Piece p = env.getPieceAtPoint(pt);
			validMoves.add(pt.toString());
		}
		if (this.getLocation().getX() - 1 >= 0) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[this.getLocation().getX() - 1], nY);
			Piece p = env.getPieceAtPoint(pt);
			validMoves.add(pt.toString());
		}
		return validMoves;
	}
}