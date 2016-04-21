package es.ucm.fdi.tp.practica5.attt;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.basecode.attt.AdvancedTTTFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * The Class AdvancedTTTFactoryExt.
 */
@SuppressWarnings("serial")
public class AdvancedTTTFactoryExt extends AdvancedTTTFactory{
	
	/* (non-Javadoc)
	 * @see es.ucm.fdi.tp.basecode.connectn.ConnectNFactory#createSwingView(es.ucm.fdi.tp.basecode.bgame.model.Observable, es.ucm.fdi.tp.basecode.bgame.control.Controller, es.ucm.fdi.tp.basecode.bgame.model.Piece, es.ucm.fdi.tp.basecode.bgame.control.Player, es.ucm.fdi.tp.basecode.bgame.control.Player)
	 */
	@Override
	public void createSwingView(Observable<GameObserver> g, Controller c, Piece viewPiece, Player random, Player ai) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				new ATTTSwingView(g, c, viewPiece, random, ai);
			}
		});
	}
}
