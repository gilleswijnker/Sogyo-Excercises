package sudoku;

import java.util.Arrays;

public class SudokuSquare extends SudokuElement {
	public boolean removeValueFromSquare(int col, int row, int value) {
		for (int i = 1; i <= 3; i++) {
			if (row != 0 && <value is in row> )
				delete value from cells in that row
		}
	}
	
	public String[] getUniqueColumnValues() {
		String[] uniquePerColumn = new String[3];
		String[] allowedPerColumn = getElementsAllowedInSingleColumn();
		uniquePerColumn[0] = unique(allowedPerColumn[2], unique(allowedPerColumn[1], allowedPerColumn[0]));
		uniquePerColumn[1] = unique(allowedPerColumn[0], unique(allowedPerColumn[2], allowedPerColumn[1]));
		uniquePerColumn[2] = unique(allowedPerColumn[1], unique(allowedPerColumn[0], allowedPerColumn[2]));
		return uniquePerColumn;
	}
	
	public String[] getUniqueRowValues() {
		String[] uniquePerRow = new String[3];
		String[] allowedPerRow = getElementsAllowedInSingleRow();
		uniquePerRow[0] = unique(allowedPerRow[2], unique(allowedPerRow[1], allowedPerRow[0]));
		uniquePerRow[1] = unique(allowedPerRow[0], unique(allowedPerRow[2], allowedPerRow[1]));
		uniquePerRow[2] = unique(allowedPerRow[1], unique(allowedPerRow[0], allowedPerRow[2]));
		return uniquePerRow;
	}
	
	public String[] getElementsAllowedInSingleColumn() {
		String[] allowedPerColumn = {"", "", ""};
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j <= 2; j++) {
				SudokuCell cell = myCells.get(i + j*3);
				String values = cell.getAllowedValues();
				allowedPerColumn[i] += unique(allowedPerColumn[i], values);
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
				allowedPerRow[i] += unique(allowedPerRow[i], values);
			}
		}
		return allowedPerRow;
	}
	
	public String unique(String string1, String string2) {
		String result = "";
		for (int i = 0; i < string2.length(); i++)
			if (string1.indexOf(string2.charAt(i)) < 0)
					result += string2.substring(i, i + 1);
		return result;
	}
}
