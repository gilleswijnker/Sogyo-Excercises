public class LeapYear {
	public static void main (String[] args) {
		// Exactly one argument has to be given
		if (args.length != 1) {
			System.out.print("Give one argument.");
		} else {
			try {
				int year = Integer.parseInt(args[0]);
				String leapyear = "not a";
				
				// Leap year when dividable by four, but not by one hundred
				if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) {
					leapyear = "a";
				}
				System.out.print("The year " + year + " is " + leapyear + " leap year.");
			}
			catch (NumberFormatException ex) {
				// Supplied cannot be converted to an integer
				System.out.print("You'll have to give me a number, not \"" + args[0] + "\".");
			}
		}
	}
}