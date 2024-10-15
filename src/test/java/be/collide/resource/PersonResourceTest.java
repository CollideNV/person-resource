package be.collide.resource;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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

        var upsertPersonDto = PersonResource.UpsertPersonDto.builder()
                .name("Person Last Name")
                .firstName("Person First name")
                .birthDate(LocalDate.of(2000, 4, 14))
                .linkedInProfile("https://www.linkedin.com/in/person-profile/")
                .emailAddress("test@test.com")
                .build();

        String url = given()
                .contentType("application/json")
                .body(upsertPersonDto)
                .when().post("/person")
                .then().log().all()
                .statusCode(201)
                .extract().headers().get("Location").getValue();

        PersonResource.PersonDTO personDTO = given()
                .when().get(url)
                .then()
                .statusCode(200).extract().as(PersonResource.PersonDTO.class);

        assertThat(personDTO.getId()).isNotNull();
        assertThat(personDTO.getName()).isEqualTo("Person Last Name");
        assertThat(personDTO.getFirstName()).isEqualTo("Person First name");
        assertThat(personDTO.getBirthDate()).isEqualTo(LocalDate.of(2000, 4, 14));
    }


    @Test
    void testCreatePersonWithIncorrectLinkedInUrl() {

        var upsertPersonDto = PersonResource.UpsertPersonDto.builder()
                .name("Person Last Name")
                .firstName("Person First name")
                .birthDate(LocalDate.of(2000, 4, 14))
                .linkedInProfile("https://www.linkedin.com/wrong/person-profile/")
                .emailAddress("test@test.com")
                .build();

        assertThat(given()
                .contentType("application/json")
                .body(upsertPersonDto)
                .post("/person")
                .statusCode()).isEqualTo(400);
    }

    @Test
    void testCreatePersonWithIncorrectEmailAddress() {

        var upsertPersonDto = PersonResource.UpsertPersonDto.builder()
                .name("Person Last Name")
                .firstName("Person First name")
                .birthDate(LocalDate.of(2000, 4, 14))
                .linkedInProfile("https://www.linkedin.com/in/person-profile/")
                .emailAddress("test@test")
                .build();

        assertThat(given()
                .contentType("application/json")
                .body(upsertPersonDto)
                .post("/person")
                .statusCode()).isEqualTo(400);
    }
}