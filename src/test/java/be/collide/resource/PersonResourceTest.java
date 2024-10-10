package be.collide.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
class PersonResourceTest {

    @Test
    void testUnknownPersonId() {
        assertThat(given().get("/person/" + UUID.randomUUID()).getStatusCode())
                .isEqualTo(404);
    }

    @Test
    void testCreateAndFindById() {


        var createPerson = PersonResource.CreatePersonDto.builder().build();

        String url = given()
                .contentType("application/json")
                .body(createPerson)
                .when().post("/person")
                .then().log().all()
                .statusCode(201)
                .extract().headers().get("Location").getValue();


        PersonResource.PersonDTO personDTO = given()
                .when().get(url)
                .then()
                .statusCode(200).extract().as(PersonResource.PersonDTO.class);

        assertThat(personDTO.getId()).isNotNull();
    }

}