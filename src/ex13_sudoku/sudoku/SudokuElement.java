package sudoku;

import java.util.ArrayList;

public abstract class SudokuElement {
	public ArrayList<SudokuCell> myCells = new ArrayList<>();
		
	public boolean update() {
		boolean isUpdated = false;
		isUpdated |= removeKnownValues();
		isUpdated |= placeSingleAllowedValues();
		return isUpdated;
	}
	
	public boolean removeKnownValues() {
		// remove values that are known
		boolean isUpdated = false;
		
		for (SudokuCell cell : myCells) {
			int value = cell.getValue();
			if (value != 0 && removeValueFromCells(value))
				isUpdated = true;
		}
		return isUpdated;
	}
		
	public boolean placeSingleAllowedValues() {
		// place values that are only allowed at one place
		boolean isUpdated = false;
		String singlePlaceValues = findSinglePlaceValues();
		for (SudokuCell cell : myCells) {
			for (int i = 0; i < singlePlaceValues.length(); i++) {
				String value = singlePlaceValues.substring(i, i + 1);
				if (cell.isValueAllowed(value)) {
					cell.setValue(value);
					removeValueFromCells(value);
					isUpdated = true;
				}
			}
		}
		return isUpdated;
	}
		
	public String findSinglePlaceValues() {
		// Find values that are allowed at only one place
		int[] values = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		String result = "";

		for (SudokuCell cell : myCells)
			countAllowedValues(cell, values);
		for (int i = 0; i <= 8; i++)
			if (values[i] == 1)
				result += Integer.toString(i + 1);
		return result;
	}
	
	public void countAllowedValues(SudokuCell cell, int[] values) {
		String allowedValues = cell.getAllowedValues();
		for (int i = 0; i < allowedValues.length(); i++) {
			int index = Integer.parseInt(allowedValues.substring(i, i + 1)) - 1;
			values[index]++;
		}
	}
	
	public boolean removeValueFromCells(int value) {
		boolean valueRemoved = false;
		
		for (SudokuCell cell : myCells) {
			if (cell.getValue() == 0 && cell.isValueAllowed(value)) {
				cell.removeFromAllowedValues(value);

				// if cell goes from blank to a value, remove that value
				// from the allowed list of other cells
				if (cell.getValue() != 0)
					removeValueFromCells(cell.getValue());
				valueRemoved = true;
			}
		}
		return valueRemoved;
	}
	
	public boolean removeValueFromCells(String value) {
		return removeValueFromCells(Integer.parseInt(value));
	}
	
	public String toString() {
		String result = "";
		for (SudokuCell cell : myCells)
			result += cell.getValue() + "\t";
		return result.trim();
	}
}
