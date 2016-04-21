package es.ucm.fdi.tp.practica5.ataxx;

import java.util.List;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica4.ataxx.AtaxxMove;

/**
 * The Class AtaxxSwingPlayer.
 */
@SuppressWarnings("serial")
public class AtaxxSwingPlayer extends Player{

	/** The row. */
	private int row;
	
	/** The col. */
	private int col;
	
	/** The nrow. */
	private int nrow;
	
	/** The ncol. */
	private int ncol;
	
	/**
	 * Instantiates a new ataxx swing player.
	 */
	public AtaxxSwingPlayer(){
	}
	
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.control.Player#requestMove(es.ucm.fdi.tp.basecode.bgame.model.Piece, es.ucm.fdi.tp.basecode.bgame.model.Board, java.util.List, es.ucm.fdi.tp.basecode.bgame.model.GameRules)
	 */
	@SuppressWarnings("unused")
	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		
		GameMove newMove = new AtaxxMove(row, col, nrow, ncol, p);
		if (newMove != null)return newMove;
		else throw new GameError("Uknown move: " + "0001");
	}

	/**
	 * Sets the move.
	 *
	 * @param row the row
	 * @param col the col
	 * @param nrow the nrow
	 * @param ncol the ncol
	 */
	public void setMove(int row, int col, int nrow, int ncol) {
		this.row=row;
		this.col=col;
		this.nrow=nrow;
		this.ncol=ncol;
	}
}
