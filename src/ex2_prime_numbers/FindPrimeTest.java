public class FindPrimeTest {
	// Test class
	public static void main (String[] args) {
		try {
			int n = Integer.parseInt(args[0]);
			FindPrime p = new FindPrime(n);
			System.out.print(p.Find());
		}
		catch (NumberFormatException ex) {
			throw new IllegalArgumentException("Please give me an integer");
		}
	}
}

class FindPrime {
	int n;

	/*
	Included constructor for my own educational purposes: to find out how it works
	Sets n to be the n-th prime number to find
	*/
	public FindPrime(int number) {
		n = number;
	}
	
	// Method to find the n-th prime number
	public long Find() {
		if (n == 1) return 2; // first prime number
		
		long k = 1;

		/*
		Strategy:
		Go until n, finding out each prime number on the way
		*/
		for (int i = 1; i < n; i++) {
			while (!IsPrime(++k));
		}
		return k;
	}

	// Check to see if a given number if prime
	boolean IsPrime(long n) {
		// skip all even numbers
		if (n % 2 == 0) return false;
		
		// loop only odd numbers
		for (long i = 3; i*i <= n; i += 2) {
			if (n % i == 0) return false;
		}
		return true;
	}
}