package es.ucm.fdi.tp.practica5.swingView;

import java.awt.Color;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.practica5.Main.PlayerMode;

/**
 * An asynchronous update interface for receiving notifications
 * about StatusBarPanel information as the StatusBarPanel is constructed.
 */
public interface StatusBarPanelObserver {
	
	/**
	 * This method is called when information about an StatusBarPanel
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param player the player
	 * @param color the color
	 */
	public void onColorChange(Piece player, Color color);
	
	/**
	 * This method is called when information about an StatusBarPanel
	 * which was previously requested using an asynchronous
	 * interface becomes available.
	 *
	 * @param player the player
	 * @param mode the mode
	 */
	public void onPlayerModesChange(Piece player, PlayerMode mode);
}
