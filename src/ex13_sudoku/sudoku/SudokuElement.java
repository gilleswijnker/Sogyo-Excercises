package sudoku;

import java.util.ArrayList;

public abstract class SudokuElement {
	public ArrayList<SudokuCell> myCells = new ArrayList<>();
		
	public boolean update() {
		// first strategy: remove values that are known / inspect cell to only allow a single value
		boolean isUpdated = false;
		
		for (SudokuCell cell : myCells) {
			if (cell.getValue() != 0 && removeValueFromCells(cell.getValueBit()))
				isUpdated = true;
			if (cell.hasOnlyOneAllowedValue()) {
				cell.setValueBit(cell.getAllowedValuesBit());
				isUpdated = true;
			}
		}
		
		// second strategy: place values that are only allowed at one place
		int singlePlaceValues = findSinglePlaceValues();
		for (SudokuCell cell : myCells) {
			int value = cell.getAllowedValuesBit() & singlePlaceValues;
			if (value != 0) {
				cell.setValueBit(value);
				isUpdated = true;
			}
		}
		
		return isUpdated;
	}
	
	public int findSinglePlaceValues() {
		// Find values that are allowed at only one place
		int bitValue = 0;
		int[] values = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		for (SudokuCell cell : myCells) {
			int allowedValues = cell.getAllowedValuesBit();
			for (int i = 0; i <= 8; i++) {
				if ((1 << i & allowedValues) != 0)
					values[i] += 1;
			}
		}
		for (int i = 0; i <= 8; i++)
			if (values[i] == 1)
				bitValue += 1 << i;
		return bitValue;
	}
	
	private String bitsToString(int value) {
		String result = "";
		for (int i = 1; i <= 9; i++) {
			if ((1 << (i - 1) & value) != 0) {
				result += i + " ";
			}
		}
		return result.trim();
	}
	
	public boolean removeValueFromCells(int value) {
		boolean valueRemoved = false;
		
		for (SudokuCell cell : myCells) {
			if (cell.isValueAllowed(value)) {
				cell.removeFromAllowedValues(value);
				valueRemoved = true;
			}
		}
		return valueRemoved;
	}
	
	public String toString() {
		String result = "";
		for (SudokuCell cell : myCells)
			result += cell.getValue() + "\t";
		return result.trim();
	}
}
