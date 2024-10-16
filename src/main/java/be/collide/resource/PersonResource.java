package be.collide.resource;

import be.collide.domain.Person;
import be.collide.repository.PersonRepository;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Path("/person")
public class PersonResource {
    @Inject
    PersonRepository personRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public PersonDTO getPerson(@PathParam("id") UUID id) {
        var person = personRepository.get(id);
        return PersonDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .firstName(person.getFirstName())
                .birthDate(person.getBirthDate())
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid UpsertPersonDto upsertPersonDto, @Context UriInfo uriInfo) {
        Person person = personRepository.create(Person.builder()
                .name(upsertPersonDto.name)
                .firstName(upsertPersonDto.firstName)
                .birthDate(upsertPersonDto.birthDate)
                .emailAddress(upsertPersonDto.emailAddress)
                .linkedInProfile(upsertPersonDto.linkedInProfile)
                .build());

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(person.getId().toString());
        return Response.created(uriBuilder.build()).build();
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PersonDTO {
        private UUID id;
        private String firstName;
        private String name;
        private LocalDate birthDate;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpsertPersonDto {
        private String firstName;
        private String name;
        private LocalDate birthDate;
        @Pattern(regexp = "^https?:\\/\\/(www\\.)?linkedin\\.com\\/in\\/[a-zA-Z0-9-]+\\/?$")
        private String linkedInProfile;
        @Email(regexp = ".+@.+\\..+")
        private String emailAddress;

    }

}
