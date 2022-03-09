package apitests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SimpleGetRequest {

    String hrUrl = "http://18.212.211.128:1000/ords/hr/regions";

    @Test
    public void test1(){
        Response response = RestAssured.get(hrUrl);// returning response format that is why we kept in into response
        //print the status code
        System.out.println("response.statusCode() = " + response.statusCode());
        //print yhe json body
        response.prettyPrint();
    }

    /*
        Given accept type is json
        When user sends get request to regions endpoint
        Then response status code must be 200
        And body is json format
        */
    @Test
    public void test2(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(hrUrl);
        //verify response status code is 200
        Assert.assertEquals(response.statusCode(),200);

        //verify content type is application/json
        System.out.println(response.contentType());
        Assert.assertEquals(response.contentType(),"application/json");

    }

    /*
        Given accept type is json
        When user sends get request to regions endpoint
        Then response status code must be 200
        And body is json format
        */
    //same test case with restassured in one line
    @Test
    public void test3(){
        RestAssured.given().accept(ContentType.JSON)
                .when().get(hrUrl).then()
                .assertThat().statusCode(200)
                .and().contentType("application/json");

    }

    /*
    * Given accept type is json
    * When user sends get request to regions
    * Then response status code must be 200
    * And body is json format
    * and response body contains Americas*/
@Test
public void test4(){
    Response response = RestAssured.given().accept(ContentType.JSON)
            .when().get(hrUrl + "/2");
    Assert.assertEquals(response.statusCode(),200);
    Assert.assertEquals(response.contentType(),"application/json");

    //verify body contain Americas
    // with the help of using asString() method we can easily convert to string all body content
    Assert.assertTrue(response.body().asString().contains("Americas"));


}


}
