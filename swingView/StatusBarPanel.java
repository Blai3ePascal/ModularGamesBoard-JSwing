package es.ucm.fdi.tp.practica5.swingView;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.Main.PlayerMode;
import es.ucm.fdi.tp.practica5.swingView.SwingView;
import es.ucm.fdi.tp.practica5.swingView.StatusBarPanelComponents.*;

/**
 * The Class StatusBarPanel.
 */
@SuppressWarnings("serial")
public class StatusBarPanel extends JPanel implements GameObserver, StatusBarPanelObserver{
	
	/** The pieces. */
	private List<Piece> pieces;
	
	/** The colors. */
	private Map<Piece, Color> colors;
	
	/** The player modes. */
	private HashMap <Piece, PlayerMode> playerModes;
	
	/** The sv. */
	private SwingView sv;
	
	/** The local piece. */
	private final Piece LOCAL_PIECE;
	
	/** The ctrl. */
	private Controller ctrl;
	
	/** The text panel. */
	private StatusMessages textPanel;
	
	/** The player info. */
	private PlayerInformation playerInfo;
	
	/** The other options. */
	private OtherOptions otherOptions;
	
	/** The internal observers. */
	private LinkedList<GameObserver> internalObservers = new LinkedList<GameObserver>();
	
	/** The random player. */
	private Player randomPlayer;
	
	/** The ai player. */
	private Player aiPlayer;
	
	/** The observer. */
	private Observable<GameObserver> observer;
	
	/**
	 * Instantiates a new status bar panel.
	 *
	 * @param g the g
	 * @param pieces the pieces
	 * @param pieceColors the piece colors
	 * @param playerModes the player modes
	 * @param ctrl the ctrl
	 * @param sv the sv
	 * @param randomPlayer the random player
	 * @param aiPlayer the ai player
	 */
	public StatusBarPanel(Observable<GameObserver> g, List<Piece> pieces, HashMap<Piece, Color> pieceColors, HashMap<Piece, PlayerMode> playerModes,
			Controller ctrl, SwingView sv, Player randomPlayer, Player aiPlayer){
		this.observer = g;
		this.pieces = pieces;
		this.colors = pieceColors;
		this.playerModes = playerModes;
		this.sv = sv;
		this.LOCAL_PIECE = sv.getLocalPiece();
		this.ctrl = ctrl;
		this.randomPlayer = randomPlayer;
		this.aiPlayer = aiPlayer;
		
		initGUI();
		g.addObserver(this);
	}
		
	/**
	 * Inits the gui.
	 */
	private void initGUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(250, 800));
		addTextBox();
		addPlayerInfoTable();
		addOtherOptions();
		otherOptions.addObserver(this.sv);
		otherOptions.addObserver(this.playerInfo);
		otherOptions.addObserver(this);
	}

	/**
	 * Adds the other options.
	 */
	private void addOtherOptions() {
		otherOptions = new OtherOptions(observer, pieces, playerModes,  colors, ctrl, LOCAL_PIECE, randomPlayer, aiPlayer);
		otherOptions.setSize(150, 150);
		add(otherOptions);
		this.internalObservers.add(otherOptions);
	}

	/**
	 * Adds the player info table.
	 */
	private void addPlayerInfoTable() {
		playerInfo = new PlayerInformation(observer, this.pieces, this.playerModes, this.colors, LOCAL_PIECE);
		JScrollPane info = new JScrollPane(playerInfo);
		info.setPreferredSize(new Dimension(100,100));
		info.setBorder(javax.swing.BorderFactory.createTitledBorder("Player Information"));
		add(info);
	}

	/**
	 * Adds the text box.
	 */
	private void addTextBox() {
		textPanel = new StatusMessages();
		JScrollPane scroll = new JScrollPane(textPanel);
		scroll.setPreferredSize(new Dimension(200, 200));
		add(scroll);
	}

	/**
	 * New msg.
	 *
	 * @param msg the msg
	 */
	protected void newMsg(String msg){
		this.textPanel.newMsg(msg);
	}
	
	/**
	 * New flat msg.
	 *
	 * @param msg the msg
	 */
	protected void newFlatMsg(String msg) {
		this.textPanel.newFlatMsg(msg);
	}
	/**
	 * Refresh colors.
	 *
	 * @param colors the colors
	 */
	public void refreshColors(Map<Piece, Color> colors) {
		this.colors = colors;
		playerInfo.initDatos();
	}

	/**
	 * True if its a multiview game
	 * @return
	 */
	private boolean multiview(){
		return this.LOCAL_PIECE != null;
	}
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onGameStart(es.ucm.fdi.tp.basecode.bgame.model.Board, java.lang.String, java.util.List, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		newFlatMsg("Game started: " + gameDesc);
		if(multiview()){
			if(!LOCAL_PIECE.equals(turn)) newFlatMsg("\n Turn for " + turn.getId());
			else newFlatMsg("\n Your turn!");
		}
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onGameOver(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Game.State, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		if(winner!=null){
			if(winner.equals(LOCAL_PIECE)) newFlatMsg("\n GAME OVER: You won");
			else		newFlatMsg("\n GAME OVER: " +winner.getId() + " wins");
		}
		else newMsg("GAME OVER: Draw");
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onChangeTurn(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onChangeTurn(Board board, Piece turn) {
		if(turn.equals(LOCAL_PIECE)) newFlatMsg("\n Your turn!");
		else newFlatMsg("\n Turn for " + turn);
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onMoveStart(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onMoveStart(Board board, Piece turn) {}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onMoveEnd(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Piece, boolean)
	 */
	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onError(java.lang.String)
	 */
	@Override
	public void onError(String msg) {}

	@Override
	public void onColorChange(Piece turn, Color color) {
		if(multiview()){
			if(turn.equals(LOCAL_PIECE)) newFlatMsg("You have changed your color");
			else newFlatMsg(turn + " has changed his color");
		}
		else newFlatMsg(turn + " has changed his color");
	}

	@Override
	public void onPlayerModesChange(Piece turn, PlayerMode mode) {
		if(multiview()){
			if(turn.equals(LOCAL_PIECE)) newFlatMsg("You have changed your mode");
			else newFlatMsg(turn + " has changed his mode");
		}
		else newFlatMsg(turn + " has changed his mode");
	}
}
