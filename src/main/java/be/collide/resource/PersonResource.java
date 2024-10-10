package be.collide.resource;

import be.collide.domain.Person;
import be.collide.service.PersonRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.Builder;
import lombok.Data;

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
    public Response create(CreatePersonDto createPersonDto, @Context UriInfo uriInfo) {
        Person person = personRepository.create(Person.builder()
                .name(createPersonDto.name)
                .firstName(createPersonDto.firstName)
                .birthDate(createPersonDto.birthDate)
                .build());

        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(person.getId().toString());
        return Response.created(uriBuilder.build()).build();
    }

    @Builder
    @Data
    public static class PersonDTO {
        private UUID id;
        private String firstName;
        private String name;
        private LocalDate birthDate;
    }

    @Builder
    @Data
    public static class CreatePersonDto {
        private String firstName;
        private String name;
        private LocalDate birthDate;
    }
}
