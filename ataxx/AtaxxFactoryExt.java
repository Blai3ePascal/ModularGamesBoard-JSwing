package es.ucm.fdi.tp.practica5.ataxx;

import javax.swing.SwingUtilities;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica4.ataxx.AtaxxFactory;

/**
 * The Class AtaxxFactoryExt.
 */
@SuppressWarnings("serial")
public class AtaxxFactoryExt extends AtaxxFactory{
	
	/** The rules. */
	GameRules rules = super.gameRules();
	
	/** The game. */
	Game game = new Game(rules);

	/**
	 * Instantiates a new ataxx factory ext.
	 *
	 * @param dimRows the dim rows
	 * @param obstacles the obstacles
	 */
	public AtaxxFactoryExt(Integer dimRows, Integer obstacles) {
		super(dimRows, obstacles);
	}

	/**
	 * Instantiates a new ataxx factory ext.
	 *
	 * @param obstacles the obstacles
	 */
	public AtaxxFactoryExt(Integer obstacles) {
		super(obstacles);
	}

	/**
	 * Instantiates a new ataxx factory ext.
	 */
	public AtaxxFactoryExt() {
		super();
	}
	
	/**
	 * Creates the player.
	 *
	 * @return the player
	 */
	public Player createPlayer(){
		return new AtaxxSwingPlayer();
	}
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.connectn.ConnectNFactory#createSwingView(es.ucm.fdi.tp.basecode.bgame.model.Observable, es.ucm.fdi.tp.basecode.bgame.control.Controller, es.ucm.fdi.tp.basecode.bgame.model.Piece, es.ucm.fdi.tp.basecode.bgame.control.Player, es.ucm.fdi.tp.basecode.bgame.control.Player)
	 */
	@Override
	public void createSwingView(Observable<GameObserver> g, Controller c, Piece viewPiece, Player random, Player ai) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				new AtaxxSwingView(g, c, viewPiece, random, ai);
			}
		});
	}
}
