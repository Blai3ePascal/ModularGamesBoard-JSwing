package es.ucm.fdi.tp.practica5.connectn;

import javax.swing.SwingUtilities;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNFactory;

/**
 * The Class ConnectNFactoryExt.
 */
@SuppressWarnings("serial")
public class ConnectNFactoryExt extends ConnectNFactory{

	/**
	 * Instantiates a new connect n factory ext.
	 *
	 * @param dimRows the dim rows
	 */
	public ConnectNFactoryExt(Integer dimRows) {
		super(dimRows);
	}

	/**
	 * Instantiates a new connect n factory ext.
	 */
	public ConnectNFactoryExt() {
		super();
	}
	
	/**
	 * Creates the player.
	 *
	 * @return the player
	 */
	public Player createPlayer(){
		return new ConnectNSwingPlayer();
	}
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.connectn.ConnectNFactory#createSwingView(es.ucm.fdi.tp.basecode.bgame.model.Observable, es.ucm.fdi.tp.basecode.bgame.control.Controller, es.ucm.fdi.tp.basecode.bgame.model.Piece, es.ucm.fdi.tp.basecode.bgame.control.Player, es.ucm.fdi.tp.basecode.bgame.control.Player)
	 */
	@Override
	public void createSwingView(Observable<GameObserver> g, Controller c, Piece viewPiece, Player random, Player ai) {

		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				new ConnectNSwingView(g, c, viewPiece, random, ai);
			}
		});
	}
}
