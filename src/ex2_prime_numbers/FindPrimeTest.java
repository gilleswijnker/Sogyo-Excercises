import java.util.ArrayList;

public class FindPrimeTest {
	// Test class
	public static void main (String[] args) {
		try {
			int n = Integer.parseInt(args[0]);
			System.out.print(FindPrime.Find(n));
		}
		catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Please give me an integer");
		}
	}
}

final class FindPrime {
	private static ArrayList<Long> foundPrimes = new ArrayList<Long>();
	
	private void FindPrime() {};
	
	// Method to find the n-th prime number
	public static long Find(int nthPrimeNumber) {
		long number = 1;

		/*
		Strategy:
		Go until n-th prime number, finding out each prime number on the way
		*/
		for (int i = 1; i <= nthPrimeNumber; i++) {
			while (!IsPrime(++number));
		}
		return number;
	}

	/* Check to see if a given number if prime
	 * Strategy: if a number is NOT a prime, it is always dividable by at least one other prime number
	 */
	private static boolean IsPrime(long n) {
		// loop all already found primes
		for (long i : foundPrimes) {
			if (i*i > n)
				break;
			if (n % i == 0) return false;
		}
		foundPrimes.add(n);
		return true;
	}
}