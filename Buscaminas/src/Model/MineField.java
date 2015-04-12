package Model;

public class MineField {
	public static final byte MINE = 9;
	public static final byte EMPTY = 0;

	private final byte[][] mines;

	public byte[][] getMines() {
		return mines;
	}

	/**
	 * Create a new {@link MineField} <code>rows</code> height
	 * and <code>columns</code> width with <code>mines</code> mines.
	 * @param rows
	 * @param columns
	 * @param mines
	 */
	public MineField(int rows, int columns, int mines) {
		this.mines = new byte[rows][columns];
		plantMines(mines);
		calculateMines();
	}

	/**
	 * Put <code>nMines</code> in the {@link MineField} at
	 * random positions
	 * @param nMines Number of mines to plant.
	 */
	private void plantMines(int nMines) {

		while (nMines > 0) {
			int row = (int) (Math.random() * mines.length);
			int column = (int) (Math.random() * mines[0].length);
			if (mines[row][column] == EMPTY) {
				mines[row][column] = MINE;
				nMines--;
			}
		}
	}

	/**
	 * Calculate how many mines is there around all cell of the
	 * byte matrix.
	 */
	private void calculateMines() {
		for (int i = 0; i < mines.length; i++) {
			for (int j = 0; j < mines[i].length; j++) {
				if (mines[i][j] == MINE) {
					for (int z = i - 1; z <= i + 1; z++) {
						for (int w = j - 1; w <= j + 1; w++) {
							if (z >= 0 && z < mines.length && w >= 0
									&& w < mines[0].length) {
								if ((z != i || w != j) && mines[z][w] != MINE) {
									mines[z][w]++;
								}
							}
						}
					}
				}
			}
		}
	}

}
