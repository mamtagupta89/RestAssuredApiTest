import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiCallTest {

    //Test case 1 to Create a user with given details
    @Test
    void createUser() throws JSONException {

        Map<String, Object>  userMap = new HashMap<>();

        userMap.put("email", "test5@example.com");
        userMap.put("password", "mySecretPass123");
        userMap.put("username", "usrname-6");


        ResponseBody response = getResponseBody(userMap,"https://api.m3o.com/v1/user/Create");

        System.out.println(response.prettyPrint());

    }

    //Test case 2 : Search user with given user Id
    @Test
    public void getUser(){
        Map<String, Object>  userMap = new HashMap<>();

        userMap.put("id","eb978f9b-c237-4239-9642-58a7b0f867ef");
        ResponseBody response2 = getResponseBody(userMap,"https://api.m3o.com/v1/user/Read");
        System.out.println(response2.prettyPrint());
    }

    //Test case 3: Delete user with provided user Id
    @Test
    public void deleteUser(){
        Map<String, Object>  userMap = new HashMap<>();
        userMap.put("id","eb978f9b-c237-4239-9642-58a7b0f867ef");
        Response response2 = getResponseBody(userMap,"https://api.m3o.com/v1/user/Delete");

        System.out.println(response2.getStatusCode());
    }

    //Test case 4: User login with correct user Details
    @Test
    public void testLoginWithCorrectCredentials(){
        Map<String, Object>  userMap = new HashMap<>();
        userMap.put("email", "test2@example.com");
        userMap.put("password", "mySecretPass123");
        Response response2 = getResponseBody(userMap,"https://api.m3o.com/v1/user/Login");

        Assert.assertEquals(response2.getStatusCode(), 200);
    }

    //Test case 5: User login with incorrect user Details
    @Test
    public void testLoginWithInCorrectCredentials(){
        Map<String, Object>  userMap = new HashMap<>();
        userMap.put("email", "test2@example.com");
        userMap.put("password", "mySecretPass1234");

        Response response3 = getResponseBody(userMap,"https://api.m3o.com/v1/user/Login");

        Assert.assertEquals(response3.getStatusCode(), 401);


    }

    //Test case 6: update user password
    @Test
    public void testUserUpdatePassword(){
        Map<String, Object>  userMap = new HashMap<>();
        userMap.put("confirmPassword","Pass@123");
        userMap.put("newPassword","Pass@123");
        userMap.put("oldPassword","mySecretPass123");
        userMap.put("userId", "eb978f9b-c237-4239-9642-58a7b0f867ef");

        Response response = getResponseBody(userMap, "https://api.m3o.com/v1/user/UpdatePassword");
        Assert.assertEquals(response.getStatusCode(), 200);

    }


    private Response getResponseBody(Map<String, Object> userMap, String requestLink) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "Bearer NzJiMWQyMzktOGIzZi00YTA1LTgzODctNjJmMzNiZTFhYjRi");

        Response response =  given().accept(ContentType.JSON).
                        contentType(ContentType.JSON).
                        headers(headerMap).
                                body(userMap).
                                        when().
                                        post(requestLink).
                                        thenReturn();


        return response;
    }
}
