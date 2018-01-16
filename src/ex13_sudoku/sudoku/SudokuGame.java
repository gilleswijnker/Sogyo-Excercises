package sudoku;

import java.util.ArrayList;

public class SudokuGame {
	private ArrayList<SudokuSquare> mySquares = new ArrayList<>();
	private ArrayList<SudokuRow> myRows = new ArrayList<>();
	private ArrayList<SudokuColumn> myColumns = new ArrayList<>();
	private int loopsMade = 0;
	
	public static void main(String[] args) {
		SudokuGame game = new SudokuGame("000820090500000000308040007100000040006402503000090010093004000004035200000700900");
		System.out.println(game);
		while (game.solve()) {};
		System.out.println();
		System.out.println(game);
	}
	
	public SudokuGame(String sudokuAsString) {
		for (int i = 0; i <= 8; i++) {
			mySquares.add(new SudokuSquare());
			myRows.add(new SudokuRow());
			myColumns.add(new SudokuColumn());
		}
		for (int row = 0; row <= 8; row++) {
			for (int col = 0; col <= 8; col++) {
				int charAt = row * 9 + col;
				int value = Integer.parseInt(sudokuAsString.substring(charAt, charAt + 1));
				SudokuCell cell = new SudokuCell(value);
				int squareIndex = getSquareIndex(row, col);

				myRows.get(row).myCells.set(col, cell);
				myColumns.get(col).myCells.set(row, cell);
				mySquares.get(squareIndex).myCells.add(cell);
			}
		}
	}
	
	public boolean solve() {
		boolean movesMade = true;
		loopsMade++;
		
		for (SudokuSquare square : mySquares)
			movesMade = square.update();
		
		for (SudokuRow row: myRows)
			movesMade = row.update();
		
		for (SudokuColumn col: myColumns)
			movesMade = col.update();
		
		System.out.print("\r" + loopsMade);
		return movesMade;
	}
	
	public String toString() {
		String result = "";
		for (SudokuRow row : myRows)
			result += row.toString() + "\n";
		return result;
	}
	
	private int getSquareIndex(int row, int col) {
		return (int) (row / 3) * 3 + (int) (col / 3);
	}
}