package sudoku;

import java.util.ArrayList;
import java.util.Arrays;

import com.oracle.jrockit.jfr.InvalidValueException;

public class SudokuGame {
	private ArrayList<SudokuSquare> mySquares = new ArrayList<>();
	private ArrayList<SudokuRow> myRows = new ArrayList<>();
	private ArrayList<SudokuColumn> myColumns = new ArrayList<>();
	private int loopsMade = 0;
	
	public static void main(String[] args) {
		/*
		 * Examples
		 *   	   000820090500000000308040007100000040006402503000090010093004000004035200000700900
		 *   	   000272283000000900002608500050800030010040070040003090006405700001000000530012000
		 * pzl 1:  320041000100000008008005000000003100006000400005700000000900800580000009000680075
		 */
		SudokuGame game = new SudokuGame("320041000100000008008005000000003100006000400005700000000900800580000009000680075");
		System.out.println(game);
		while (game.solve()) {};
		System.out.println();
		System.out.println(game);
		System.out.println(game.getLoopsMade());
	}
	
	public int getLoopsMade() {
		return loopsMade;
	}
	
	public SudokuGame(String sudokuAsString) {
		if (sudokuAsString.length() != 81)
			throw new IllegalArgumentException("sudoku string contains " + sudokuAsString.length() + " chars...");
		
		initElements();
		fillElements(sudokuAsString);
	}
	
	public boolean solve() {
		boolean movesMade = false;
		loopsMade++;
				
		for (SudokuSquare square : mySquares)
			movesMade |= square.update();
				
		for (SudokuRow row: myRows)
			movesMade |= row.update();
		
		for (SudokuColumn col: myColumns)
			movesMade |= col.update();
		
		for (SudokuSquare square : mySquares) {
			System.out.println(Arrays.toString(square.getUniqueColumnValues()));
			System.out.println(Arrays.toString(square.getUniqueRowValues()));
			System.out.println();
		}
				
		return movesMade;
	}
	
	public String toString() {
		String result = "";
		for (SudokuRow row : myRows)
			result += row.toString() + "\n";
		return result;
	}
	
	private void initElements() {
		for (int i = 0; i <= 8; i++) {
			mySquares.add(new SudokuSquare());
			myRows.add(new SudokuRow());
			myColumns.add(new SudokuColumn());
		}
	}
	
	private void fillElements(String sudokuAsString) {
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
	
	private int getSquareIndex(int row, int col) {
		return (int) (row / 3) * 3 + (int) (col / 3);
	}
}