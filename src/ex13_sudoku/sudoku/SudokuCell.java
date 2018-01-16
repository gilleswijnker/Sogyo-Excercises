package sudoku;

public class SudokuCell {
	private int allowedValues;
	private int myValue = 0;
	
	/////////////////
	// constructors /
	/////////////////
	public SudokuCell(int value) {
		myValue = value;
		if (value == 0)
			allowedValues = 0b111111111;
	}
	
	public SudokuCell() {
		this(0);
	}
	
	////////////
	// methods /
	////////////
	
	// Remove a value from the values allowed for this cell
	public void removeFromAllowedValues(int value) {
		//int bitmaskValue = 1 << (value - 1);
		int bitmaskValue = allowedValues & value;
		allowedValues ^= bitmaskValue;
	}
	
	public boolean isValueAllowed(int value) {
		return (allowedValues & value) != 0;
	}
	
	public boolean hasOnlyOneAllowedValue() {
		return getAllowedValues().length() == 1;
	}
	
	// return my value
	public int getValue() {
		return myValue;
	}
	
	public int getValueBit() {
		return (myValue == 0) ? 0 : 1 << (myValue - 1);
	}
	
	// set my value
	public void setValueBit(int value) {
		setValue((int) (Math.log(value) / Math.log(2)) + 1);
	}
	
	public void setValue(int value) {
		if (value != 0)
			allowedValues = 0;
		myValue = value;
	}
	
	public String getAllowedValues() {
		String result = "";
		for (int i = 1; i <= 9; i++) {
			if ((1 << (i - 1) & allowedValues) != 0) {
				result += i + " ";
			}
		}
		return result.trim();
	}
	
	public int getAllowedValuesBit() {
		return allowedValues;
	}
}