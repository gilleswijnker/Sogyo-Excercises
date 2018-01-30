package nl.sogyo.ex7;

import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Quotes {
	// read quotes from file and return as a list: {{author, quote}, ...}
	public List<String[]> readQuotes(String filename, String sep) {
		List<String[]> result = new ArrayList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {	
			String line = null;
			while ((line = br.readLine()) != null)
				result.add(splitAndTrim(line, sep)); 
		}
		catch (IOException ex) {
			System.out.print("Terminating due to error:\n\t");
			System.out.println(ex.getMessage());
			System.exit(0);
		}
		return result;
	}
	
	// split the quote in author and quote
	private String[] splitAndTrim(String text, String sep) {
		String[] splittedText = text.split(sep);
		splittedText[0] = splittedText[0].trim(); 
		splittedText[1] = splittedText[1].trim();
		return splittedText;
	}
	
	public String[] getRandomQuote(String filename, String sep) {
		List<String[]> listOfQuotes = readQuotes(filename, sep);
		int lineToPick = new Random().nextInt(listOfQuotes.size());
		return listOfQuotes.get(lineToPick);
	}
	
	public static void main(String[] args) {
		// Quotes are in quotes.csv, which can be found in the 'main' directory of this project
		String[] quoteOfTheDay = new Quotes().getRandomQuote("quotes.csv", "\t");
		
		// Author name: start each word with a capital
		String author = RegexHelper.replacePattern("(^|\\s)(\\S)", quoteOfTheDay[0], 
			(buffer, matcher) -> {
				String firstLetter = matcher.group(2);
				matcher.appendReplacement(buffer, matcher.group(1) + firstLetter.toUpperCase());
			}
		);
		
		// Quote: starts with a capital and ends with a punctuation
		String quote = RegexHelper.replacePattern("^(\\S)(.*)(.)$", quoteOfTheDay[1], 
				(buffer, matcher) -> {
					String firstLetter = matcher.group(1).toUpperCase(); // Start with a capital
					String closingCharacter = matcher.group(3);
					if (!RegexHelper.endsWith(".?!", closingCharacter))
						closingCharacter += ".";
					matcher.appendReplacement(buffer, firstLetter + matcher.group(2) + closingCharacter);
				}
			);
		
		// Get the date info. Could be in a separate method (which produces the to-be-printed string)
		// but seems pointless in this scenario
		LocalDate today = LocalDate.now();
		String todaysName = today.format(DateTimeFormatter.ofPattern("EEEE"));
		String monthsName = today.format(DateTimeFormatter.ofPattern("MMMM"));
		String todayNum = today.format(DateTimeFormatter.ofPattern("d"));
		
		System.out.println("Quote for " + todaysName + " the " + todayNum + "th of " + monthsName +":");
		System.out.println("\"" + quote + "\" -- " + author);
	}
}
