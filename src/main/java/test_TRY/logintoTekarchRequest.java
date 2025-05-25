package test_TRY;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class logintoTekarchRequest {
	
	@Test
	
	public void logintoTekarch()
	{
	File file = new File("src/main/resources/RequestData/loginData.json");

	RestAssured.given()
	.contentType(ContentType.JSON)
	.body(file)
	.when()
	.post()
	.then() 
	.statusCode(201)
	.contentType(ContentType.JSON);
	//.time(lessThan(4000L));
	}
}
