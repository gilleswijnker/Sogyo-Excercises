package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import sudokuhelper.*;

public class SudokuGame {
	private ArrayList<SudokuSquare> mySquares;
	private ArrayList<SudokuRow> myRows;
	private ArrayList<SudokuColumn> myColumns;
	private int loopsMade = 0;
	private int depth = 0;
	
	public static void main(String[] args) {
		/*
		 * Examples
		 *   	   000820090500000000308040007100000040006402503000090010093004000004035200000700900
		 *   	   000272283000000900002608500050800030010040070040003090006405700001000000530012000
		 * pzl 1:  320041000100000008008005000000003100006000400005700000000900800580000009000680075
		 * pzl 2:  003080001020001000100300800200009070006240000050000100300900008000056090040000700
		 * pzl 3:  020604030300050904004700600000000768000000000148000000002005400607090005050307020
		 */
		SudokuGame game = new SudokuGame(args[0]);
		System.out.println(game);
		try {
			game.solve();
		} catch (StackOverflowError ex) {
			// too much recursion: no solution found :(
			System.out.println("I'm sorry, I could not find a solution");
		}
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
	
	public GameState solve() {
		boolean movesMade = false;
		loopsMade++;
				
		for (SudokuSquare square : mySquares)
			movesMade |= square.update();
				
		for (SudokuRow row: myRows)
			movesMade |= row.update();
		
		for (SudokuColumn col: myColumns)
			movesMade |= col.update();

		// do next strategy only when the 'simple' strategies above yield no result 
		if (!movesMade) {
			// each square
			for (int i = 0; i <= 8; i++) {
				String[] uniquePerColumn = mySquares.get(i).getUniqueColumnValues();
				String[] uniquePerRow = mySquares.get(i).getUniqueRowValues();
				for (int j = 0; j <= 2; j++) {
					if (uniquePerColumn[j].length() == 0) continue;
					movesMade |= mySquares.get(SudokuHelper.columnFromIndex(i, 3)).removeValues(j, -1, uniquePerColumn[j]);
					movesMade |= mySquares.get(SudokuHelper.columnFromIndex(i, 6)).removeValues(j, -1, uniquePerColumn[j]);
					movesMade |= mySquares.get(SudokuHelper.rowFromIndex(i, 1)).removeValues(-1, j, uniquePerRow[j]);
					movesMade |= mySquares.get(SudokuHelper.rowFromIndex(i, 2)).removeValues(-1, j, uniquePerRow[j]);
				}
			}
		}
		
		// too bad, but I'm not valid anymore :(
		if (!isSolutionValid())
			return GameState.INVALID;
		
		// yeay! I'm solved!
		if (isSolved()) 
			return GameState.SOLVED;
		
		// was not possible to make a move. Start guessing
		if (!movesMade)
			return iterativeSolve();
		
		return solve();
	}
	
	private GameState iterativeSolve() {
		// guess a value, and try to solve the sudoku
		for (int row = 0; row <= 8; row++) {
			// for each col
			for (int col = 0; col <= 8; col++) {
				// 'hard' reference to cell of interest: creating a new reference variable failed
				String allowedValues = myRows.get(row).myCells.get(col).getAllowedValues();
				if (allowedValues.length() > 0) {
					for (int k = 0; k < allowedValues.length(); k++) {
						// store current state
						String currentSudoku = toString().replaceAll("[\r\n\t ]", "");
						myRows.get(row).myCells.get(col).setValue(allowedValues.substring(k, k + 1));
						GameState gameState = solve();
						if (gameState == GameState.SOLVED) return GameState.SOLVED;
						
						// restore last state 
						initElements();
						fillElements(currentSudoku);
					}
				}
			}
		}
		return GameState.UNSOLVED;
	}
	
	public String toString() {
		String result = "";
		for (SudokuRow row : myRows)
			result += row.toString() + "\n";
		return result;
	}
	
	public boolean isSolved() {
		// are all cells filled with a value?
		for (int i = 0; i <= 8; i++)
			for (int j = 0; j <= 8; j++)
				if (mySquares.get(i).myCells.get(j).getValue() == 0)
					return false;
		return true;
	}
	
	public boolean isSolutionValid() {
		// is the sudoku table still valid (no duplicates in an element)
		for (SudokuSquare square : mySquares)
			if (!isElementValid(square)) return false;
				
		for (SudokuRow row: myRows)
			if (!isElementValid(row)) return false;
		
		for (SudokuColumn col: myColumns)
			if (!isElementValid(col)) return false;
		
		return true;
	}
	
	private boolean isElementValid(SudokuElement element) {
		// find out if an does not contain duplicate values
		ArrayList<Integer> values = new ArrayList<>();
		for (SudokuCell cell : element.myCells) {
			Integer value = new Integer(cell.getValue());
			if (value == 0) continue;
			if (values.contains(value))
				return false;
			values.add(value);
		}
		return true;
	}
	
	private void initElements() {
		// initialize the sudoku elements
		mySquares = new ArrayList<>();
		myRows = new ArrayList<>();
		myColumns = new ArrayList<>();
		for (int i = 0; i <= 8; i++) {
			mySquares.add(new SudokuSquare());
			myRows.add(new SudokuRow());
			myColumns.add(new SudokuColumn());
		}
	}
	
	private void fillElements(String sudokuAsString) {
		// fill the sudoku elements based on the supplied starting string
		for (int row = 0; row <= 8; row++) {
			for (int col = 0; col <= 8; col++) {
				int charAt = row * 9 + col;
				int value = Integer.parseInt(sudokuAsString.substring(charAt, charAt + 1));
				SudokuCell cell = new SudokuCell(value);
				int squareIndex = SudokuHelper.getSquareIndex(row, col);

				myRows.get(row).myCells.set(col, cell);
				myColumns.get(col).myCells.set(row, cell);
				mySquares.get(squareIndex).myCells.add(cell);
			}
		}
	}
}

enum GameState {
	SOLVED, INVALID, UNSOLVED
}