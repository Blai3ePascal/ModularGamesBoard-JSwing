package es.ucm.fdi.tp.practica5.swingView.StatusBarPanelComponents;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

// TODO: Auto-generated Javadoc
/**
 * The Class ColorChooser.
 */
public class ColorChooser extends JDialog {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The color chooser. */
	JColorChooser colorChooser;
	
	/** The color. */
	Color color;

	/**
	 * Instantiates a new color chooser.
	 *
	 * @param parent the parent
	 * @param title the title
	 * @param initColor the init color
	 */
	public ColorChooser(JFrame parent, String title, Color initColor) {
		super(parent, title);
		setModalityType(DEFAULT_MODALITY_TYPE);
		colorChooser = new JColorChooser(initColor == null ? Color.WHITE : initColor);

		getContentPane().add(colorChooser);

		// Create a button
		JPanel buttonPane = new JPanel();
		JButton buttonOK = new JButton("OK");
		buttonOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				color = colorChooser.getColor();
				closeDialog();
			}
		});
		buttonPane.add(buttonOK);

		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		});
		buttonPane.add(buttonCancel);

		// set action listener on the button
		getContentPane().add(buttonPane, BorderLayout.PAGE_END);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/**
	 * Close dialog.
	 */
	private void closeDialog() {
		setVisible(false);
		dispose();
	}

	// override the createRootPane inherited by the JDialog, to create the
	// rootPane.
	/* (non-Javadoc)
	 * @see javax.swing.JDialog#createRootPane()
	 */
	// create functionality to close the window when "Escape" button is pressed
	public JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke("ESCAPE");

		@SuppressWarnings("serial")
		Action action = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		};
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, "ESCAPE");
		rootPane.getActionMap().put("ESCAPE", action);
		return rootPane;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}
}