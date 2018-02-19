package nl.sogyo.ex7;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexHelper {
	private RegexHelper() {}
	
	public interface ReplaceMethod {
		public abstract void replace(StringBuffer s, Matcher regexMatcher);  
	}
	
	// replace a pattern according to the method defined in the ReplaceMethod-interface
	public static String replacePattern(String pattern, String text, ReplaceMethod rm) {
		Pattern regex = Pattern.compile(pattern);
		Matcher regexMatcher = regex.matcher(text);		
		StringBuffer s = new StringBuffer();
		
		while (regexMatcher.find()) {
			rm.replace(s, regexMatcher);
		}
		regexMatcher.appendTail(s);
		
		return s.toString();
	}
	
	// does 'text' end with any of the characters given in 'pattern'?
	public static boolean endsWith(String pattern, String text) {
		Pattern regex = Pattern.compile("[" + pattern + "]");
		Matcher regexMatcher = regex.matcher(text);
		return regexMatcher.find(text.length() - 1);
	}
}
