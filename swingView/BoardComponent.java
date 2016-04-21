package es.ucm.fdi.tp.practica5.swingView;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.Main.PlayerMode;

/**
 * The Class BoardComponent.
 */
public abstract class BoardComponent extends JPanel implements GameObserver, StatusBarPanelObserver{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The  cell height. */
	private int _CELL_HEIGHT = 51;
	
	/** The  cell width. */
	private int _CELL_WIDTH = 51;
	
	/** The Constant SHADOW. */
	protected static final Color NINTENDO = new Color(51,51,51,100);

	/** The rows. */
	private int rows;
	
	/** The cols. */
	private int cols;
	
	/** The rb. */
	private RectBoardSwingView rb;
	
	/** The board. */
	private Color[][] board;
	
	/** The b. */
	Board b;
	
	/**
	 * Instantiates a new board component.
	 *
	 * @param rows the rows
	 * @param cols the cols
	 * @param b the b
	 * @param rb the rb
	 */
	public BoardComponent(int rows, int cols, Board b, RectBoardSwingView rb) {
		this.b = b;
		this.rb = rb;
		initBoard(rows, cols);
		initGUI();
	}

	/**
	 * Inits the board.
	 *
	 * @param rows the rows
	 * @param cols the cols
	 */
	private void initBoard(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		
		board = new Color[rows][cols];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++){
			if (b.getPosition(i, j)!=null){
				if(b.getPosition(i, j).getId().equals("*"))
					board[i][j] = Color.DARK_GRAY;
				else board[i][j] = rb.getPieceColor(b.getPosition(i, j));
			}
		}
		
	}

	/**
	 * Inits the gui.
	 */
	private void initGUI() {

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) {
				rb.setPiece(e.getX(), e.getY(), _CELL_WIDTH,_CELL_HEIGHT, e.getButton());
			}
		});
		this.setSize(new Dimension(rows * _CELL_HEIGHT, cols * _CELL_WIDTH));//Cells size
		this.setBackground(Color.darkGray);
		
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {//Draw every cell
		super.paintComponent(g);
		_CELL_WIDTH = this.getWidth() / cols;
		_CELL_HEIGHT = this.getHeight() / rows;

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				drawCell(i, j, g);
	}

	/**
	 * Draw cell.
	 *
	 * @param row the row
	 * @param col the col
	 * @param g the g
	 */
	private void drawCell(int row, int col, Graphics g) {
		int x = col * _CELL_WIDTH;
		int y = row * _CELL_HEIGHT;
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 2, y + 2, _CELL_WIDTH - 4, _CELL_HEIGHT - 4);//Draw the cell
		
		if(b.getPosition(row, col)!=null){
			if(b.getPosition(row, col).getId().equals("*")){
				g.setColor(board[row][col]);
				g.fillRect(x, y, _CELL_WIDTH, _CELL_HEIGHT);//Draw the obstacle
		
				g.setColor(Color.DARK_GRAY);
				g.drawRect(x, y, _CELL_WIDTH, _CELL_HEIGHT);//Draw the obstacle border
			}
			else{
				g.setColor(NINTENDO);
				g.fillOval(x + 25, y + 25, _CELL_WIDTH - 40, _CELL_HEIGHT - 40);//Draw the piece shadow
				
				g.setColor(board[row][col]);
				g.fillOval(x + 20, y + 20, _CELL_WIDTH - 40, _CELL_HEIGHT - 40);//Draw the piece
				
				g.setColor(Color.black);
				g.drawOval(x + 20, y + 20, _CELL_WIDTH - 40, _CELL_HEIGHT - 40);//Draw the piece black border
			}
		}
	}

	/**
	 * Sets the board size.
	 *
	 * @param rows the rows
	 * @param cols the cols
	 */
	public void setBoardSize(int rows, int cols) {
		initBoard(rows, cols);
		repaint();
	}

	/**
	 * Redraw.
	 *
	 * @param b the b
	 */
	/*
	 * Desde fuera podemos pedir a este componente
	 * de redibujar el tablero, normalmente desde el método redrawBoard
	 */
	public void redraw(Board b){
		this.b = b;
		initBoard(rows, cols);
	}

	@Override
	public void onColorChange(Piece player, Color color){}

	@Override
	public void onPlayerModesChange(Piece player, PlayerMode mode) {}

	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {}

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

	/**
	 * Gets the pice color.
	 *
	 * @param p the p
	 * @return the pice color
	 */
	/*
	 * Cuando dibujas el tablero, usa estos métodos para
	 * consultar el color o el tipo (jugador o obstï¿½culo) de
	 * una ficha. Quien implementa estos métodos se
	 * encarga de conectar los con la información almacenada en SwingView
	 */
	protected abstract Color getPiceColor(Piece p);
	
	/**
	 * Checks if is player piece.
	 *
	 * @param p the p
	 * @return true, if is player piece
	 */
	protected abstract boolean isPlayerPiece(Piece p);
	
	/**
	 * Mouse clicked.
	 *
	 * @param row the row
	 * @param col the col
	 * @param mouseButton the mouse button
	 */
	//Cuando se produce un click sobre el tablero, pasa toda la información a este método. Quien lo implementa decide que hacer con este click
	protected abstract void mouseClicked(int row, int col, int mouseButton);
}
