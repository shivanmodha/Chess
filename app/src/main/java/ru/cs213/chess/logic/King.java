package ru.cs213.chess.logic;

import java.util.ArrayList;

/**
* <h1>King</h1>
* Extends of Piece class, this King class is for the King piece, used to determine the movement of it.
*
* @author  Shivan Modha & Nowshad Azimi
* @version 1.0
* @since   03-27-2019
*/
public class King extends Piece {
	public King(char _player, CPoint _location) {
		super(_player, _location, new Cell(_player + "K"));
	}
	/**
	   * This method is used determine possible moves the King piece can make
	   * @param env This parameter is a Board type that has the current Board information
	   * @return ArrayList<String> This has all the possible moves the piece can make.
	   */
	public ArrayList<String> getMoves(Board env) {
		/**
		* Will contain all possible legal moves King can make
		*/
		ArrayList<String> validMoves = new ArrayList<String>();

		/**
		* King's current x/horizontal point in the cell
		*/
		int x = getLocation().getX();
		/**
		* King's current y/horizontal point in the cell
		*/
		int y = getLocation().getY();

		if (--x >= 0 && --y >= 0) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (--x >= 0 && ++y < 8) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (++x < 8 && ++y < 8) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (++x < 8 && --y >= 0) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (++x < 8) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (--x >= 0) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (++y < 8) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (--y >= 0) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		if (getNumberOfMoves() == 0) {
			if (getPlayer() == 'w') {
				if ((env.getPieceAtPoint(new CPoint('f', 1)) == null) && (env.getPieceAtPoint(new CPoint('g', 1)) == null) && (env.getPieceAtPoint(new CPoint('h', 1)).getNumberOfMoves() == 0)) {
					validMoves.add("g1");
				}
				if ((env.getPieceAtPoint(new CPoint('d', 1)) == null) && (env.getPieceAtPoint(new CPoint('c', 1)) == null) && (env.getPieceAtPoint(new CPoint('b', 1)) == null) && (env.getPieceAtPoint(new CPoint('a', 1)).getNumberOfMoves() == 0)) {
					validMoves.add("b1");
				}
			} else if (getPlayer() == 'b') {
				if ((env.getPieceAtPoint(new CPoint('f', 8)) == null) && (env.getPieceAtPoint(new CPoint('g', 8)) == null) && (env.getPieceAtPoint(new CPoint('h', 8)).getNumberOfMoves() == 0)) {
					validMoves.add("g8");
				}
				if ((env.getPieceAtPoint(new CPoint('d', 8)) == null) && (env.getPieceAtPoint(new CPoint('c', 8)) == null) && (env.getPieceAtPoint(new CPoint('b', 8)) == null) && (env.getPieceAtPoint(new CPoint('a', 8)).getNumberOfMoves() == 0)) {
					validMoves.add("b8");
				}
			}
		}

		ArrayList<String> violations = env.analyzePlayer(this.getPlayer() == 'w' ? 'b' : 'w');
		for (int i = validMoves.size() - 1; i >= 0; i--) {
			if (violations.contains(validMoves.get(i))) {
				validMoves.remove(i);
			}
		}

		return validMoves;
	}
	/**
	   * This helper method is used determine possible moves the King piece can make while
	   * making sure it does not make a move where it can be checked
	   * @param env This parameter is a Board type that has the current Board information
	   * @return ArrayList<String> This has all the possible moves the piece can make.
	   */
	public ArrayList<String> getAttacks(Board env) {
		ArrayList<String> validMoves = new ArrayList<String>();

		int x = getLocation().getX();
		int y = getLocation().getY();

		if (--x >= 0 && --y >= 0) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (--x >= 0 && ++y < 8) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (++x < 8 && ++y < 8) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (++x < 8 && --y >= 0) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (++x < 8) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (--x >= 0) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (++y < 8) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}

		x = getLocation().getX();
		y = getLocation().getY();

		if (--y >= 0) {
			CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x], 8-y);
			Piece p = env.getPieceAtPoint(pt);
			if (p != null && p.getPlayer() != getPlayer()) {
				validMoves.add(pt.toString());
			} else if (p == null) {			
				validMoves.add(pt.toString());
			}
		}
		return validMoves;
	}
}