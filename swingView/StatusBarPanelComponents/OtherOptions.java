package es.ucm.fdi.tp.practica5.swingView.StatusBarPanelComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.Main.PlayerMode;
import es.ucm.fdi.tp.practica5.swingView.StatusBarPanelObserver;

/**
 * The Class OtherOptions.
 */
public class OtherOptions extends JPanel implements Observable<StatusBarPanelObserver>, GameObserver{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The modes. */
	String [] modes = {"Manual", "Automatic"};

	/** The piece colors panel. */
	private JPanel pieceColorsPanel;
	
	/** The player modes panel. */
	private JPanel playerModesPanel;
	
	/** The automatic moves panel. */
	private JPanel automaticMovesPanel;
	
	/** The game ops panel. */
	private JPanel gameOpsPanel;
	
	/** The pieces. */
	private List<Piece> pieces;
	
	/** The colors. */
	private Map<Piece, Color> colors;
	
	/** The border. */
	private Dimension border;
	
	/** The ctrl. */
	private Controller ctrl;
	
	/** The local piece. */
	private final Piece LOCAL_PIECE;
	
	/** The player modes. */
	private HashMap<Piece, PlayerMode> playerModes;
	
	/** The observers. */
	private ArrayList<StatusBarPanelObserver> observers = new ArrayList<StatusBarPanelObserver>();

	/** The random player. */
	private Player randomPlayer;

	/** The ai player. */
	@SuppressWarnings("unused")
	private Player aiPlayer;

	/** The restart. */
	private JButton restart;
	
	/** The random. */
	private JButton random;
	
	/** The combo modes. */
	@SuppressWarnings("rawtypes")
	private JComboBox comboModes;

	
	
	/**
	 * Instantiates a new other options.
	 *
	 * @param g the g
	 * @param pieces the pieces
	 * @param playerModes the player modes
	 * @param colors the colors
	 * @param ctrl the ctrl
	 * @param lOCAL_PIECE the l oca l_ piece
	 * @param randomPlayer the random player
	 * @param aiPlayer the ai player
	 */
	public OtherOptions(Observable<GameObserver> g, List<Piece> pieces, HashMap<Piece, 
			PlayerMode> playerModes, Map<Piece, Color> colors, Controller ctrl,
			Piece lOCAL_PIECE, Player randomPlayer, Player aiPlayer){
		
		this.pieces = pieces;
		this.LOCAL_PIECE = lOCAL_PIECE;
		this.playerModes = playerModes;
		this.colors = colors;
		this.border = new Dimension(40, 10);
		this.ctrl = ctrl;
		this.randomPlayer = randomPlayer;
		this.aiPlayer = aiPlayer;
		
		this.pieceColorsPanel = new JPanel();
		this.playerModesPanel = new JPanel();
		this.automaticMovesPanel = new JPanel();
		this.gameOpsPanel = new JPanel();

		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		initPieceCols();
		initPlayerModes();
		initPlayerMoves();
		initGameControls();
		
		this.add(pieceColorsPanel);
		this.add(playerModesPanel);
		this.add(automaticMovesPanel);
		this.add(gameOpsPanel);

		g.addObserver(this);
	}

	/**
	 * Inits the piece cols.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initPieceCols(){
		//ComboBox de los colores
		JComboBox pieceCols = new JComboBox(pieces.toArray());
		JButton chooseColor = new JButton("Choose Color");
		pieceColorsPanel.add(pieceCols);
		pieceColorsPanel.add(chooseColor);
		pieceColorsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Piece Colors"));
		pieceColorsPanel.setPreferredSize(this.border);
		
		
		chooseColor.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					int n = pieceCols.getSelectedIndex();
					Piece selectedPiece = pieces.get(n);
					ColorChooser c = new ColorChooser(new JFrame(), "Choose Line Color", colors.get(n));
					Color selectedColor = c.getColor();
					
					if (c.getColor() != null) {
						colors.put(selectedPiece, selectedColor);
						fireColorChanged(selectedPiece, selectedColor);
					}
				} catch (Exception _e) {
				}
			}
		});
	}
	
	/**
	 * Fire color changed.
	 *
	 * @param piece the piece
	 * @param color the color
	 */
	private void fireColorChanged(Piece piece, Color color) {
		for (StatusBarPanelObserver o : observers) {
			o.onColorChange(piece, color);
		}
	}
	
