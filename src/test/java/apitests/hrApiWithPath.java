package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class hrApiWithPath {

    @BeforeClass
    public void beforeClass(){
        baseURI= ConfigurationReader.get("hr_api_url");
    }

    @Test
    public void getCountriesWithPath(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q", "{\"region_id\":2}")
                .when().get("countries");
        assertEquals(response.statusCode(),200);

        //print the limit value
        System.out.println("response.path(\"limit\") = " + response.path("limit"));

        //print the  hasmMore value
        System.out.println("hasMore = " + response.path("hasMore"));

        //print the country_id
        System.out.println("response.path(\"items.country_id[0]\").toString() = " + response.path("items.country_id[0]").toString());

        //print second country name
        System.out.println("response.path(\"items.country_id[1].country_name\") = " + response.path("items.country_name[1]"));
        //print third country id
        System.out.println(response.path("items.country_id[3]").toString());

        //Print third href
        System.out.println("third href = " + response.path("items.links[2].href[0]"));

        System.out.println("==============================");

        //assert that all regions' ids are equal to 2
        List<Integer> region_ids = response.path("items.region_id");
        for (Integer region_id : region_ids) {
            assertTrue(region_id.equals(2));
        }

    }

    @Test
    public void test2(){
        Response response = given().accept(ContentType.JSON)
                .and().queryParam("q","{\"job_id\":\"IT_PROG\"}")
                .when().get("/employees");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertTrue(response.body().asString().contains("IT_PROG"));


        // make sure we have only IT_PROG as a job_id
        List<String> itJobIDS = response.path("items.job_id");
        for (String it : itJobIDS) {
            assertEquals(it,"IT_PROG");
        }


    }
}
