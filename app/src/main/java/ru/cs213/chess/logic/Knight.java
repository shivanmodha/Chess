package ru.cs213.chess.logic;

import java.util.ArrayList;

/**
* <h1>Knight</h1>
* Extends of Piece class, this Knight class is for the Knight piece, used to determine the movement of it.
*
* @author  Shivan Modha & Nowshad Azimi
* @version 1.0
* @since   03-27-2019
*/
public class Knight extends Piece {
	public Knight(char _player, CPoint _location) {
		super(_player, _location, new Cell(_player + "N"));
	}
	 /**
	   * This method is used determine possible moves the Knight piece can make
	   * @param env This parameter is a Board type that has the current Board information
	   * @return ArrayList<String> This has all the possible moves the piece can make.
	   */
	public ArrayList<String> getMoves(Board env) {
		/**
		* Will contain all possible legal moves Knight can make
		*/
		ArrayList<String> validMoves = new ArrayList<String>();

		/**
		* Knight's current x/horizontal point in the cell
		*/
		int x = getLocation().getX();
		/**
		* Knight's current y/horizontal point in the cell
		*/
		int y = getLocation().getY();

		if (x - 2 >= 0) {
			if (y - 1 >= 0) {
				CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x - 2], 8-(y - 1));
				Piece p = env.getPieceAtPoint(pt);
				if (p != null && p.getPlayer() != getPlayer()) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				} else if (p == null) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				}
			}

			if (y + 1 < 8) {
				CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x - 2], 8-(y + 1));
				Piece p = env.getPieceAtPoint(pt);
				if (p != null && p.getPlayer() != getPlayer()) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				} else if (p == null) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				}
			}
		}

		if (x + 2 < 8) {
			if (y - 1 >= 0) {
				CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x + 2], 8-(y - 1));
				Piece p = env.getPieceAtPoint(pt);
				if (p != null && p.getPlayer() != getPlayer()) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				} else if (p == null) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				}
			}

			if (y + 1 < 8) {
				CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x + 2], 8-(y + 1));
				Piece p = env.getPieceAtPoint(pt);
				if (p != null && p.getPlayer() != getPlayer()) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				} else if (p == null) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				}
			}
		}

		if (y - 2 >= 0) {
			if (x - 1 >= 0) {
				CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x - 1], 8-(y - 2));
				Piece p = env.getPieceAtPoint(pt);
				if (p != null && p.getPlayer() != getPlayer()) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				} else if (p == null) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				}
			}

			if (x + 1 < 8) {
				CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x + 1], 8-(y - 2));
				Piece p = env.getPieceAtPoint(pt);
				if (p != null && p.getPlayer() != getPlayer()) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				} else if (p == null) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				}
			}
		}

		if (y + 2 >= 0) {
			if (x - 1 >= 0) {
				CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x - 1], 8-(y + 2));
				Piece p = env.getPieceAtPoint(pt);
				if (p != null && p.getPlayer() != getPlayer()) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				} else if (p == null) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				}
			}

			if (x + 1 < 8) {
				CPoint pt = new CPoint(CPoint.NUM_TO_ALPH[x + 1], 8-(y + 2));
				Piece p = env.getPieceAtPoint(pt);
				if (p != null && p.getPlayer() != getPlayer()) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				} else if (p == null) {
					if (pt.getY() < 8 && pt.getY() >= 0) {
						validMoves.add(pt.toString());
					}
				}
			}
		}

		return validMoves;
	}
	/**
	   * This helper method is used determine possible moves the Knight piece can make
	   * @param env This parameter is a Board type that has the current Board information
	   * @return ArrayList<String> This has all the possible moves the piece can make.
	   */
	public ArrayList<String> getAttacks(Board env) {
		return getMoves(env);
	}
}