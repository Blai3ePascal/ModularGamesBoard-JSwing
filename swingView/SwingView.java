package es.ucm.fdi.tp.practica5.swingView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.Main.PlayerMode;
import es.ucm.fdi.tp.practica5.swingView.StatusBarPanel;

/**
 * The Class SwingView.
 */
public abstract class SwingView extends JFrame implements GameObserver, StatusBarPanelObserver{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4096034758275108355L;
	
	/** The ctrl. */
	private Controller ctrl; //Mantenemos una refernecia al controlador
	
	/** The local piece. */
	private final Piece LOCAL_PIECE;//Si es null, todos juegan en esta vista. Es importante para decidir si desactivamos la vista, hacer un movimiento automático, etc.
	
	/** The turn. */
	private Piece turn;
	
	/** The board. */
	private Board board;
	
	/** The pieces. */
	private List<Piece> pieces;
	
	/** The piece colors. */
	private HashMap <Piece, Color> pieceColors = new HashMap<Piece, Color>();//Los colores de las fichas
	
	/** The player modes. */
	private HashMap <Piece, PlayerMode> playerModes;//Los modos de los jugadores
	
	/** The players. */
	private Player[] players;
	
	/** The sbp. */
	private StatusBarPanel sbp;
	
	/** The board comp. */
	private BoardComponent boardComp;
	
	/** The repeat. */
	int repeat[];
	
	/** The rand player. */
	private Player randPlayer;
	
	/** The ai player. */
	private Player aiPlayer;
	
	/** The observer. */
	private Observable<GameObserver> observer;
	
