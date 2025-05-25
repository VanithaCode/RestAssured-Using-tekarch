package restUtilities;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiClient {
	
	public Response getReq(String endpoint, HashMap<String,String>header)
	{
		Response response= RestAssured.given()
				.headers(header).when()
				.get(endpoint);
				
			return response;
	}
	
	public Response postReq(String endpoint, String payload, HashMap<String,String>header)
	{
		Response response= RestAssured.given()
				.headers(header).when()
				.body(payload)
				.post(endpoint);
			return response;
	}
	
	public Response putReq(String endpoint, String payload, HashMap<String,String>header)
	{
		Response response= RestAssured.given()		
				.headers(header).when()
				.body(payload)
				.put(endpoint);
		return response;

	
	}
	public void patchReq()
	{
	
	}
	public Response deleteReq(String endpoint, String payload, HashMap<String,String>header)
	{
		Response response= RestAssured.given()		
				.headers(header).when()
				.body(payload)
				.delete(endpoint);
		return response;
	}
	
}
