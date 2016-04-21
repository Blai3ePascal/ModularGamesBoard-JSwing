package es.ucm.fdi.tp.practica5.swingView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.Main.PlayerMode;

/**
 * The Class RectBoardSwingView.
 */
@SuppressWarnings("serial")
public abstract class RectBoardSwingView extends SwingView{
	
	/** The board comp. */
	private BoardComponent boardComp;
	
	/** The board. */
	private Board board;
	
	/** The observer. */
	private Observable<GameObserver> observer;

	/**
	 * Instantiates a new rect board swing view.
	 *
	 * @param g the g
	 * @param c the c
	 * @param localPiece the local piece
	 * @param randPlayer the rand player
	 * @param aiPlayer the ai player
	 */
	public RectBoardSwingView(Observable<GameObserver> g, Controller c, Piece localPiece, Player randPlayer, Player aiPlayer) {
		super(g, c, localPiece, randPlayer, aiPlayer);
		this.observer = g;
		this.board = super.getBoard();
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.SwingView#initBoardGui()
	 */
	/*
	 * Inicializa la parte grï¿½fica del tablero cuando SwingView lo pide: 
	 * construir el componente y pasarlo a SwingView llamando a setBoardArea.
	 */
	@Override
	protected void initBoardGui() {
		
		JPanel tablero = new JPanel();
		tablero.setBackground(Color.darkGray);
		
		boardComp = new BoardComponent(board.getRows(),board.getCols(), board, this){
			/*
			 * Así se pueden conectar los métodos abstractos de la clase BoardComponent 
			 * con la información almacenada en SwingView, y también pasar los eventos 
			 * del ratón a la subclase.
			 * */
			
			@Override
			protected Color getPiceColor(Piece p) {
				//Get the color from the colours table, and if not
				//available (ej: for obstacles), set it to have a color.
				return null;
			}

			@Override
			protected boolean isPlayerPiece(Piece p) {
				// return true if p is a player piece, false if not.
				return false;
			}

			@Override
			protected void mouseClicked(int row, int col, int mouseButton) {}

			@Override
			public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
				handleOnGameStart(board,gameDesc, pieces, turn);
			}
			
			@Override
			public void onGameOver(Board board, State state, Piece winner) {}

			@Override
			public void onMoveStart(Board board, Piece turn) {}

			@Override
			public void onMoveEnd(Board board, Piece turn, boolean success) {}

			@Override
			public void onChangeTurn(Board board, Piece turn) {}

			@Override
			public void onError(String msg) {}
		};
		setBoardArea(boardComp);
		tablero.setLayout(new BorderLayout());
		tablero.add(boardComp, BorderLayout.CENTER);
		this.add(tablero, BorderLayout.CENTER);
		getContentPane().add(boardComp, BorderLayout.CENTER);
		observer.addObserver(boardComp);
	}

	/**
	 * Handle on game start.
	 *
	 * @param board the board
	 * @param gameDesc the game desc
	 * @param pieces the pieces
	 * @param turn the turn
	 */
	private void handleOnGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn){
		this.board = board;
	}
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.SwingView#activateBoard()
	 */
	@Override
	protected void activateBoard() {
		this.boardComp.setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.SwingView#onColorChange(es.ucm.fdi.tp.basecode.bgame.model.Piece, java.awt.Color)
	 */
	@Override
	public void onColorChange(Piece player, Color color) {
		super.onColorChange(player, color);
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.SwingView#onPlayerModesChange(es.ucm.fdi.tp.basecode.bgame.model.Piece, es.ucm.fdi.tp.practica5.Main.PlayerMode)
	 */
	@Override
	public void onPlayerModesChange(Piece player, PlayerMode mode) {
		super.onPlayerModesChange(player, mode);
	}
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.SwingView#deActivateBoard()
	 */
	@Override
	protected void deActivateBoard() {
		this.boardComp.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.SwingView#redrawBoard()
	 */
	@Override
	protected void redrawBoard() {
		boardComp.redraw(board);
		this.repaint();
	}
	
	/**
	 * Sets the piece.
	 *
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 * @param but the but
	 */
	protected void setPiece(int x, int y, int width, int height, int but){
		int ancho = width;
		int col = 0;
		while(ancho < x){
			ancho = ancho + width;
			col++;
		}
		int alto = height;
		int row = 0;
		while(alto < y){
			alto = alto + height;
			row++;
		}
		handleMouseClick(row, col, but);
	}
	
	/**
	 * Handle mouse click.
	 *
	 * @param row the row
	 * @param col the col
	 * @param mouseButton the mouse button
	 */
	protected abstract void handleMouseClick(int row, int col, int mouseButton);
	
	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	protected abstract Player getPlayer();


}
