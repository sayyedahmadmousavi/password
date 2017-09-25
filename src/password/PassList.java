package password;

import java.util.ArrayList;

public class PassList {
	
	static ArrayList<String> passList = new ArrayList<String>();
	public static void setPassList(ArrayList<String> pass) {
		passList = pass;
	}
	public static ArrayList<String> getPassList() {
		return passList;
	}

}
