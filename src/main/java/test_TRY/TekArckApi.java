package test_TRY;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TekArckApi {
	public String token;
	
	private Map<String, String> newInputData = new HashMap();
	
	private Map<String, String> contextData = new HashMap();

	
	@BeforeMethod
	public void init()
	{
		RestAssured.baseURI="https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net/";
	}
	
	@Test(priority = 1)
	
	public void loginTest()
	{
		
		Response res = RestAssured.given().header("Content-Type","application/json").when()
		.body("{\"username\": \"feb2025.vanitha@tekarch.com\", \"password\": \"Admin123\"}").post("login")
		.then().statusCode(201).extract().response();
		
		res.prettyPrint();
		
		res.statusCode();
		
		//assertEquals(res.statusCode(), 201,"Status code match");
		
		token = res.jsonPath().getString("[0].token");
		
		String userid= res.jsonPath().getString("[0].userid");
		System.out.println("token : "+token);
		System.out.println("user id : "+userid);	
		
		contextData.put("token", token);
		contextData.put("userid", userid);
	}
	
	@Test(priority = 2,dependsOnMethods = "loginTest")
	@Parameters("id")
	public void getUserTest(@Optional("0") String id) {
		// read
		Response getData = RestAssured.given().header("Content-Type","application/json")
				.header("Token",token).when()
				.get("getdata").then().statusCode(200).extract().response();
		
		List<Map<String, Object>> records = getData.body().jsonPath().getList("$");
		
		if (!id.equalsIgnoreCase("0")) {
			Map<String, Object> targetRecord = records.stream()
	                .filter(record -> record.get("id").equals(id))
	                .findFirst()
	                .orElse(null);
			
			
		    if (targetRecord != null) {
	            System.out.println("****** Matched Record: **********");
	            System.out.println("Account No: " + targetRecord.get("accountno"));
	            System.out.println("Department No: " + targetRecord.get("departmentno"));
	            System.out.println("Salary: " + targetRecord.get("salary"));
	            System.out.println("Pincode: " + targetRecord.get("pincode"));
	            System.out.println("****** Matched Record: **********");
	        } else {
	            System.out.println("No matching record found for id: " + id);
	        }
		} else {
			System.out.println("No matching record found for id: " + id);
		}
		
		
	}
	
	
	@Test(priority = 3,dependsOnMethods = "loginTest")
	public void addUserTest() {
    	System.out.println("*********** ADD USER *************" );

		newInputData.put("accountno", "TA-teka333");
		newInputData.put("departmentno", "4");
		newInputData.put("salary", "12111122");
		newInputData.put("pincode", "675328");
		
		Gson gson = new Gson();
		String requestJson = gson.toJson(newInputData);
		System.out.println("AddUser: " + requestJson);
		
		// Create 
		Response addData = RestAssured.given().header("Content-Type","application/json")
				.header("Token",token)
				.body(requestJson)
				.when()
				.post("addData").then()
				.log().all() 
				.statusCode(201).extract().response();
		
        	System.out.println("addData Extracted Response: " + addData.asString());
    }
	
	@Test(priority = 4,dependsOnMethods = "loginTest")
	public void updateUser() {
    	System.out.println("*********** UPDATE USER *************" );
		
		Gson gson = new Gson();
		
		
		
		Map<String, Object> foundData = getUser(newInputData.get("accountno"));
		
		foundData.put("salary", "12111133");

		String requestJson = gson.toJson(foundData);
		System.out.println("UpdateUser: " + requestJson);
		
		// Update 
		Response update = RestAssured.given().header("Content-Type","application/json")
				.header("Token",token)
				.body(requestJson)
				.when()
				.put("updateData")
				.then()
	            .log().all() // Logs request and response details
				.statusCode(200).extract().response();
		
		
		getUser(newInputData.get("accountno"));
		
//		printResponseLog(update);
		
//		getUserTest("v5Jn7AfQjyK3WVGglmJp");
		
//		  {
//		        "accountno": "TA-5678333",
//		        "departmentno": "4",
//		        "salary": "45678",
//		        "pincode": "234567",
//		        "userid": "taGX4XQQfXD3Z76sI1Xn",
//		        "id": "v5Jn7AfQjyK3WVGglmJp"
//		    }
	}
	public void deleteUser()
	{
		//Response del
	}
	
	
	public void printResponseLog(Response response) {
		  // Print the HTTP response code
	    int statusCode = response.getStatusCode();
	    System.out.println("HTTP Response Code: " + statusCode);

	    // Check for errors in the response
	    if (statusCode != 200) {
	        String errorMessage = response.body().asString(); // Print the full error response
	        System.out.println("Error Response: " + errorMessage);
	    } 
	}
	
	
	public Map<String, Object> getUser(String accountno) {
		// read
		Response getData = RestAssured.given().header("Content-Type","application/json")
				.header("Token",token).when()
				.get("getdata").then().statusCode(200).extract().response();
		
		List<Map<String, Object>> records = getData.body().jsonPath().getList("$");
		
		if (!accountno.equalsIgnoreCase("0")) {
			Map<String, Object> targetRecord = records.stream()
	                .filter(record -> record.get("accountno").equals(accountno))
	                .findFirst()
	                .orElse(null);
			
			
		    if (targetRecord != null) {
	            System.out.println("****** Matched Record: **********");
	            Gson gson = new GsonBuilder().setPrettyPrinting().create();
	            
	            // Convert map to pretty JSON string
	            String prettyJson = gson.toJson(targetRecord);

	            System.out.println(prettyJson);
	            
        		System.out.println("****** Matched Record: **********");
        		return targetRecord;
        		
	        } else {
	            System.out.println("No matching record found for id: " + accountno);
	        }
		} else {
			System.out.println("No matching record found for id: ");
		}
		return null;
		
		
	}
	

}
