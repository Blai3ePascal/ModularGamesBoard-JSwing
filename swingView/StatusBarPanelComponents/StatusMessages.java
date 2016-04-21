package es.ucm.fdi.tp.practica5.swingView.StatusBarPanelComponents;

import javax.swing.JTextArea;

/**
 * The Class StatusMessages.
 */
public class StatusMessages extends JTextArea{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new status messages.
	 */
	public StatusMessages(){
		this.setBorder(javax.swing.BorderFactory.createTitledBorder("Status Messages"));
	}
	
	
	/**
	 * New msg.
	 *
	 * @param msg the msg
	 */
	public void newMsg(String msg){
		this.setText(this.getText()+"\n -> "+msg);
	}
	
	/**
	 * New msg.
	 *
	 * @param msg the msg
	 */
	public void newFlatMsg(String msg){
		this.setText(this.getText()+"\n "+msg);
	}
}
