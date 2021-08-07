package tests;

import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CRUDTest {

    private String userName = "testH@gmail.com";
    private String passWord = "T1234567";

    @Test
    public void create_project_test() {
        String projectName = "This is a test "+System.currentTimeMillis();
        JSONObject body= new JSONObject();
        body.put("Content",projectName);
        body.put("Icon",4);

        Response response = given()
                .auth()
                .preemptive()
                .basic(userName,passWord)
                .body(body.toString())
                .log()
                .all()
                .when()
                .post("http://todo.ly/api/projects.json");

        response
                .then()
                .statusCode(200)
                .body("Content", equalTo(projectName))
                .body("Icon",equalTo(4));

    }

    @Test
    public void update_project_test() {
        String timeMillis = String.valueOf(System.currentTimeMillis());
        String projectName = "This is a test "+timeMillis;
        JSONObject body= new JSONObject();
        body.put("Content",projectName);
        body.put("Icon",4);

        Response response = given()
                .auth()
                .preemptive()
                .basic(userName,passWord)
                .body(body.toString())
                .log()
                .all()
                .when()
                .post("http://todo.ly/api/projects.json");

        response
                .then()
                .statusCode(200)
                .body("Content", equalTo(projectName))
                .body("Icon",equalTo(4));

        int id = response.then().extract().path("Id");
        String updatedName = "This is a updated test "+timeMillis;
        body.put("Content",updatedName);
        body.put("Icon",4);

        response=given()
                .auth()
                .preemptive()
                .basic(userName,passWord)
                .body(body.toString())
                .log()
                .all()
                .when()
                .put("http://todo.ly/api/projects/"+id+".json");

        response.then()
                .statusCode(200)
                .body("Content", equalTo(updatedName))
                .body("Icon",equalTo(4))
                .log()
                .all();
    }

    @Test
    public void delete_project() {
        String projectName = "This is a test "+System.currentTimeMillis();
        JSONObject body= new JSONObject();
        body.put("Content",projectName);
        body.put("Icon",4);

        Response response = given()
                .auth()
                .preemptive()
                .basic(userName,passWord)
                .body(body.toString())
                .log()
                .all()
                .when()
                .post("http://todo.ly/api/projects.json");

        response
                .then()
                .statusCode(200)
                .body("Content", equalTo(projectName))
                .body("Icon",equalTo(4));

        int id = response.then().extract().path("Id");
        response=given()
                .auth()
                .preemptive()
                .basic(userName,passWord)
                .log()
                .all()
                .when()
                .delete("http://todo.ly/api/projects/"+id+".json");

        response.then()
                .statusCode(200)
                .body("Content", equalTo(projectName))
                .body("Icon",equalTo(4))
                .body("Deleted",equalTo(true))
                .log()
                .all();
    }

    @Test
    public void get_project() {
        String projectName = "This is a test "+System.currentTimeMillis();
        JSONObject body= new JSONObject();
        body.put("Content",projectName);
        body.put("Icon",4);

        Response response = given()
                .auth()
                .preemptive()
                .basic(userName,passWord)
                .body(body.toString())
                .log()
                .all()
                .when()
                .post("http://todo.ly/api/projects.json");

        response
                .then()
                .statusCode(200)
                .body("Content", equalTo(projectName))
                .body("Icon",equalTo(4));

        int id = response.then().extract().path("Id");
        response=given()
                .auth()
                .preemptive()
                .basic(userName,passWord)
                .log()
                .all()
                .when()
                .get("http://todo.ly/api/projects/"+id+".json");

        response.then()
                .statusCode(200)
                .body("Content", equalTo(projectName))
                .body("Icon",equalTo(4))
                .log()
                .all();
    }
}
