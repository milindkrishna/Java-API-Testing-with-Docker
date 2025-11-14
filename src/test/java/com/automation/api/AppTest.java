package com.automation.api;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * API Test class for sample REST API testing using RestAssured and TestNG.
 * This class demonstrates 4 different API test scenarios using JSONPlaceholder API.
 */
public class AppTest 
{
    private RequestSpecification httpRequest;
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final int SUCCESS_STATUS_CODE = 200;
    private static final int CREATED_STATUS_CODE = 201;
    
    @BeforeClass
    public void setup()
    {
        // Set the base URI
        RestAssured.baseURI = BASE_URL;
        httpRequest = RestAssured.given();
    }
    
    /**
     * Test 1: GET request to retrieve a list of posts
     * Validates: Status code, response body is not empty, and list contains posts
     */
    @Test(description = "Test GET request to retrieve list of posts")
    public void testGetListOfPosts()
    {
        Response response = httpRequest
                .get("/posts")
                .then()
                .assertThat()
                .statusCode(SUCCESS_STATUS_CODE)
                .extract()
                .response();
        
        Assert.assertNotNull(response.jsonPath().getList(""), 
                "Posts list should not be null");
        Assert.assertTrue(response.jsonPath().getList("").size() > 0, 
                "Posts list should not be empty");
        System.out.println("Test 1 Passed: Successfully retrieved posts list with " + 
                response.jsonPath().getList("").size() + " posts");
    }
    
    /**
     * Test 2: GET request to retrieve a single post by ID
     * Validates: Status code, post ID matches, and post contains required fields
     */
    @Test(description = "Test GET request to retrieve single post by ID")
    public void testGetSinglePostById()
    {
        int postId = 5;
        Response response = httpRequest
                .get("/posts/" + postId)
                .then()
                .assertThat()
                .statusCode(SUCCESS_STATUS_CODE)
                .extract()
                .response();
        
        Assert.assertNotNull(response.jsonPath().getInt("id"), 
                "Post ID should not be null");
        Assert.assertEquals(response.jsonPath().getInt("id"), postId, 
                "Post ID should match the requested ID");
        Assert.assertNotNull(response.jsonPath().getString("title"), 
                "Post title should not be null");
        Assert.assertNotNull(response.jsonPath().getString("body"), 
                "Post body should not be null");
        System.out.println("Test 2 Passed: Successfully retrieved post with ID: " + postId);
    }
    
    /**
     * Test 3: POST request to create a new post
     * Validates: Status code is 201 (Created), response contains ID, and title/body are returned
     */
    @Test(description = "Test POST request to create a new post")
    public void testCreateNewPost()
    {
        String requestBody = "{\n" +
                "  \"title\": \"Test Post Title\",\n" +
                "  \"body\": \"This is a test post created using API automation\",\n" +
                "  \"userId\": 1\n" +
                "}";
        
        Response response = httpRequest
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post("/posts")
                .then()
                .assertThat()
                .statusCode(CREATED_STATUS_CODE)
                .extract()
                .response();
        
        Assert.assertNotNull(response.jsonPath().getInt("id"), 
                "Created post should have an ID");
        Assert.assertEquals(response.jsonPath().getString("title"), "Test Post Title", 
                "Post title should match the request");
        Assert.assertEquals(response.jsonPath().getString("body"), 
                "This is a test post created using API automation", 
                "Post body should match the request");
        System.out.println("Test 3 Passed: Successfully created post with ID: " + 
                response.jsonPath().getInt("id"));
    }
    
    /**
     * Test 4: PUT request to update an existing post
     * Validates: Status code is 200, updated fields are returned correctly
     */
    @Test(description = "Test PUT request to update an existing post")
    public void testUpdateExistingPost()
    {
        String requestBody = "{\n" +
                "  \"id\": 1,\n" +
                "  \"title\": \"Updated Post Title\",\n" +
                "  \"body\": \"This post has been updated via API automation\",\n" +
                "  \"userId\": 1\n" +
                "}";
        
        int postId = 1;
        Response response = httpRequest
                .header("Content-Type", "application/json")
                .body(requestBody)
                .put("/posts/" + postId)
                .then()
                .assertThat()
                .statusCode(SUCCESS_STATUS_CODE)
                .extract()
                .response();
        
        Assert.assertEquals(response.jsonPath().getString("title"), "Updated Post Title", 
                "Updated title should match the request");
        Assert.assertEquals(response.jsonPath().getString("body"), 
                "This post has been updated via API automation", 
                "Updated body should match the request");
        Assert.assertEquals(response.jsonPath().getInt("id"), postId, 
                "Post ID should remain the same");
        System.out.println("Test 4 Passed: Successfully updated post with ID: " + postId);
    }
}
