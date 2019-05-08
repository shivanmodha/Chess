package ru.cs213.chess.logic;

import java.util.ArrayList;

/**
* <h1>Rook</h1>
* Extends of Piece class, this Rook class is for the Rook piece, used to determine the movement of it.
*
* @author  Shivan Modha & Nowshad Azimi
* @version 1.0
* @since   03-27-2019
*/
public class Rook extends Piece {
	public Rook(char _player, CPoint _location) {
		super(_player, _location, new Cell(_player + "R"));
	}
	 /**
	   * This method is used determine possible moves the Rook piece can make
	   * @param env This parameter is a Board type that has the current Board information
	   * @return ArrayList<String> This has all the possible moves the piece can make.
	   */
	public ArrayList<String> getMoves(Board env) {
		/**
		* Will contain all possible legal moves Rook can make
		*/
		ArrayList<String> validMoves = new ArrayList<String>();

		/**
		* Rook's current x/horizontal point in the cell
		*/
		int x = getLocation().getX();
		/**
		* Rook's current y/horizontal point in the cell
		*/
		int y = getLocation().getY();

		while (++x < 8) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
				break;
			} else if (p != null) break;
			
			validMoves.add(pt.toString());
		}

		x = getLocation().getX();
		y = getLocation().getY();

		while (--x >= 0) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
				break;
			} else if (p != null) break;
			
			validMoves.add(pt.toString());
		}

		x = getLocation().getX();
		y = getLocation().getY();

		while (++y < 8) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
				break;
			} else if (p != null) break;
			
			validMoves.add(pt.toString());
		}

		x = getLocation().getX();
		y = getLocation().getY();

		while (--y >= 0) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
				break;
			} else if (p != null) break;
			
			validMoves.add(pt.toString());
		}
		
		return validMoves;
	}
	/**
	   * This helper method is used determine possible moves the Rook piece can make
	   * @param env This parameter is a Board type that has the current Board information
	   * @return ArrayList<String> This has all the possible moves the piece can make.
	   */
	public ArrayList<String> getAttacks(Board env) {
		return getMoves(env);
	}
}