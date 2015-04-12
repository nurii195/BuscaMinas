package View;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * @author Marco A. Fernández
 *
 */
@SuppressWarnings("serial")
public class ButtonMatrix extends JPanel implements MouseListener {

	private final JButton[][] buttons;

	/**
	 * Create a new {@link ButtonMatrix} with <code>rows</code> rows and
	 * <code>columns</code> columns.
	 * 
	 * @param rows
	 *            Number of rows. Must be > 0.
	 * @param columns
	 *            Number of columns. Must be > 0.
	 * @throws IllegalArgumentException if <code>rows</code> or
	 * 		<code>columns</code> are less or equal zero.
	 */
	public ButtonMatrix(int rows, int columns) {
		if (rows <= 0 || columns <= 0)
			throw new IllegalArgumentException(
					"Number of rows and number of colums must be greater than zero");

		this.setLayout(new GridLayout(rows, columns));
		buttons = new JButton[rows][columns];
		for (int i = 0; i < buttons.length; i++)
		{
			for (int j = 0; j < buttons[i].length; j++)
			{
				buttons[i][j] = new JButton();
				buttons[i][j].addMouseListener(this);
				this.add(buttons[i][j]);
			}
		}
	}
	
	/**
	 * Search for <code>button</code> in the {@link ButtonMatrix}.
	 * @param button JButton to search in this {@link ButtonMatrix}.
	 * @return Point(row, column) of <code>button</code>.
	 * @throws IllegalArgumentException if not found.
	 */
	public Point getIndexOf(JButton button){
		for (int i = 0; i < buttons.length; i++)
		{
			for (int j = 0; j < buttons[i].length; j++)
			{
				if(buttons[i][j] == button)
					return new Point(i,j);
			}
		}
		throw new IllegalArgumentException();
	}
	
	/**
	 * Get the JButton at <code>row</code> and <code>column</code>.
	 * @param row Row of the JButton.
	 * @param column Column of the JButton.
	 * @return JButton at <code>row</code> and <code>column</code> position.
	 * @throws IndexOutOfBoundsException if <code>row</code> or <code>column</code>
	 * are greater than the button matrix.
	 */
	public JButton getButton(int row, int column) {
		return buttons[row][column];
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		MouseListener[] listeners = this.getListeners(MouseListener.class);
		for (MouseListener actionListener : listeners)
			actionListener.mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		MouseListener[] listeners = this.getListeners(MouseListener.class);
		for (MouseListener actionListener : listeners)
			actionListener.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		MouseListener[] listeners = this.getListeners(MouseListener.class);
		for (MouseListener actionListener : listeners)
			actionListener.mouseReleased(e);
	}
}
