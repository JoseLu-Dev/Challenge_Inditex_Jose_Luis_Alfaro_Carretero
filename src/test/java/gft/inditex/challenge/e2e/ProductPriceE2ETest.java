package gft.inditex.challenge.e2e;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductPriceE2ETest {

    private static final Long BRAND_ID_ZARA = 1L;
    private static final Long PRODUCT_ID = 35455L;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = "/api/v1";
    }

    @Test
    @DisplayName("Test 1: Request at 10:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void shouldReturnTariff1_whenRequestAt10OnDay14() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("onDate", "2020-06-14T10:00:00Z")
                .when()
                .get("/brands/{brandId}/products/{productId}/prices", BRAND_ID_ZARA, PRODUCT_ID)
                .then()
                .statusCode(200)
                .body("productId", equalTo(PRODUCT_ID.intValue()))
                .body("brandId", equalTo(BRAND_ID_ZARA.intValue()))
                .body("appliedTariffId", equalTo(1))
                .body("finalPrice", equalTo(35.50f));
    }

    @Test
    @DisplayName("Test 2: Request at 16:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void shouldReturnTariff2_whenRequestAt16OnDay14() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("onDate", "2020-06-14T16:00:00Z")
                .when()
                .get("/brands/{brandId}/products/{productId}/prices", BRAND_ID_ZARA, PRODUCT_ID)
                .then()
                .statusCode(200)
                .body("productId", equalTo(PRODUCT_ID.intValue()))
                .body("brandId", equalTo(BRAND_ID_ZARA.intValue()))
                .body("appliedTariffId", equalTo(2))
                .body("finalPrice", equalTo(25.45f));
    }

    @Test
    @DisplayName("Test 3: Request at 21:00 on day 14 for product 35455 and brand 1 (ZARA)")
    void shouldReturnTariff1_whenRequestAt21OnDay14() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("onDate", "2020-06-14T21:00:00Z")
                .when()
                .get("/brands/{brandId}/products/{productId}/prices", BRAND_ID_ZARA, PRODUCT_ID)
                .then()
                .statusCode(200)
                .body("productId", equalTo(PRODUCT_ID.intValue()))
                .body("brandId", equalTo(BRAND_ID_ZARA.intValue()))
                .body("appliedTariffId", equalTo(1))
                .body("finalPrice", equalTo(35.50f));
    }

    @Test
    @DisplayName("Test 4: Request at 10:00 on day 15 for product 35455 and brand 1 (ZARA)")
    void shouldReturnTariff3_whenRequestAt10OnDay15() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("onDate", "2020-06-15T10:00:00Z")
                .when()
                .get("/brands/{brandId}/products/{productId}/prices", BRAND_ID_ZARA, PRODUCT_ID)
                .then()
                .statusCode(200)
                .body("productId", equalTo(PRODUCT_ID.intValue()))
                .body("brandId", equalTo(BRAND_ID_ZARA.intValue()))
                .body("appliedTariffId", equalTo(1))
                .body("finalPrice", equalTo(35.50f));
    }

    @Test
    @DisplayName("Test 5: Request at 21:00 on day 16 for product 35455 and brand 1 (ZARA)")
    void shouldReturnTariff4_whenRequestAt21OnDay16() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("onDate", "2020-06-16T21:00:00Z")
                .when()
                .get("/brands/{brandId}/products/{productId}/prices", BRAND_ID_ZARA, PRODUCT_ID)
                .then()
                .statusCode(200)
                .body("productId", equalTo(PRODUCT_ID.intValue()))
                .body("brandId", equalTo(BRAND_ID_ZARA.intValue()))
                .body("appliedTariffId", equalTo(4))
                .body("finalPrice", equalTo(38.95f));
    }
}
