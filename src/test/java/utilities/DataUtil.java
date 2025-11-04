package utilities;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class DataUtil extends testbase.BaseTest {

	public Map<String,String> loadClassMethods() throws FileNotFoundException, IOException, ParseException {
		Map<String,String> classMethodMap = new HashMap<String,String>();
		String path=System.getProperty("user.dir")+"//src//test//resources//jsons//classmethods.json";
		JSONParser parser = new JSONParser();
		JSONObject json=(JSONObject)parser.parse(new FileReader(new File(path)));
		JSONArray classDetails = (JSONArray)json.get("classdetails");//jsonarray
		for(int cMId=0;cMId<classDetails.size();cMId++) {
			JSONObject classDetail = (JSONObject)classDetails.get(cMId);
			String className= (String)classDetail.get("class");
			JSONArray methods= (JSONArray)classDetail.get("methods");
			//System.out.println(className);
			for(int mId=0;mId<methods.size();mId++) {
				String method=(String)methods.get(mId);
				classMethodMap.put(method, className);
			}
			System.out.println("-----------------");
		}
		
		System.out.println(classMethodMap);
		return classMethodMap;

	}
	
	public int getTestDataSets(String pathFile,String dataFlag) throws FileNotFoundException, IOException, ParseException {
		
		JSONParser parser = new JSONParser();
		JSONObject json=(JSONObject)parser.parse(new FileReader(new File(pathFile)));
		JSONArray testDataSets = (JSONArray)json.get("testdata");
		for(int dSetId=0;dSetId<testDataSets.size();dSetId++) {
			JSONObject testData = (JSONObject)testDataSets.get(dSetId);
			String flag = (String)testData.get("flag");
			if(dataFlag.equals(flag)) {
				JSONArray dataSets = (JSONArray)testData.get("data");
				System.out.println(dataSets);
				return dataSets.size();
			}
		}
		return -1;
		
	}
	
	public JSONObject getTestData(String pathFile,String dataFlag,int iteration) throws FileNotFoundException, IOException, ParseException{
		JSONParser parser = new JSONParser();
		JSONObject json=(JSONObject)parser.parse(new FileReader(new File(pathFile)));
		JSONArray testDataSets = (JSONArray)json.get("testdata");
		for(int dSetId=0;dSetId<testDataSets.size();dSetId++) {
			JSONObject testData = (JSONObject)testDataSets.get(dSetId);
			String flag = (String)testData.get("flag");
			if(dataFlag.equals(flag)) {
				JSONArray dataSets = (JSONArray)testData.get("data");
				System.out.println("JSon data start here................................");
				System.out.println(dataSets.toArray()[0]);
				JSONObject data = (JSONObject)dataSets.get(0);
				System.out.println(data.get("browsername"));
				System.out.println(data.get("runmode"));
				System.out.println(data.get("username"));
				System.out.println(data.get("password"));
				return data;
			}
		}
		return null;
	}
	
	
public static void main (String [] args) throws FileNotFoundException, IOException, ParseException {
	
	DataUtil datautil =  new DataUtil();
	datautil.getTestData("C:\\Users\\bkumar\\eclipse-workspace\\DataDrivenFramework_Javab_JsonRunner\\src\\test\\resources\\jsons\\data\\jpetstoredata.json", "VerifyStoreEntryTitle", 0);
	
	
	
}
}
