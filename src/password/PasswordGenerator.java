package password;

import java.util.ArrayList;
import java.util.Random;

public class PasswordGenerator {

	private int num;
	private int passLength;

	private boolean lowerCase;
	private boolean uperCase;
	private boolean numbers;
	private boolean symbols;
	private boolean similarCharacters;

	private final String UPSER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
	private final String NUMBER = "1234567890";
	private String symbol;

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbols() {
		return symbol;
	}

	public void setLenght(int lenght) {
		this.passLength = lenght;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getLenght() {
		return passLength;
	}

	public int getNum() {
		return num;
	}

	public void isSimilarCharacters(boolean selected) {
		similarCharacters = selected;
	}

	public void isSymbols(boolean selected) {
		symbols = selected;
	}

	public void isNumbers(boolean selected) {
		numbers = selected;
	}

	public void isUperCase(boolean selected) {
		uperCase = selected;
	}

	public void isLowerCase(boolean selected) {
		lowerCase = selected;
	}

	public ArrayList<String> GeneratePassword() {
		Random random = new Random();
		String allChar = "";

		allChar = checkString();

		int length = allChar.length();
		ArrayList<String> passList = new ArrayList<String>();
		

		for (int i = 0; i < num; i++) {

			String pass = "";

			while (pass.length() < passLength) {
				int nextInt = random.nextInt(length);

				if (similarCharacters) {
					pass += allChar.charAt(nextInt) + "";

				} else if (!pass.contains(allChar.charAt(nextInt) + "")) {

					pass += allChar.charAt(nextInt) + "";
				}

			}
			passList.add(pass);

		}
		
		return passList;

	}

	public String checkString() {
		String res = "";
		if (lowerCase) {
			res += LOWER_CASE;
		}
		if (uperCase) {
			res += UPSER_CASE;
		}
		if (numbers) {
			res += NUMBER;
		}
		if (symbols) {
			res += symbol;
		}

		return res;
	}

}
