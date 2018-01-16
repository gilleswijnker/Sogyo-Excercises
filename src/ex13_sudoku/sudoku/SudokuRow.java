package sudoku;

public class SudokuRow extends SudokuElement {
	public SudokuRow() {
		for (int i = 0; i <= 8; i++)
			myCells.add(new SudokuCell());
	}
}