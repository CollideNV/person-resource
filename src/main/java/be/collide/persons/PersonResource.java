package be.collide.persons;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Path("/person")
public class PersonResource {
    @Inject
    Persons persons;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public PersonDTO getPerson(@PathParam("id") UUID id) {
        var person = persons.get(id);
        return PersonDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .firstName(person.getFirstName())
                .birthDate(person.getBirthDate())
                .employments(person.getEmployments().stream().map(employment -> PersonDTO.EmploymentDto.builder().company(employment.getCompany()).endDate(employment.getEndDate()).startDate(employment.getStartDate()).id(employment.getId()).build()).toList())
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/employment")
    public void create(@PathParam("id") UUID id, @Valid PersonResource.PersonDTO.EmploymentDto employmentDto) {

        Person person = persons.get(id);
        person.addEmployment(employmentDto.id, employmentDto.company, employmentDto.startDate, employmentDto.endDate);
        persons.update(person);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid UpsertPersonDto upsertPersonDto, @Context UriInfo uriInfo) {
        Person person = persons.create(Person.builder()
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
        private List<EmploymentDto> employments;

        @Builder
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class EmploymentDto {
            private UUID id;
            private LocalDate startDate;
            private LocalDate endDate;
            private String company;
        }
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


    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddEmploymentDto {
        @NotNull
        private UUID id;
        @NotNull
        private LocalDate startDate;
        private LocalDate endDate;
        @NotNull
        private String company;
    }

}
