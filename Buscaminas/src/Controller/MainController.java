package Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;

import Model.MineField;
import View.ButtonMatrix;
import View.MainWindow;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MainController extends MainWindow {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainController frame = new MainController();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private final MouseListener listener;
	private MineField field;

	public MainController() {
		super(10, 10);
		mntmNuevoJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initGame();				
			}

		});
		
		listener = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1)
					processLeftClick(buttonMatrix.getIndexOf((JButton) e
							.getSource()));
				else if (e.getButton() == MouseEvent.BUTTON3){
					JButton b = (JButton) e.getSource();
					b.setText(b.getText() == "" ? "?" : "");					
				}
				syncModelView();
			}
		};
		
	}

	protected void initGame() {
		
		boolean gotWidth = false;
		int width = 0;
		do
		{	
			try{
				width = Integer.parseInt(JOptionPane.showInputDialog(this, "¿Ancho? Mínimo 2"));
				if(width > 1)
					gotWidth = true;
			}
			catch (Exception e){}
		}
		while (!gotWidth);	
		
		boolean gotHeigth = false;
		int heigth = 0;
		do
		{	
			try{
				heigth = Integer.parseInt(JOptionPane.showInputDialog(this, "¿Alto? Mínimo 2"));
				if(width > 1)
					gotHeigth = true;
			}
			catch (Exception e){}
		}
		while (!gotHeigth);	
		
		boolean gotMines = false;
		int mines = 0;
		do
		{	
			try{
				mines = Integer.parseInt(JOptionPane.showInputDialog(this, "¿Minas? Máximo "+((width * heigth)-1)));
				if(mines < width * heigth && mines > 0)
					gotMines = true;
			}
			catch (Exception e){}
		}
		while (!gotMines);	
		
		this.remove(buttonMatrix);
		buttonMatrix = new ButtonMatrix(heigth, width);
		this.buttonMatrix.addMouseListener(listener);
		this.add(buttonMatrix, BorderLayout.CENTER);
		field = new MineField(heigth, width,mines);
		this.setSize(this.getWidth() + 1, this.getHeight() + 1);
	}

	private void gameWon(){
		JOptionPane.showMessageDialog(this, "Ganaste");
		buttonMatrix.setEnabled(false);
	}
	
	private void gameFailed(){
		showMines();
		JOptionPane.showMessageDialog(this, "Fallaste");
		buttonMatrix.setEnabled(false);
	}
	
	private void showMines() {
		for (int i = 0; i < field.getMines().length; i++)
		{
			for (int j = 0; j < field.getMines()[0].length; j++)
			{
				if(field.getMines()[i][j] == MineField.MINE)
				{
					buttonMatrix.getButton(i, j).setText("X");
				}
			}
		}
		
	}

	/**
	 * Un hide all cell around <code>row</code>, <code>column</code>
	 * recursively.
	 * @param row
	 * @param column
	 */
	private void unHidePosition(int row, int column){
		if(field.getMines()[row][column] == MineField.EMPTY){
			field.getMines()[row][column] += 10;
			for (int z = row - 1; z <= row + 1; z++) {
				for (int w = column - 1; w <= column + 1; w++) {
					if (z >= 0 && z < field.getMines().length && w >= 0
							&& w < field.getMines()[0].length) {
						if ((z != row || w != column) && field.getMines()[z][w] != MineField.MINE) {
							unHidePosition(z, w);
						}
					}
				}
			}
		}
		else if (field.getMines()[row][column] < 10){
			field.getMines()[row][column] += 10;
		}
	}
	
	/**
	 * Check if the player have won the game.
	 * @return true if player have won.
	 */
	private boolean checkForWinner(){
		for (int i = 0; i < field.getMines().length; i++) {
			for (int j = 0; j < field.getMines()[0].length; j++) {
				if(field.getMines()[i][j] == MineField.MINE){
					for(int z = i-1; z<= i+1; z++){
						for(int w = j-1; w <= j+1; w++){
							if(z>= 0 && z < field.getMines().length &&
							   w>= 0 && w < field.getMines()[0].length &&
							   (z != i || w != j) && field.getMines()[z][w] < 9)
								return false;
						}
					}
				}					
			}
		}
		return true;
	}
	
	/**
	 * Process the mouse left click
	 * @param p MineField point
	 */
	private void processLeftClick(Point p) {
		if(field.getMines()[p.x][p.y] == MineField.MINE){
			buttonMatrix.getButton(p).setText("X");
			gameFailed();
		}
		else{
			unHidePosition(p.x, p.y);
			if (checkForWinner()) {
				gameWon();
			}
		}
	}

	/**
	 * Sync the view with the model
	 */
	private void syncModelView() {
		byte[][] mines = field.getMines();
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				if (mines[i][j] == 10) {
					flatButton(buttonMatrix.getButton(i, j));

				} else if (mines[i][j] > 10) {
					JButton button = buttonMatrix.getButton(i, j);
					if(button.getText() != "?"){
						button.setText(String.valueOf(mines[i][j] - 10));
						switch (mines[i][j] - 10) {
						case 1:
							button.setForeground(Color.BLUE);
							break;
						//TODO other colors
						}
						flatButton(button);
					}
				}
			}
		}
	}

	private void flatButton(JButton button){
		button.setFocusPainted(false);
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorder(new LineBorder(Color.GRAY));
	}
}
