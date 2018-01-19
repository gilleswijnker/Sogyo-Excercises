package sudoku;

import java.util.Arrays;
import sudokuhelper.*;

public class SudokuSquare extends SudokuElement {
	public boolean removeValues(int column, int row, String values) {
		boolean isUpdated = false;
		for (int i = 0; i <= 2; i++) {
			if (row >= 0) {
				isUpdated |= removeValueFromCells(myCells.get(SudokuHelper.rowFromIndex(row, i + 1)), values);
			} else if (column >= 0) {
				isUpdated |= removeValueFromCells(myCells.get(SudokuHelper.columnFromIndex(column, i * 3)), values);
			}
		}
		return isUpdated;
	}
	
	public String[] getUniqueColumnValues() {
		String[] uniquePerColumn = new String[3];
		String[] allowedPerColumn = getElementsAllowedInSingleColumn();
		uniquePerColumn[0] = SudokuHelper.unique(allowedPerColumn[2], SudokuHelper.unique(allowedPerColumn[1], allowedPerColumn[0]));
		uniquePerColumn[1] = SudokuHelper.unique(allowedPerColumn[0], SudokuHelper.unique(allowedPerColumn[2], allowedPerColumn[1]));
		uniquePerColumn[2] = SudokuHelper.unique(allowedPerColumn[1], SudokuHelper.unique(allowedPerColumn[0], allowedPerColumn[2]));
		return uniquePerColumn;
	}
	
	public String[] getUniqueRowValues() {
		String[] uniquePerRow = new String[3];
		String[] allowedPerRow = getElementsAllowedInSingleRow();
		uniquePerRow[0] = SudokuHelper.unique(allowedPerRow[2], SudokuHelper.unique(allowedPerRow[1], allowedPerRow[0]));
		uniquePerRow[1] = SudokuHelper.unique(allowedPerRow[0], SudokuHelper.unique(allowedPerRow[2], allowedPerRow[1]));
		uniquePerRow[2] = SudokuHelper.unique(allowedPerRow[1], SudokuHelper.unique(allowedPerRow[0], allowedPerRow[2]));
		return uniquePerRow;
	}
	
	public String[] getElementsAllowedInSingleColumn() {
		String[] allowedPerColumn = {"", "", ""};
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				SudokuCell cell = myCells.get(i + j*3);
				String values = cell.getAllowedValues();
				allowedPerColumn[i] += SudokuHelper.unique(allowedPerColumn[i], values);
			}
		}
		return allowedPerColumn;
	}
	
	public String[] getElementsAllowedInSingleRow() {
		String[] allowedPerRow= {"", "", ""};
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				SudokuCell cell = myCells.get(j + i*3);
				String values = cell.getAllowedValues();
				allowedPerRow[i] += SudokuHelper.unique(allowedPerRow[i], values);
			}
		}
		return allowedPerRow;
	}
}
