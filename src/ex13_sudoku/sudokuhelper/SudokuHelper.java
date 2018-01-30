package sudokuhelper;

public final class SudokuHelper {
	private SudokuHelper() {}
	
	public static int columnFromIndex(int i, int j) {
		return (i + j) % 9;
	}
	
	public static int rowFromIndex(int i, int j) {
		return (int)(i / 3)*3 + (i + j) % 3;
	}
	
	public static int getSquareIndex(int row, int col) {
		return (int) (row / 3) * 3 + (int) (col / 3);
	}
	
	public static String unique(String string1, String string2) {
		String result = "";
		for (int i = 0; i < string2.length(); i++)
			if (string1.indexOf(string2.charAt(i)) < 0)
					result += string2.substring(i, i + 1);
		return result;
	}
	
	public static void printSudoku(String sudoku) {
		if (sudoku.length() != 81)
			throw new IllegalArgumentException("sudoku string contains " + sudoku.length() + " chars...");
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; j++) {
				System.out.print(" " + sudoku.substring(i * 9 + j, i * 9 + j + 1) + " ");
				if ((j + 1) % 3 == 0 && j != 8)
					System.out.print("|");
			}
			System.out.println();
			if ((i + 1) % 3 == 0 && i != 8)
				System.out.print("------------------------------\n");
		}
	}
	
	public static void main(String args[]) {
		printSudoku(args[0]);
	}
}