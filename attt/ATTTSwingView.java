package es.ucm.fdi.tp.practica5.attt;

import java.awt.Color;
import java.util.List;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.Main.PlayerMode;
import es.ucm.fdi.tp.practica5.swingView.RectBoardSwingView;

// TODO: Auto-generated Javadoc
/**
 * The Class ATTTSwingView.
 */
@SuppressWarnings("serial")
public class ATTTSwingView extends RectBoardSwingView{
	
	/** The player. */
	private ATTTSwingPlayer player;
	
	/** The first. */
	private boolean first;
	
	/** The activate. */
	private boolean activate;
	
	/** The initrow. */
	private int initrow;
	
	/** The initcol. */
	private int initcol;
	
	/** The pieces. */
	private int pieces;
	
	
	/**
	 * Instantiates a new ATTT swing view.
	 *
	 * @param g the g
	 * @param c the c
	 * @param localPiece the local piece
	 * @param randPlayer the rand player
	 * @param aiPlayer the ai player
	 */
	public ATTTSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer,
			Player aiPlayer) {
		super(g, c, localPiece, randPlayer, aiPlayer);
		player = new ATTTSwingPlayer();
		this.first = true;
		activate=true;
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.RectBoardSwingView#handleMouseClick(int, int, int)
	 */
	@Override
	protected void handleMouseClick(int row, int col, int mouseButton) {
		if(activate){
			if(mouseButton != 1){
				first = true;
				sendText("Your move have been reset");
			}else{
				if(this.pieces < 6){
					sendText("You put a piece on (" + row + ", " + col+")");
					player.setMove(-1, -1, row, col);
					decideMakeManualMove(player);
					this.pieces++;
				}
				else{
					if(first){
						sendText("You have selected (" + row + ", " + col+")");
						sendText("Choose your destiny");
						this.initrow = row;
						this.initcol = col;
						first = false;
					}else{
						sendText("You move (" + initrow + ", " + initcol+") to (" + row + ", " + col+")");
						player.setMove(initrow, initcol, row, col);
						decideMakeManualMove(player);
						this.pieces++;
						first = true;
					}
				}
			}
		}
		else sendText("You can't move, your board is disabled");
	}
	
	/*
	 * SwingView llama a estos métodos para indicar si se puede aceptar movimientos manuales o no
	 * */
	
	/**
	 *  Declare the board active, so handleMouseClick accepts moves
	 *  add corresponding message to the status messages indicating what
	 *  to do for making a move, etc.
	 */
	@Override
	protected void activateBoard() {
		activate = true;
	}

	/**
	 * Declare the board inactive, so handleMouseClick rejects moves
	 */
	@Override
	protected void deActivateBoard() {
		activate = false;
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.RectBoardSwingView#getPlayer()
	 */
	@Override
	protected Player getPlayer() {
		return player;
	}
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.RectBoardSwingView#onColorChange(es.ucm.fdi.tp.basecode.bgame.model.Piece, java.awt.Color)
	 */
	@Override
	public void onColorChange(Piece player, Color color) {
		super.onColorChange(player, color);
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.RectBoardSwingView#onPlayerModesChange(es.ucm.fdi.tp.basecode.bgame.model.Piece, es.ucm.fdi.tp.practica5.Main.PlayerMode)
	 */
	@Override
	public void onPlayerModesChange(Piece player, PlayerMode mode) {
		super.onPlayerModesChange(player, mode);
		this.first = true;
	}
	
	/**
	 * Handle on game start.
	 *
	 * @param board the board
	 * @param gameDesc the game desc
	 * @param pieces the pieces
	 * @param turn the turn
	 */
	public void onGameStart(final Board board, final String gameDesc, final List<Piece> pieces, final Piece turn){
		super.onGameStart(board, gameDesc, pieces, turn);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleChangeTurn(board, turn);
			}
		});
	}
	
	/**
	 * Handle change turn.
	 *
	 * @param board the board
	 * @param turn the turn
	 */
	private void handleChangeTurn(Board board, Piece turn) {
		if(this.pieces < 6){
		if(getPlayerMode(turn) == PlayerMode.MANUAL)
			if(getLocalPiece()!=null){
				if(getLocalPiece().equals(turn))sendText("Choose an empty cell");
			}
			else sendText("Choose an empty cell");
		}
		else{
			if(getPlayerMode(turn) == PlayerMode.MANUAL)
				if(getLocalPiece()!=null){
					if(getLocalPiece().equals(turn))sendText("Choose a piece");
				}
				else sendText("Choose a piece");
		}
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.SwingView#onChangeTurn(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onChangeTurn(final Board board, final Piece turn) {
		super.onChangeTurn(board, turn);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				handleChangeTurn(board, turn);
			}
		});
	}
}
