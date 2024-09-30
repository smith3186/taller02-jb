package com.vichamalab.api.producto.steps;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.slf4j.LoggerFactory;

import com.vichamalab.api.producto.dto.Product;
import com.vichamalab.api.producto.dto.ProductResponse;
import com.vichamalab.api.producto.hooks.GlobalHook;
import com.vichamalab.api.producto.hooks.ScenarioHook;
import com.vichamalab.api.producto.requests.APIProducto;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dada;
import io.cucumber.java.es.Entonces;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Steps {
    Response response;
    public static String originalSku = "ORIGINAL";
    String currentMethod = "";
    String nombreEsperado = "";
    String descripcionEsperada = "";
    float precioEsperado = 0;
    String currentUrl = "";
    private static final Logger logger = LoggerFactory.getLogger(ScenarioHook.class);

    @Dada("una API válida con ruta {string} y formato {string}")
    public void una_api_válida_con_url_y_formato(String ruta, String contentType) {
        currentUrl = ruta;
    }

    @Cuando("se hace una petición {string} con el nombre {string}")
    public void se_hace_una_petición_con_el_nombre(String metodo, String nombre) {
        GlobalHook.productRequest.setName(nombre);
        currentMethod = metodo;
    }

    @Cuando("se hace una petición con el nombre {string}")
    public void se_hace_una_petición_con_el_nombre(String nombre) {
        GlobalHook.productRequest.setName(nombre);
    }

    @Cuando("y la descripción {string}")
    public void y_la_descripción(String description) {
        GlobalHook.productRequest.setDescription(description);
    }

    @Cuando("y el precio {float}")
    public void y_el_precio(float price) {
        GlobalHook.productRequest.setPrice(price);
    }

    @Cuando("a la API")
    public void a_la_api() {
        if ("POST".equals(currentMethod.toUpperCase())) {
            response = APIProducto.crearProducto(currentUrl, GlobalHook.productRequest);
        } else if ("PUT".equals(currentMethod.toUpperCase())) {
            nombreEsperado = GlobalHook.productRequest.getName();
            descripcionEsperada = GlobalHook.productRequest.getDescription();
            precioEsperado = GlobalHook.productRequest.getPrice();
            response = APIProducto.actualizarProducto(currentUrl, originalSku, GlobalHook.productRequest);
        } else if ("DELETE".equals(currentMethod.toUpperCase())) {
            response = APIProducto.eliminarProducto(currentUrl, originalSku);
        }
    }

    @Entonces("se recibe una respuesta {string} con código {int}")
    public void se_recibe_una_respuesta_de_error_con_código(String responseType, Integer statusCode) {
        Assertions.assertEquals(statusCode, response.getStatusCode());
    }

    @Entonces("y el mensaje {string}")
    public void y_el_mensaje(String message) {
        Assertions.assertEquals(message, response.jsonPath().getString("message"));
    }

    @Dada("un producto con nombre {string}")
    public void un_producto_con_nombre(String nombre) {
        GlobalHook.productRequest.setName(nombre);
    }

    @Dada("previamente creado con exito usando la ruta {string} y metodo {string}")
    public void previamente_creado_con_exito_usando_la_ruta_y_metodo(String ruta, String metodo) {
        currentUrl = "/api/v1/product/";
        if (!GlobalHook.inicializado) {
            response = APIProducto.crearProducto(currentUrl, GlobalHook.productRequest);
            originalSku = response.jsonPath().getString("sku");
            GlobalHook.inicializado = true;
        }
    }

    @Entonces("con los campos actualizados correctamente")
    public void con_los_campos_actualizados_correctamente() {
        ProductResponse response = APIProducto.recuperarProducto(currentUrl, originalSku);
        Product product = response.getProducts().get(0);
        Assertions.assertEquals(nombreEsperado, product.getName());
        Assertions.assertEquals(descripcionEsperada, product.getDescription());
        Assertions.assertEquals(precioEsperado, product.getPrice());
    }

    @Dada("previamente creado con exito")
    public void previamente_creado_con_exito() {
        currentUrl = "/api/v1/product/";
        response = APIProducto.crearProducto(currentUrl, GlobalHook.productRequest);
        originalSku = response.jsonPath().getString("sku");
    }


}



