package runner;

import java.util.ArrayList;
import java.util.List;

public class Runner {

	public static void main(String[] args) {
		TestNGRunner testNG = new TestNGRunner(1);
		testNG.createSuite("JPetStore", false);
		testNG.addListener("listener.MyTestNGListener");// later			
		testNG.addTest("VerifyHomePageTitle");
		testNG.addTestParameter("browserName", "chrome");
		testNG.addTestParameter("runmode", "Y");
		List<String> includedMethods = new ArrayList<String>();
		testNG.addTestClass("testcases.HomePageTest", includedMethods);
		includedMethods = new ArrayList<String>();
		includedMethods.add("VerifyHomePageTitle");
		
		testNG.addTest("VerifySignInButtonPresence");
		testNG.addTestParameter("browserName", "chrome");
		testNG.addTestParameter("runmode", "Y");
		includedMethods = new ArrayList<String>();
		testNG.addTestClass("testcases.StoreEntryTest", includedMethods);
		includedMethods = new ArrayList<String>();
		includedMethods.add("VerifySignInButtonPresence");
		includedMethods.add("VerifyStoreEntryTitle");
		includedMethods.add("Verifylogin");
		
		testNG.addTest("verifyAvailablePets");
		testNG.addTestParameter("browserName", "chrome");
		testNG.addTestParameter("runmode", "Y");
		includedMethods = new ArrayList<String>();
		testNG.addTestClass("testcases.DashBoardTest", includedMethods);
		includedMethods = new ArrayList<String>();
		includedMethods.add("verifyAllPets");
		includedMethods.add("verifyAvailablePets");	
		testNG.run();
	}
}
