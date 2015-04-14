package View;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	protected JPanel contentPane;
	protected ButtonMatrix buttonMatrix;
	private JMenuBar menuBar;
	private JMenu mnMenu;
	protected JMenuItem mntmNuevoJuego;

	/**
	 * Create the frame.
	 */
	public MainWindow(int rows, int columns) {
		setTitle("Buscaminas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 461, 371);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		mntmNuevoJuego = new JMenuItem("Nuevo Juego");
		mnMenu.add(mntmNuevoJuego);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		buttonMatrix = new ButtonMatrix(rows, columns);
		buttonMatrix.setEnabled(false);
		contentPane.add(buttonMatrix,BorderLayout.CENTER);
	}

}
