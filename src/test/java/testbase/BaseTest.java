package testbase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import reports.ExtentManager;


import utilities.DataUtil;


// how to configure and run on grid - 4 alpha 6  3.141.59
// how to manage data from xls or json
// how to run this with JSON config
// Running from GIT and Jenkins

public class BaseTest {
	
	public ApplicationKeywords app;
	public ExtentReports rep;
	public ExtentTest test;
	//public String datafilepath = "C:\\Users\\MSI\\eclipse-workspace\\DataDrivenFramework_Javab_JsonRunner\\src\\test\\resources\\jsons\\data\\jpetstoredata.json";
	
	
	@BeforeTest(alwaysRun = true)
	public void beforeTest(ITestContext context) throws NumberFormatException, FileNotFoundException, IOException, ParseException {
		
		System.out.println("----------Before Test I am running---------");
		String datafilepath = context.getCurrentXmlTest().getParameter("datafilepath");	
		String dataflag = context.getCurrentXmlTest().getParameter("dataflag");
		String iteration = context.getCurrentXmlTest().getParameter("iteration");
		String dataFlag = context.getCurrentXmlTest().getName();
		
		System.out.println(datafilepath);
		System.out.println(dataFlag);
		System.out.println(iteration);
		// reading data from JSON
		JSONObject data = new DataUtil().getTestData(datafilepath, dataflag, Integer.parseInt(iteration));		
		context.setAttribute("data", data);
		String runmode = (String)data.get("runmode");


		//System.out.println(sheetName);

		
		// init the reporting for the test
		rep = ExtentManager.getReports();
		test =rep.createTest(context.getCurrentXmlTest().getName());
		test.log(Status.INFO, "Starting Test "+context.getCurrentXmlTest().getName());
		//test.log(Status.INFO, "Data "+data.toString());

		context.setAttribute("report", rep);
		context.setAttribute("test", test);
        if(!runmode.equals("Y")) {
        	test.log(Status.SKIP, "Skpping as Data Runmode is N");
        	throw new SkipException("Skpping as Data Runmode is N");
		}		
		// init and share it with all tests
		app = new ApplicationKeywords(); // 1 app keyword object for entire test -All @Test
        app.setReport(test);
        context.setAttribute("app", app);
		
	}
	
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(ITestContext context) {
		//System.out.println("****Before Method****");
	    test = (ExtentTest)context.getAttribute("test");

		String criticalFailure = (String)context.getAttribute("criticalFailure");
		if(criticalFailure != null && criticalFailure.equals("Y")) {
			test.log(Status.SKIP, "Critical Failure in Prevoius Tests");
			throw new SkipException("Critical Failure in Prevoius Tests");// skip in testNG
		}
	    app = (ApplicationKeywords)context.getAttribute("app");
	    rep = (ExtentReports)context.getAttribute("report");
	   // System.out.println("*******Before Methods Ends Here*****************");
	}
	
	@AfterTest(alwaysRun = true)
	public void quit(ITestContext context) {
		//app = (ApplicationKeywords)context.getAttribute("app");
		//System.out.println("I am reaching here above null");
		if(app!=null) {
			//System.out.println("I am reaching here below null");
			app.quit();
		}
		
		rep = (ExtentReports)context.getAttribute("report");

		if(rep !=null)
			rep.flush();	
		
		File sourceDir = new File(System.getProperty("user.dir") + "//ExtentReports//" + ExtentManager.ForLatest);
		File targetDir = new File(System.getProperty("user.dir") + "//ExtentReports//Latest");

		if (targetDir.isDirectory() && targetDir.listFiles().length > 0) {
			try {
				FileUtils.cleanDirectory(targetDir);
				System.out.println("Directory was not empty and contents deleted.");
			} catch (IOException e) {
				System.err.println("Error cleaning directory: " + e.getMessage());
			}
		}

		try {
			FileUtils.copyDirectory(sourceDir, targetDir);
			System.out.println("Folder contents copied successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	

}
