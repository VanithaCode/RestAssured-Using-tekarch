 package test_TRY;

import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import restUtilities.ApiClient;

public class TekArckApiMethodReusablity {
	public String token = null;
	ApiClient  api= new ApiClient();
	

	
	
	@Test(priority = 1)
	public void loginTest()
	{
		RestAssured.baseURI="https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net";
		
		HashMap<String,String> headers = new HashMap<String,String>();
		headers.put("Content-Type","application/json");
		
		String endpoint="login";
		String payload="{\"username\": \"feb2025.vanitha@tekarch.com\", \"password\": \"Admin123\"}";
		
		Response res =  api.postReq(endpoint, payload, headers);
		
//		Response res = RestAssured.given().header("Content-Type","application/json").when()
//		.body("{\"username\": \"feb2025.vanitha@tekarch.com\", \"password\": \"Admin123\"}").post("login")
//		.then().statusCode(201).extract().response();
		
		res.prettyPrint();
		res.statusCode();
		
		//assertEquals(res.statusCode(), 201,"Status code match");
		
		 token = res.jsonPath().getString("[0].token");
		
		String userid= res.jsonPath().getString("[0].userid");
		System.out.println("token : "+token);
		System.out.println("user id : "+userid);		
	}
	
	@Test(priority = 2,dependsOnMethods = "loginTest")
	public void getUserTest() {
		
		HashMap<String,String> headers = new HashMap<String,String>();
		headers.put("Content-Type","application/json");
		
		String endpoint="getdata";
		String payload="{\"username\": \"feb2025.vanitha@tekarch.com\", \"password\": \"Admin123\"}";
		Response res =  api.postReq(endpoint, payload, headers);
		
//		Response getData = RestAssured.given().header("Content-Type","application/json")
//				.header("Token",token).when()
//				.get("getdata").then().statusCode(200).extract().response();
//		getData.prettyPrint();
		
		
		
		//Assert.assertEquals(getData.statusCode(), 200,"getdata status code is 200 matched");		
	}
	
	
	@Test(priority = 3,dependsOnMethods = "loginTest")
	public void addUserTest() {
		
		HashMap<String,String> headers = new HashMap<String,String>();
		headers.put("Content-Type","application/json");
		String endpoint="adddata";
		String payload="{\"username\": \"feb2025.vanitha@tekarch.com\", \"password\": \"Admin123\"}";
		Response res =  api.postReq(endpoint, payload, headers);
		
		
//		Response addData = RestAssured.given().header("Content-Type","application/json")
//				.header("Token",token)
//				.body("{\"accountno\": \"TA-teka001\", \"departmentno\": \"4\", \"salary\": \"11111111\", \"pincode\": \"675328\"}")
//				.when()
//				.get("addData").then()
//				.statusCode(201).extract().response();
		res.statusCode();	
	}

}
