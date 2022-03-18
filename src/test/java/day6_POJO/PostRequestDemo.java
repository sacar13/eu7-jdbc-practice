package day6_POJO;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.*;

public class PostRequestDemo {

    @BeforeClass
    public void beforeClass() {
        baseURI = ConfigurationReader.get("spartan_api_url");
    }
    /*
    Given accept type and Content type is JSON
    And request json body is:
    {
      "gender":"Male",
      "name":"MikeEU",
      "phone":8877445596
   }
    When user sends POST request to '/api/spartans'
    Then status code 201
    And content type should be application/json
    And json payload/response/body should contain:
    "A Spartan is Born!" message
    and same data what is posted
 */

    @Test
    public void PostNewSpartan() {
        String jsonBody = "{\n" +
                "      \"gender\":\"Male\",\n" +
                "      \"name\":\"MikeEU\",\n" +
                "      \"phone\":8877445596\n" +
                "   }";

        //first content type hey api accept json and second one return me json
        Response response = given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(jsonBody)
                .when()
                .post("api/spartans");
         response.prettyPrint();
        assertEquals(response.statusCode(),201);
        assertEquals(response.contentType(),"application/json");
        //verify successful message
        String actualMessage = response.path("success");
        String expectedMessage = "A Spartan is Born!";
        assertEquals(actualMessage,expectedMessage);

        //assertion for the response body
        String name = response.path("data.name");
        String gender = response.path("data.gender");
        long phone = response.path("data.phone");

        assertEquals(name,"MikeEU");
        assertEquals(gender,"Male");
        assertEquals(phone,8877445596l);

    }
    @Test
    public void postNewSpartan2(){
        //create a map to keep json body information
        Map<String,Object> requestMap = new HashMap<>();
        //adding value that we want to post
        requestMap.put("name","MikeEU2");
        requestMap.put("gender","Male");
        requestMap.put("phone",8877445596l);

        given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                //the body method automatically handle serialize and de- serialize
                .body(requestMap)
                .when()
                .post("api/spartans")
                .then().log().all()
                .statusCode(201)
                .and()
                .contentType("application/json")
                .and()
                .body("success",equalTo("A Spartan is Born!"),
                        "data.name",is("MikeEU2"),
                        "data.gender",equalTo("Male"),
                        "data.phone",is(8877445596l));
    }

    @Test
    public void postNewSpartan3(){

        //Optional homeworks
        //Homework-1
        //1-Create csv file from mackaroo website, which includes name,gender,phone
        //2-Download excel file
        //3- using testng data provider and apache poi create data driven posting from spartan



        //Homework-2
        //-Create one mackaroo api for name,gender,phone
        //send get request to retrieve random info from that api
        //use those info to send post request to spartan

        Spartan spartanEU = new Spartan();
        spartanEU.setName("MikeEU3");
        spartanEU.setGender("Male");
        spartanEU.setPhone(8877445596l);


        given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                //the body method automatically handle serialize and de- serialize
                .body(spartanEU)
                .when()
                .post("api/spartans")
                .then().log().all()
                .statusCode(201)
                .and()
                .contentType("application/json")
                .and()
                .body("success",equalTo("A Spartan is Born!"),
                        "data.name",is("MikeEU3"),
                        "data.gender",equalTo("Male"),
                        "data.phone",is(8877445596l));

        //after post request, send a get request to generated spartan

    }

    @Test
    public void PostNewSpartan4(){

        Spartan spartanEU = new Spartan();
        spartanEU.setName("MikeEU3");
        spartanEU.setGender("Male");
        spartanEU.setPhone(8877445596l);


        Response response = given().log().all()
                .accept(ContentType.JSON)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .body(spartanEU)
                .when()
                .post("/api/spartans");

        //END OF POST REQUEST


        //get request
        int idFromPost = response.path("data.id");
        System.out.println("id = " + idFromPost);
        //after post request, send a get request to generated spartan
        given().accept(ContentType.JSON)
                .pathParam("id",idFromPost)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).log().all();



    }

}
