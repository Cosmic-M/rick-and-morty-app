package rickandmorty.rickandmortyapp.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import rickandmorty.rickandmortyapp.model.Gender;
import rickandmorty.rickandmortyapp.model.MovieCharacter;
import rickandmorty.rickandmortyapp.model.Status;
import rickandmorty.rickandmortyapp.service.MovieCharacterService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MovieCharacterControllerTest {
    @MockBean
    private MovieCharacterService movieCharacterService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void getRandomCharacter_ok() {
        MovieCharacter jerry = new MovieCharacter();
        jerry.setId(5L);
        jerry.setExternalId(5L);
        jerry.setName("Jerry Smith");
        jerry.setStatus(Status.ALIVE);
        jerry.setGender(Gender.MALE);

        Mockito.when(movieCharacterService.getRandomCharacters()).thenReturn(jerry);

        RestAssuredMockMvc.when()
                .get("/movie-characters/random")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(5))
                .body("externalId", Matchers.equalTo(5))
                .body("name", Matchers.equalTo("Jerry Smith"))
                .body("gender", Matchers.equalTo("MALE"))
                .body("status", Matchers.equalTo("ALIVE"));
    }

    @Test
    void shouldFindAllCharactersByName() {
        final String name = "Sum";
        MovieCharacter summerSmith = new MovieCharacter();
        summerSmith.setName("Summer Smith");
        summerSmith.setGender(Gender.FEMALE);
        summerSmith.setStatus(Status.ALIVE);
        summerSmith.setExternalId(3L);
        summerSmith.setExternalId(3L);

        MovieCharacter evilSummer = new MovieCharacter();
        evilSummer.setName("Evil Summer from university C153");
        evilSummer.setGender(Gender.FEMALE);
        evilSummer.setStatus(Status.ALIVE);
        evilSummer.setExternalId(5999L);
        evilSummer.setExternalId(5999L);

        List<MovieCharacter> mockSummers = List.of(summerSmith, evilSummer);

        Mockito.when(movieCharacterService.findAllByNameContains(name))
                .thenReturn(mockSummers);

        RestAssuredMockMvc.given()
                .queryParam("name", name)
                .when()
                .get("/movie-characters/by-name")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2))
                .body("[0].name", Matchers.equalTo("Summer Smith"))
                .body("[0].gender", Matchers.equalTo("FEMALE"))
                .body("[0].status", Matchers.equalTo("ALIVE"))
                .body("[1].name", Matchers.equalTo("Evil Summer from university C153"))
                .body("[1].gender", Matchers.equalTo("FEMALE"))
                .body("[1].status", Matchers.equalTo("ALIVE"));
    }
}
