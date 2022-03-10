package apitests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class SpartanTestWithPath {

    @BeforeClass
    public void beforeClass(){
        baseURI="http://18.212.211.128:8000";
    }

     /*
          Given accept type is json
          And path param spartan id is 11
          When user sends a get request to /spartans/{id}
         Then status code is 200
         And content type is Json
         And   "id": 11,
               "name": "Nona",
              "gender": "Female",
              "phone": 7959094216
    */
     @Test
     public void test1(){
         Response response = given().accept(ContentType.JSON)
                 .and().pathParam("id", 10)
                 .when().get("api/spartans/{id}");
         assertEquals(response.statusCode(),200);
         assertEquals(response.contentType(),"application/json");
         //response.prettyPrint();

         //printing each key value in the body/payload
         System.out.println(response.body().path("id").toString());
         System.out.println(response.body().path("name").toString());
         System.out.println(response.body().path("gender").toString());
         System.out.println(response.body().path("phone").toString());

         //save jason key values
         int id = response.path("id");
         String name = response.path("name");
         String gender = response.path("gender");
         long phone = response.path("phone");

         System.out.println("id = " + id);
         System.out.println("name = " + name);
         System.out.println("gender = " + gender);
         System.out.println("phone = " + phone);

         System.out.println("========================");

         //assert one by one
         assertEquals(id,10);
         assertEquals(name,"Lorenza");
         assertEquals(gender,"Female");
         assertEquals(phone,3312820936l);
     }

  @Test
  public void getAllSpartanWithPath(){
      Response response = given().accept(ContentType.JSON)
              .when().get("/api/spartans");
      assertEquals(response.statusCode(),200);
      assertEquals(response.contentType(),"application/json");

      int firstID = response.path("id[0]");
      System.out.println("firstID = " + firstID);

      String firstName = response.path("name[0]");
      System.out.println("firstName = " + firstName);

      String lastName = response.path("name[-1]");
      System.out.println("lastName = " + lastName);

      int lastID = response.path("id[-1]");
      System.out.println("lastID = " + lastID);

      System.out.println("================");
      
      //print all names of spartans
      List<String> names = response.path("name");
      System.out.println("names = " + names);

      //print all phones of spartans
      List<Object> phones = response.path("name");
      for (Object phone : phones) {
          System.out.println("phone = " + phone);
      }



     }
    

}
