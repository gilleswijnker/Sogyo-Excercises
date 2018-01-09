public class MyLists {
	public static void main(String[] args) {
		MyListClass c = new MyListClass();
		
		// sub-exercise 1
		System.out.println("\nEx 1");
		int[] list = c.GenerateRandomList(10);
		System.out.println(MyListClass.ListToString(list, " "));
		
		// sub-exercise 2
		System.out.println("\nEx 2");
		System.out.println(MyListClass.FindMaxValue(list));
		
		// sub-exercise 3
		System.out.println("\nEx 3");
		System.out.println(MyListClass.FindAddLowestTwo(list));
		
		// sub-exercise 4
		System.out.println("\nEx 4");
		int[] filteredList = MyListClass.FilterNumbers(list, value -> value % 2 == 0);
		System.out.println(MyListClass.ListToString(filteredList, " "));
		
		// sub-exercise 5
		System.out.println("\nEx 5");
		int[] filteredList2 = MyListClass.FilterNumbers(list, value -> value % 2 == 0);
		System.out.println(MyListClass.ListToString(filteredList2, " "));
		int[] filteredList3 = MyListClass.FilterNumbers(list, value -> value % 3 == 0);
		System.out.println(MyListClass.ListToString(filteredList3, " "));
		int[] filteredList5 = MyListClass.FilterNumbers(list, value -> value % 5 == 0);
		System.out.println(MyListClass.ListToString(filteredList5, " "));
		int[] filteredListRest = MyListClass.FilterNumbers(list, value -> value % 2 != 0 && value % 3 != 0 && value % 5 != 0);
		System.out.println(MyListClass.ListToString(filteredListRest, " "));
		
		// sub-exercise 6
		System.out.println("\nEx 6");
		int[] sortedList = MyListClass.BubbleSort(list);
		System.out.println(MyListClass.ListToString(sortedList, " "));
	}
}

class MyListClass {
	// interfaces for lambda-functions
	private interface FindValueHelper {
		public abstract int chooseValue(int valueOne, int valueTwo);
	}
	
	public interface FindFilterHelper {
		public abstract boolean isValueFiltered(int value);
	}

	////////////////////////////////////////////
	// Generate a list filled with random ints /
	////////////////////////////////////////////
	public int[] GenerateRandomList(int size, int upperBound) {
		int[] list = new int[size];
		for (int i = 0; i < size; i++)
			list[i] = (int) (Math.random() * upperBound) + 1;
		return list;
	}
	
	public int[] GenerateRandomList(int size) {
		return GenerateRandomList(size, 100);
	}
	
	public int[]  GenerateRandomList() {
		return GenerateRandomList(10);
	}
	
	/////////////////////////////////////////
	// find highest/ lowest value in a list /
	/////////////////////////////////////////
	public static int FindMaxValue(int[] list) {
		// using lambda
		FindValueHelper checker = (int listValue, int value) -> value > listValue ? value : listValue;
		return RecursiveHelper(list, list[0], 0, checker);
	}
	
	public static int FindLowestValue(int[] list) {
		// using lambda
		FindValueHelper checker = (int listValue, int value) -> value < listValue ? value : listValue;
		return RecursiveHelper(list, list[0], 0, checker);
	}

	// Recursively select a value from a list based on the checker-lambda function /
	private static int RecursiveHelper(int[] list, int value, int index, FindValueHelper checker) {
		if (index == list.length) return value;
		int choosenValue = checker.chooseValue(list[index], value);
		return RecursiveHelper(list, choosenValue, index + 1, checker);
	}
	
	/////////////////////////////////////////
	// find lowest two values and add these /
	/////////////////////////////////////////
	public static int FindAddLowestTwo(int[] list) {
		int[] minValues = new int[2];
		
		if (list[0] < list[1]) {
			minValues[0] = list[0];
			minValues[1] = list[1];
		} else {
			minValues[0] = list[1];
			minValues[1] = list[0];
		}
		
		for (int i = 2; i < list.length; i++) {
			int value = list[i];
			if (value < minValues[0]) { 
				int tempValue = minValues[0];
				minValues[0] = value;
				value = tempValue;
			}
			if (value < minValues[1])
				minValues[1] = value;
		}
		
		System.out.println(ListToString(minValues, " "));
		return minValues[0] + minValues[1];
	}
	
	////////////////
	// apply filter/
	////////////////
	public static int[] FilterNumbers(int[] list, FindFilterHelper filter) {
		return FilterNumberIterator(list, new int[0], 0, filter);
	}
	
	private static int[] FilterNumberIterator(int[] originalList, int[] filteredList, int index, FindFilterHelper filter) {
		if (index > originalList.length - 1) return filteredList;
	
		int value = originalList[index];
		if (filter.isValueFiltered(value)) {
			filteredList = AppendToList(filteredList, value);
		}
		return FilterNumberIterator(originalList, filteredList, index + 1, filter);
	}
	
	private static int[] AppendToList(int[] list, int value) {
		// create a list that's one longer, copy all values and append the given value
		int lengthList = list.length + 1;
		int[] returnList = new int[lengthList];
	
		// copy
		for (int i = 1; i < lengthList; i++) {
			returnList[i - 1] = list[i - 1];
		}
		// append
		returnList[lengthList - 1] = value;
		return returnList;
	}
	
	////////////////
	// BubbleSort /
	////////////////
	public static int[] BubbleSort(int[] list) {
		int swaps = 1;
		while (swaps != 0) {
			for (int i = 0; i < list.length - 1; i++) {
				swaps = 0;
				if (list[i] < list[i+1]) {
					swaps++;
					int t = list[i];
					list[i] = list[i+1];
					list[i+1] = t;
				}
			}
		}
		return list;
	}
	
	////////////////////////////////
	// make a string out of a list /
	////////////////////////////////
	public static String ListToString(int [] list, String sep) {
		String returnString = "";
		for (int i : list) {
			returnString += Integer.toString(i) + sep;
		}
		return returnString.trim();
	}
}