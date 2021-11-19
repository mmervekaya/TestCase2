package com.groceryAPI.tests;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetGroceryStockAPI {
	
	String url="https://grocery.com.tr";
	String getPath="/allGrocery";
	String postPath="/add";
	RequestSpecification request = RestAssured.given();
	
	@Test
	public void getSuccesfullAllStock() {
		Response response = request.get(getPath);
		
		int status = response.getStatusCode();
		Assert.assertEquals(200, status);
		
		String name = response.jsonPath().getString("name[0]");
		Assert.assertEquals("apple", name);
	}
	
	@Test
	public void getOneThingStock() {
		Response response = request.get(getPath + "/apple");
		int status = response.getStatusCode();
		Assert.assertEquals(200, status);
		
		String name = response.jsonPath().getString("name");
		Assert.assertEquals("apple", name);
	}
	
	@Test
	public void getDiffirentThingStock() {
		Response response = request.get(getPath + "/banana");
		int status = response.getStatusCode();
		Assert.assertEquals(400, status);
		
	}
	
	@Test
	public void addVegatablesAndFruits() {
		
		Map<String, Object> product = new HashMap();
		product.put("id", "4"); 
		product.put("name", "banana");
		product.put("price", "12.3");
		product.put("stock", "3");
		
		JSONObject requestParams = new JSONObject(product);
		
		
		request.header("Content-Type", "application/json");
		request.body(requestParams);
		
		Response response = request.post(postPath);
		int status = response.getStatusCode();
		Assert.assertEquals(201, status);
		
	}
	
	@Test
	public void addExistVegetablesAndFruits() {
		Map<String, String> product = new HashMap();
		product.put("id", "4"); 
		product.put("name", "grapes");
		product.put("price", "12.3");
		product.put("stock", "3");
		
		JSONObject requestParams = new JSONObject(product);
		
		
		request.header("Content-Type", "application/json");
		request.body(requestParams);
		
		Response response = request.post(postPath);
		int status = response.getStatusCode();
		Assert.assertEquals(409, status);
	}

}
