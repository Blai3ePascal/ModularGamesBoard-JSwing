package es.ucm.fdi.tp.practica5.swingView.StatusBarPanelComponents;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.Main.PlayerMode;
import es.ucm.fdi.tp.practica5.swingView.StatusBarPanelObserver;

/**
 * The Class PlayerInformation.
 */
public class PlayerInformation extends JTable implements GameObserver, StatusBarPanelObserver{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The table model. */
	private AbstractTableModel tableModel;
	
	/** The pieces. */
	private List<Piece> pieces;
	
	/** The player modes. */
	HashMap<Piece, PlayerMode> playerModes;
	
	/** The line colors. */
	private Map<Integer, Color> lineColors;
	
	/** The colors. */
	Map<Piece, Color> colors;
	
	/** The datos. */
	private String [][] datos;

	/** The repeat. */
	private int[] repeat;

	/** The board. */
	private Board board;
	
	/** The local piece. */
	private final Piece LOCAL_PIECE;
	
	/**
	 * Instantiates a new player information.
	 *
	 * @param g the g
	 * @param pieces the pieces
	 * @param playerModes the player modes
	 * @param colors the colors
	 */
	public PlayerInformation(Observable<GameObserver> g, List<Piece> pieces, HashMap<Piece, PlayerMode> playerModes, 
			Map<Piece, Color> colors, final Piece LOCAL_PIECE){
		
		this.datos = new String[pieces.size()][3];
		this.lineColors = new HashMap<Integer, Color>();
		this.pieces = pieces;
		this.colors = colors;
		this.playerModes = playerModes;
		this.LOCAL_PIECE = LOCAL_PIECE;
		
		g.addObserver(this);
		
	}
	
	/**
	 * True if its a multiview game
	 * @return
	 */
	private boolean multiview(){
		return this.LOCAL_PIECE != null;
	}
	
	/**
	 * Corrects the mode to show it
	 * 
	 * @param p
	 * @return
	 */
	private String showMode(Piece p){
		if(playerModes.get(p).equals(PlayerMode.MANUAL))return "Manual";
		else if(playerModes.get(p).equals(PlayerMode.RANDOM))return "Random";
		else return "Intelligent";
	}
	
	/**
	 * Inicializa los datos de la tabla
	 */
	public void initDatos(){
		for(int i=0;i<pieces.size();i++){lineColors.put(i, colors.get(pieces.get(i)));
			for(int j=0;j<3;j++){
				switch(j){
				case 0: datos[i][j]= pieces.get(i).getId(); break;
				case 1:
					if(multiview()){
						if(this.LOCAL_PIECE.equals(this.pieces.get(i)))datos[i][j] = showMode(pieces.get(i));
					}
					else datos[i][j] = showMode(pieces.get(i));
					break;
				case 2: datos[i][j]= Integer.toString(repeat[i]);break;
				}
			}
		}
	}
	
	/**
	 * Actualiza los datos de la tabla
	 */
	public void refreshTable(){
		int i =0;
		for(Piece p:pieces){
			lineColors.put(i, colors.get(pieces.get(i)));
			if(multiview()){
				if(this.LOCAL_PIECE.equals(this.pieces.get(i)))this.tableModel.setValueAt(showMode(p),i,1);
			}
			else this.tableModel.setValueAt(showMode(p),i,1);
			this.tableModel.setValueAt(repeat[i], i, 2);
			i++;
		}
		this.tableModel.fireTableDataChanged();
	}
	
	/**
	 * Piece count.
	 */
	private void pieceCount(){//actualiza el repeat[], q es el contador de piezas
		this.repeat = new int[this.pieces.size()];
		int i=0;
		for(Piece p: pieces){
			for(int j=0; j < board.getRows();j++)
			for(int k=0; k < board.getCols(); k++)
				if(board.getPosition(j, k)!=null)
				if(board.getPosition(j, k).getId() == p.getId())
					this.repeat[i]++;
			i++;}
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer, int, int)
	 */
	@Override
	public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		Component comp = super.prepareRenderer(renderer, row, col);
		comp.setBackground(lineColors.get(row));
		return comp;
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JTable#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int nRow, int nCol) {
		return false;
	}


	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onGameStart(es.ucm.fdi.tp.basecode.bgame.model.Board, java.lang.String, java.util.List, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		this.board = board;
		pieceCount();
		initDatos();
		String [] cabecera = {"Player", "Mode", "Pieces"};
		this.tableModel = new DefaultTableModel(datos, cabecera);
		this.setModel(tableModel);
		this.setPreferredSize(new Dimension(2,150));
	}


	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onGameOver(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Game.State, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onGameOver(Board board, State state, Piece winner) {}

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
		this.board = board;
		pieceCount();
		refreshTable();
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onChangeTurn(es.ucm.fdi.tp.basecode.bgame.model.Board, es.ucm.fdi.tp.basecode.bgame.model.Piece)
	 */
	@Override
	public void onChangeTurn(Board board, Piece turn){}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.bgame.model.GameObserver#onError(java.lang.String)
	 */
	@Override
	public void onError(String msg){}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.StatusBarPanelObserver#onColorChange(es.ucm.fdi.tp.basecode.bgame.model.Piece, java.awt.Color)
	 */
	@Override
	public void onColorChange(Piece p, Color color) {
		colors.put(p, color);
		refreshTable();
	}

	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.practica5.swingView.StatusBarPanelObserver#onPlayerModesChange(es.ucm.fdi.tp.basecode.bgame.model.Piece, es.ucm.fdi.tp.practica5.Main.PlayerMode)
	 */
	@Override
	public void onPlayerModesChange(Piece player, PlayerMode mode) {
		playerModes.put(player, mode);
		refreshTable();
	}

}