	/**
	 * Inits the player modes.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initPlayerModes(){
		//ComboBox de los modos de juego
		JComboBox pieceList = createComboBox();
		this.comboModes = new JComboBox(modes);
		JButton set = new JButton("Set");
		playerModesPanel.add(pieceList);
		playerModesPanel.add(comboModes);
		playerModesPanel.add(set);
		playerModesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Player Modes"));
		playerModesPanel.setPreferredSize(this.border);
		
		set.addActionListener(new ActionListener(){
			@Override	public void actionPerformed(ActionEvent arg0) {
							Piece selectedPiece = (Piece)pieceList.getSelectedItem();
							PlayerMode selectedMode = (changeMode((Piece)pieceList.getSelectedItem(), (String) comboModes.getSelectedItem()));
							
							playerModes.put(selectedPiece, selectedMode);
							fireModeChanged(selectedPiece, selectedMode);}
			});
	}
	
	/**
	 * Creates the combo box.
	 *
	 * @return the j combo box
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox createComboBox(){
		if(this.LOCAL_PIECE!=null){
			Piece[] p = new Piece[]{LOCAL_PIECE};
			return new JComboBox(p);
		}else return new JComboBox(pieces.toArray());
	}
	
	/**
	 * Change mode.
	 *
	 * @param p the p
	 * @param mode the mode
	 * @return the player mode
	 */
	private PlayerMode changeMode(Piece p, String mode){
		if(mode.equals("Automatic"))return PlayerMode.RANDOM;
		else return PlayerMode.MANUAL;
	}
	
	/**
	 * Fire mode changed.
	 *
	 * @param p the p
	 * @param mode the mode
	 */
	private void fireModeChanged(Piece p, PlayerMode mode) {
		for (StatusBarPanelObserver o : observers) {
			o.onPlayerModesChange(p, mode);
		}
	}
	
	/**
	 * Inits the player moves.
	 */
	private void initPlayerMoves(){
		
		automaticMovesPanel.setPreferredSize(this.border);
		automaticMovesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Automatic Moves"));
		
		//Botón Random
		this.random = new JButton("Random");
		random.addActionListener(new ActionListener(){
	
			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.makeMove(randomPlayer);
			}
			
		});
		automaticMovesPanel.add(random);
		
		//Botón intelligent
		JButton intelligent = new JButton("Intelligent");
		intelligent.setEnabled(false);
		automaticMovesPanel.add(intelligent);
		
	}
	
	/**
	 * Inits the game controls.
	 */
	private void initGameControls(){
		//Botón quit
		JButton quit = new JButton("Quit");
		quit.addActionListener(new ActionListener(){
			@Override	
			public void actionPerformed(ActionEvent arg0) {
				 int confirmed = JOptionPane.showConfirmDialog(null, 
					        "Are you sure you want to exit the program?", "Exit Program Message Box",
					        JOptionPane.YES_NO_OPTION);
	
					    if (confirmed == JOptionPane.YES_OPTION) {
					      ctrl.stop();
					    }
			}
		});
		gameOpsPanel.add(quit);
		
		//Botón restart
		this.restart = new JButton("Restart");
		restart.addActionListener(new ActionListener(){
	
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 ctrl.restart();
			}
			
		});
		gameOpsPanel.add(restart);
	}	
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onGameStart(es.ucm.fdi.tp.basecode.bgame.model.Board, java.lang.String, java.util.List, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		if(this.LOCAL_PIECE!=null)if(this.LOCAL_PIECE!=turn)this.random.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onGameOver(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Game.State, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onMoveStart(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onMoveStart(Board board, Piece turn){
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onMoveEnd(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Piece, boolean)
	 */
	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		this.restart.setEnabled(true);
		this.random.setEnabled(true);
		this.comboModes.setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onChangeTurn(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onChangeTurn(Board board, Piece turn){
		if(playerModes.get(turn)==PlayerMode.RANDOM){ 
			this.restart.setEnabled(false);
			this.random.setEnabled(false);
			this.comboModes.setEnabled(false);
		}
		if(this.LOCAL_PIECE!=null)if(this.LOCAL_PIECE!=turn)this.random.setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onError(java.lang.String)
	 */
	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.Observable#addObserver(java.lang.Object)
	 */
	@Override
	public void addObserver(StatusBarPanelObserver o) {
		observers.add(o);
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.Observable#removeObserver(java.lang.Object)
	 */
	@Override
	public void removeObserver(StatusBarPanelObserver o) {
		observers.remove(o);
	}
}

