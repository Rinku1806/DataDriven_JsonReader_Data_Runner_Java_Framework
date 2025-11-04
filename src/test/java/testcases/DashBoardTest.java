package testcases;

import java.util.ArrayList;

import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.Test;

import utilities.DataUtil;

public class DashBoardTest extends testbase.BaseTest {

	// @Test(dataProviderClass = DataUtil.class, dataProvider = "dp")
	@Test
	public void verifyAvailablePets(ITestContext context) {
		JSONObject data = (JSONObject) context.getAttribute("data");
		String browserName = (String) data.get("browsername");
		String runmode = (String) data.get("runmode");
		String username = (String) data.get("username");
		String pwd = (String) data.get("password");

		if (runmode.equals("N")) {
			test.info("Test Skipped");
			throw new SkipException("Skipping the test as the run mode is NO");
		} else {
			try {
				app.openBrowser(browserName);
				app.navigate("TEST_URL");
				app.wait(1);
				app.click("ENTER_STORE_LINK_xpath");
				app.wait(2);
				app.click("SIGNIN_LINK_xpath");
				app.Login(username, pwd);
				WebElement availablePetsTable = app.driver.findElement(By.xpath("//*[@id='SidebarContent']"));
				java.util.List<WebElement> tableContents = availablePetsTable.findElements(By.tagName("a"));
				for (WebElement i : tableContents) {
					System.out.println(i.findElement(By.tagName("img")).getText());
				}
				/*
				 * app.softAssert.assertEquals(app.getElement("BIRDS_LOCATOR_xpath").
				 * getAttribute("href").split("=")[2], app.envProp.getProperty("BIRDS_TEXT"));
				 * test.info("Validation of birds in the store completed");
				 * app.softAssert.assertEquals(app.getElement("CATS_LOCATOR_xpath").getAttribute
				 * ("href").split("=")[2], app.envProp.getProperty("CATS_TEXT"));
				 * test.info("Validation of cats in the store completed");
				 * app.softAssert.assertEquals(app.getElement("DOGS_LOCATOR_xpath").getAttribute
				 * ("href").split("=")[2], app.envProp.getProperty("DOGS_TEXT"));
				 * test.info("Validation of dogs in the store completed");
				 * app.softAssert.assertEquals(app.getElement("FISH_LOCATOR_xpath").getAttribute
				 * ("href").split("=")[2], app.envProp.getProperty("FISH_TEXT"));
				 * test.info("Validation of fish in the store completed");
				 * app.softAssert.assertEquals(app.getElement("REPTILES_LOCATOR_xpath").
				 * getAttribute("href").split("=")[2],
				 * app.envProp.getProperty("REPTILES_TEXT"));
				 * test.info("Validation of reptiles in the store completed");
				 */
				// app.assertAll();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@Test
	// @Test(dataProviderClass = DataUtil.class, dataProvider = "dp")

	public void verifyAllPets(ITestContext context) throws InterruptedException {

		JSONObject data = (JSONObject) context.getAttribute("data");
		String browserName = (String) data.get("browsername");
		String runmode = (String) data.get("runmode");
		String username = (String) data.get("username");
		String pwd = (String) data.get("password");

		if (runmode.equals("N")) {
			test.info("Test skipped");
			throw new SkipException("Skipping the test as the run mode is NO");
		} else {

			app.openBrowser(browserName);
			app.navigate("TEST_URL");
			app.wait(1);
			app.click("ENTER_STORE_LINK_xpath");
			app.wait(2);
			app.click("SIGNIN_LINK_xpath");
			app.Login(username, pwd);
			app.click("BIRDS_LOCATOR_xpath");
			app.verifyTableEntities(app.getElement("BIRDTABLE_LOCATOR_xpath"),
					app.envProp.getProperty("EXPECTED_PET_BIRDS").split(","));
			app.returnToMainMenu();
			test.info("Validation of all breeds of birds in the store completed");
			app.click(app.envProp.getProperty("CATS_LOCATOR_xpath"));
			app.verifyTableEntities(app.getElement("CATS_LOCATOR_xpath"),
					app.envProp.getProperty("EXPECTED_PET_CATS").split(","));
			app.returnToMainMenu();
			test.info("Validation of all breeds of cats in the store completed");
			app.click(app.envProp.getProperty("DOGS_LOCATOR_xpath"));
			app.verifyTableEntities(app.getElement("DOGS_LOCATOR_xpath"),
					app.envProp.getProperty("EXPECTED_PET_DOGS").split(","));
			app.returnToMainMenu();
			test.info("Validation of all breeds of dogs in the store completed");
			app.click(app.envProp.getProperty("REPTILES_LOCATOR_xpath"));
			app.verifyTableEntities(app.getElement("REPTILES_LOCATOR_xpath"),
					app.envProp.getProperty("EXPECTED_PET_REPTILES").split(","));
			app.returnToMainMenu();
			test.info("Validation of all breeds of reptiles in the store completed");
			app.click(app.envProp.getProperty("FISH_LOCATOR_xpath"));
			app.verifyTableEntities(app.getElement("FISHTABLE_LOCATOR_xpath"),
					app.envProp.getProperty("EXPECTED_PET_FISH").split(","));
			app.returnToMainMenu();
			test.info("Validation of all breeds of fish in the store completed");
		}
		// app.assertAll(); }

	}
}
