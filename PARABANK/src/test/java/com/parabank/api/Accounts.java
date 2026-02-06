package com.parabank.api;
import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;

public class Accounts {

	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8080/parabank/services/bank";
		String response= given().pathParam("acct", "13566").header("Accept","application/xml")
		.when().get("accounts/{acct}").then().extract().asString();
		System.out.println(response);

	}

}
