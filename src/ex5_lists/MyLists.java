public class MyLists {
	public static void main(String[] args) {
		MyListClass c = new MyListClass();
		int[] list = c.GenerateRandomList(10);
		System.out.println(MyListClass.ListToString(list, " "));
		System.out.println(MyListClass.FindMaxValue(list));
		System.out.println(MyListClass.FindAddLowestTwo(list));
	}
}

class MyListClass {
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
	
	/////////////////////////////////
	// find highest value in a list /
	/////////////////////////////////
	public static int FindMaxValue(int[] list) {
		int maxValue = list[0];

		// not using for-each to skip first list entry 
		for (int i = 1; i < list.length; i++)
			if (list[i] > maxValue) maxValue = list[i];
		return maxValue;
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
				int tempValue = value;
				tempValue = minValues[0];
				minValues[0] = value;
				value = tempValue;
			}
			if (value < minValues[1])
				minValues[1] = value;
		}
		
		System.out.println(ListToString(minValues, " "));
		return minValues[0] + minValues[1];
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