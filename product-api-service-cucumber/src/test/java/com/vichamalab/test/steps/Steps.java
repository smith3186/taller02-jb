package com.vichamalab.test.steps;

import com.vichamalab.test.dto.ProductRequest;
import com.vichamalab.test.utils.Utils;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Steps {
    private String requestBody;
    private Response response;
    RequestSpecBuilder builder = new RequestSpecBuilder();

    @Given("una API válida")
    public void una_api_válida() {

        RestAssured.baseURI = "http://localhost:8081";
        builder.addHeader("Authorization", "Bearer + aGFzaGRzZnNkZnNkZnNkZnNk");


    }

    @When("se hace una petición con el nombre {string}")
    public void se_hace_una_petición_con_el_nombre(String nombre) {
        requestBody = "{ \"name\": \"" + nombre + "\",";


    }

    @When("la descripción {string}")
    public void la_descripción(String description) {
        requestBody += "\"description\": \"" + description + "\",";

    }

    @When("el precio {float}")
    public void el_precio(Float price) {

        requestBody += "\"price\": " + price + " }";


    }

    @When("a la url {string}")
    public void a_la_url(String string) {
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/v1/product/");


        System.out.println("Request: " + requestBody);

    }

    @Then("se recibe una respuesta exitosa con código {int}")
    public void se_recibe_una_respuesta_exitosa_con_código(Integer statusCode) {
        response.then().extract().response().statusCode();

    }

    @Then("el mensaje {string}")
    public void el_mensaje(String message) {
        response.then().body("message", equalTo(message));

    }

}
