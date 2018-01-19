package sudoku;

public class SudokuCell {
	private StringBuilder allowedValues = new StringBuilder("123456789");
	private int myValue = 0;
	
	/////////////////
	// constructors /
	/////////////////
	public SudokuCell(int value) {
		if (value != 0) {
			myValue = value;
			allowedValues.setLength(0);
		}
	}
	
	public SudokuCell() {}
	
	////////////
	// methods /
	////////////
	
	// Remove a value from the values allowed for this cell
	public void removeFromAllowedValues(int value) {
		String textValue = Integer.toString(value);
		int index = allowedValues.indexOf(textValue);
		if (index < 0) return;
		allowedValues.deleteCharAt(index);
		detectSingleValueAllowed();
	}
	
	public boolean isValueAllowed(int value) {
		String textValue = Integer.toString(value);
		return isValueAllowed(textValue);
	}
	
	public boolean isValueAllowed(String value) {
		return allowedValues.indexOf(value) >= 0;
	}
	
	public boolean hasOnlyOneAllowedValue() {
		return allowedValues.length() == 1;
	}
	
	// return my value
	public int getValue() {
		return myValue;
	}
		
	// set my value	
	public void setValue(int value) {
		if (value != 0)
			allowedValues.setLength(0);
		myValue = value;
	}
	
	public void setValue(String value) {
		setValue(Integer.parseInt(value));
	}
	
	public String getAllowedValues() {
		return allowedValues.toString();
	}
	
	public void setAllowedValues(String values) {
		allowedValues = new StringBuilder(values);
	}
	
	// check if only one value allowed in this cell.
	// if so, set that value to be the value
	private void detectSingleValueAllowed() {
		if (hasOnlyOneAllowedValue())
			setValue(allowedValues.toString());
	}
}