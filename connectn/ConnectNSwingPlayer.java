package es.ucm.fdi.tp.practica5.connectn;

import java.util.List;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNMove;

/**
 * The Class ConnectNSwingPlayer.
 */
@SuppressWarnings("serial")
public class ConnectNSwingPlayer extends Player{

	/** The row. */
	private int row;
	
	/** The col. */
	private int col;
	
	/**
	 * Instantiates a new connect n swing player.
	 */
	public ConnectNSwingPlayer(){
	}
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.control.Player#requestMove(es.ucm.fdi.tp.basecode.bgame.model.Piece, es.ucm.fdi.tp.basecode.bgame.model.Board, java.util.List, es.ucm.fdi.tp.basecode.bgame.model.GameRules)
	 */
	@SuppressWarnings("unused")
	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		GameMove newMove = new ConnectNMove(row, col, p);
		if (newMove != null)return newMove;
		throw new GameError("Uknown move: " + newMove.toString());

	}

	/**
	 * Sets the move.
	 *
	 * @param row the row
	 * @param col the col
	 */
	public void setMove(int row, int col) {
		this.row=row;
		this.col=col;
	}
	
	
}
