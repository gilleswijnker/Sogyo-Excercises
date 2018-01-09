import java.util.Scanner;

public class FibonacciTest {
	public static void main (String[] args) {
		Fibonacci f = new Fibonacci();
		f.PrintFibonacciNumber(UserInput.GetUserInput());
	}
}

class Fibonacci {
	private int numberToFind;
	private int sumOfEvenNumbers;

	// Print the n-th Fibonacci number and the sum of all even Fibonacci numbers up to the n-th
	public void PrintFibonacciNumber(int n) {
		int fib = GetFibonacciNumber(n);
		System.out.println("The value of the " + n + "th term of the Fibonacci sequence is " + fib);
		System.out.println("The sum of even values of the first " + n + " terms is " + sumOfEvenNumbers);
	}
	
	// Get the n-th Fibonacci number
	public int GetFibonacciNumber(int n) {
		numberToFind = n;
		return Fibonacci(0, 1, 1);
	}
	
	// Recursive Fibonacci function
	private int Fibonacci(int a, int b, int n) {
		if (a % 2 == 0) sumOfEvenNumbers += a; // sum numbers when even
		if (n == numberToFind) return a;
		return Fibonacci(b, a + b, n + 1);
	}
}

final class UserInput {
	private UserInput() {};
	
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