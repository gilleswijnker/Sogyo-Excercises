import java.util.Scanner;

public class FibonacciTest {
	public static void main (String[] args) {
		Fibonacci f = new Fibonacci();
		f.PrintFibonacciNumber(UserInput.GetUserInput());
	}
}

class Fibonacci {
	// Print the n-th Fibonacci number and the sum of all even Fibonacci numbers up to the n-th
	public void PrintFibonacciNumber(int n) {
		int fib[] = GetFibonacciNumber(n);
		String term;
		
		/* 
		Setting the 'term' that follows a number
		(and trying out the switch statement)
		*/
		switch (n) {
			case 1: term = "st";
					break;
			
			case 2: term = "nd";
					break;
			
			default: term = "th";
					 break;
		}
		
		// print out the result
		System.out.println("The value of the " + n + term + " term of the Fibonacci sequence is " + fib[0] + ".");
		System.out.println("The sum of all even values of the first " + n + " terms is " + fib[1] + ".");
	}
	
	// Get the n-th Fibonacci number and the sum of all even Fibonacci numbers up to the n-th
	public int[] GetFibonacciNumber(int n) {
		int a = 0; // 1st number
		int b = 1; // 2nd number
		int c = 0; // 'helper'
		int SumOfEvenNumbers = 0;
		int[] ReturnValue = new int[2];
		
		for (int i = 1; i < n; i++) {
			c = a;
			a = b;
			b = c + b;

			// add 'a' if 'a' is even
			SumOfEvenNumbers += (a % 2 == 0) ? a : 0; // trying out the ternary operator
		}
		ReturnValue[0] = a;
		ReturnValue[1] = SumOfEvenNumbers;
		return ReturnValue;
	}
}

class UserInput {
	// Get input from user
	public static int GetUserInput() {
		Scanner reader = new Scanner(System.in);
		System.out.print("Give me a number: ");
		
		int i;

		try {
			i = reader.nextInt();
		}
		finally {
			// always close reader
			reader.close();
		}
		return i;
	}
}