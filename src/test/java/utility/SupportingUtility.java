package utility;

public class SupportingUtility {

	public static boolean isNumber(String str) {
		boolean result = false;
		char[] chArray = str.toCharArray();
		for(char c : chArray) {
			if(!Character.isDigit(c)) {
				result = false;
			}
			else {
				result = true;
			}
		}
		return result;	
	}
	
	
	
	
	
	
}
