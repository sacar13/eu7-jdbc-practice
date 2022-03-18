package apitests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class CBTrainingWithJsonPath {

    @BeforeClass
    public void beforeClass(){
        baseURI= ConfigurationReader.get("cbt_api_url");
    }
    @Test
    public void test1(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 24668)
                .when().get("/student/{id}");
        assertEquals(response.statusCode(),200);
        //assign response to jsonpath
        JsonPath jsonPath = response.jsonPath();

        //get values from jsonpath
        String firstName = jsonPath.getString("students.firstName[0]");
        System.out.println("firstName = " + firstName);

        String contactID = jsonPath.getString("students.contact[0].contactId");
        System.out.println("contactID = " + contactID);




    }

}
