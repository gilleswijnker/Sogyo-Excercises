package sudoku;

public class SudokuColumn extends SudokuElement {
	public SudokuColumn() {
		for (int i = 0; i <= 8; i++)
			myCells.add(new SudokuCell());
	}
}