	/** The img. */
	private ImageIcon img;

	
	/**
	 * Instantiates a new swing view.
	 *
	 * @param g the g
	 * @param c the c
	 * @param localPiece the local piece
	 * @param randPlayer the rand player
	 * @param aiPlayer the ai player
	 */
	public SwingView(Observable<GameObserver> g,  Controller c, Piece localPiece, Player randPlayer, Player aiPlayer){
		super();
		this.observer = g;
		this.ctrl = c;
		this.randPlayer = randPlayer;
		this.aiPlayer = aiPlayer;
		this.observer = g;
		this.LOCAL_PIECE = localPiece;

		initFrame();
		initPlayers(randPlayer, aiPlayer);
		g.addObserver(this);//Registrarse como observador
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	/**
	 * Inits the gui.
	 */
	private void initGUI(){
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.sbp = new StatusBarPanel(observer, pieces, pieceColors, playerModes, ctrl, this, randPlayer, aiPlayer);
		mainPanel.add(this.sbp ,BorderLayout.EAST);
		
		this.setContentPane(mainPanel);
	}
	
	/**
	 * Inits the board.
	 */
	private void initBoard() {
		if(boardComp == null) initBoardGui();
	}

	/**
	 * Inits the frame.
	 */
	private void initFrame(){
		this.setSize(1050, 800);
		this.setResizable(false);
		this.setLocationByPlatform(true);
		this.img = new ImageIcon("fdi.gif");
		this.setIconImage(img.getImage());
	}
	
	/**
	 * Sets the board area.
	 *
	 * @param c the new board area
	 */
	/*
	 * Las subclases usan éstos métodos 
	 * para colocar el componente del tablero, 
	 * añadir texto a la area de información, etc.
	 * */
	final protected void setBoardArea(JComponent c) {
		
	} 
	
	/**
	 * Gets the board.
	 *
	 * @return the board
	 */
	final protected Board getBoard() { 
		return board; 
	} 	
	
	/**
	 * Inits the player types.
	 */
	private void initPlayerTypes(){
		playerModes = new HashMap <Piece, PlayerMode>();
		for(Piece p: this.pieces) playerModes.put(p, PlayerMode.MANUAL);
	}
	
	/**
	 * Inits the players.
	 *
	 * @param randPlayer the rand player
	 * @param aiPlayer the ai player
	 */
	private void initPlayers(Player randPlayer, Player aiPlayer){
		this.players = new Player[2];
		players[0] = randPlayer;
		if(aiPlayer!=null)players[1] = aiPlayer;
	}
	
	/**
	 * Gets the piece color.
	 *
	 * @param p the p
	 * @return the piece color
	 */
	/*
	 *Las subclases usan estos métodos
	 *para consultar y cambiar el color de las fichas
	 */
	final protected Color getPieceColor(Piece p) { 
		return pieceColors.get(p);
	} 
	
	/**
	 * Gets the local piece.
	 *
	 * @return the local piece
	 */
	final protected Piece getLocalPiece(){
		return this.LOCAL_PIECE;
	}
	
	/**
	 * Adds the msg.
	 *
	 * @param msg the msg
	 */
	final protected void addMsg(String msg) {
		this.sbp.newMsg(msg);
	}
	
	/**
	 * Sets the play.
	 *
	 * @param mode the new play
	 */
	protected void setPlay(boolean mode){//si true, hace un random move, y si false un intelligent(no implementado)
		if(mode)ctrl.makeMove(randPlayer);
	}
	
	/**
	 * Decide make manual move.
	 *
	 * @param manualPlayer the manual player
	 */
	/*
	 * Las subclases usan estos métodos para hacer un movimiento manual
	 * */
	final protected void decideMakeManualMove(Player manualPlayer) {
		if(playerModes.get(turn)==PlayerMode.MANUAL){
			ctrl.makeMove(manualPlayer);
		}
	}
	
	/**
	 * Decide make automatic move.
	 */
	//Llamamos a este mï¿½todo para hacer un movimiento automático, si es necesario
	private void decideMakeAutomaticMove() {
		if(playerModes.get(turn)==PlayerMode.RANDOM){
			setPlay(true);
		}
	}
	
	/**
	 * Inits the piece colors.
	 */
	private void initPieceColors(){
		for(Piece p : this.pieces)
			pieceColors.put(p, Utils.randomColor());
	}
	
	/**
	 * Inits the gamedesc.
	 *
	 * @param gameDesc the game desc
	 */
	private void initGamedesc(final String gameDesc){
		if(!multiview())this.setTitle("BoardGames " + gameDesc);
		else this.setTitle("BoardGames " + gameDesc + " ("+this.LOCAL_PIECE.getId()+")");
	}

	/**
	 * Decides Activate or deActivate the board
	 * 
	 * @param turn
	 */
	private void decidesActivateBoard(Piece turn){
		if(multiview()){
			if(this.LOCAL_PIECE.equals(turn))activateBoard();
			else deActivateBoard();
		}
		else activateBoard();
	}
	
	/**
	 * Gets the player mode.
	 *
	 * @param p the p
	 * @return the player mode
	 */
	protected final PlayerMode getPlayerMode(Piece p) {
		return playerModes.get(p);
	}
	
	/**
	 * Send text.
	 *
	 * @param cookies the cookies
	 */
	public void sendText(String cookies){
		this.sbp.newMsg(cookies);
	}
	
	/**
	 * Send text.
	 *
	 * @param cookies the cookies
	 */
	public void sendFlatText(String cookies){
		this.sbp.newFlatMsg(cookies);
	}
	
	/**
	 * True if its a multiview game
	 * @return
	 */
	private boolean multiview(){
		return this.LOCAL_PIECE != null;
	}
	
	//LISTENERS
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onGameStart(es.ucm.fdi.tp.basecode.bgame.model.Board, java.lang.String, java.util.List, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onGameStart(final Board board, final String gameDesc, final List<Piece> pieces, final Piece turn) {
		this.board = board;
		this.pieces = pieces;
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				HandleOnGameStart(board, gameDesc, pieces, turn);
		;}});
	}

	/**
	 * Handle on game start.
	 *
	 * @param board the board
	 * @param gameDesc the game desc
	 * @param pieces the pieces
	 * @param turn the turn
	 */
	//Métodos que gestionan cada listener
	private void HandleOnGameStart(final Board board, final String gameDesc, final List<Piece> pieces, final Piece turn){
		this.pieces = pieces;
		this.board = board;
		initPieceColors();
		initPlayerTypes();
		initGamedesc(gameDesc);
		this.turn = turn;
		initGUI();//Inicializar la GUI
		initBoard();
		this.setVisible(true);
		decidesActivateBoard(turn);
	}
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onGameOver(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Game.State, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				HandleOnGameOver(board, state, winner);
			}});
	}

	/**
	 * Handle on game over.
	 *
	 * @param board the board
	 * @param state the state
	 * @param winner the winner
	 */
	private void HandleOnGameOver(Board board, State state, Piece winner){
		if(state == State.Stopped) {
			this.setVisible(false);
			this.dispose();
		}
	}
	
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onMoveStart(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onMoveStart(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() { 
				HandleOnMoveStart(board, turn);
			}
		});
	}

	/**
	 * Handle on move start.
	 *
	 * @param board the board
	 * @param turn the turn
	 */
	private void HandleOnMoveStart(Board board, Piece turn){}
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onMoveEnd(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Piece, boolean)
	 */
	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		redrawBoard();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				HandleOnMoveEnd(board, turn, success);
			}
		});
	}

	/**
	 * Handle on move end.
	 *
	 * @param board the board
	 * @param turn the turn
	 * @param success the success
	 */
	private void HandleOnMoveEnd(Board board, Piece turn, boolean success){
	}
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onChangeTurn(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onChangeTurn(Board board, Piece turn) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				HandleOnChangeTurn(board, turn);
			}
		});
	}

	/**
	 * Handle on change turn.
	 *
	 * @param board the board
	 * @param turn the turn
	 */
	private void HandleOnChangeTurn(Board board, Piece turn){
		this.turn = turn;
		if(multiview())decidesActivateBoard(turn);
		decideMakeAutomaticMove();
		if(playerModes.get(turn)==PlayerMode.RANDOM)Utils.sleep(300);
	}
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onError(java.lang.String)
	 */
	@Override
	public void onError(String msg) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { 
				HandleOnError(msg);
		}});
	}
	
	/**
	 * Handle on error.
	 *
	 * @param msg the msg
	 */
	private void HandleOnError(String msg){
		if(multiview()){
			if(this.LOCAL_PIECE.equals(turn))JOptionPane.showConfirmDialog(null, msg, "GAME ERROR", JOptionPane.ERROR_MESSAGE);
		}
		else JOptionPane.showConfirmDialog(null, msg, "GAME ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.StatusBarPanelObserver#onColorChange(es.ucm.fdi.tp.basecode.bgame.model.Piece, java.awt.Color)
	 */
	@Override
	public void onColorChange(Piece player, Color newColor) {
		redrawBoard();
	}
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.StatusBarPanelObserver#onPlayerModesChange(es.ucm.fdi.tp.basecode.bgame.model.Piece, es.ucm.fdi.tp.practica5.Main.PlayerMode)
	 */
	@Override
	public void onPlayerModesChange(Piece player, PlayerMode newMode) {
		if(this.turn.equals(player))ctrl.makeMove(randPlayer);
		playerModes.put(player, newMode);
	}
	
	//ABSTACT
	
	/**
	 * Inits the board gui.
	 * 
	 * 
	 * Usamos para pedir a las subclases inicializar la parte gráfica 
	 * del tablero, (re)dibujar el tablero, 
	 * (des)activar el tablero, etc.
	 */
	protected abstract void initBoardGui();
	
	/**
	 * Activate board.
	 */
	protected abstract void activateBoard();
	
	/**
	 * De activate board.
	 */
	protected abstract void deActivateBoard();
	
	/**
	 * Redraw board.
	 */
	protected abstract void redrawBoard();
}
