package rickandmorty.rickandmortyapp.service.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import rickandmorty.rickandmortyapp.dto.ApiCharacterDto;
import rickandmorty.rickandmortyapp.dto.MovieResponseDto;
import rickandmorty.rickandmortyapp.model.Gender;
import rickandmorty.rickandmortyapp.model.MovieCharacter;
import rickandmorty.rickandmortyapp.model.Status;

@ExtendWith(MockitoExtension.class)
class MovieCharacterMapperTest {
    @InjectMocks
    private MovieCharacterMapper mapper;
    private MovieCharacter rick;
    private ApiCharacterDto rickApiDto;

    @BeforeEach
    void setUp() {
        rick = new MovieCharacter();
        rick.setId(1L);
        rick.setExternalId(1L);
        rick.setName("Rick Sanchez");
        rick.setStatus(Status.ALIVE);
        rick.setGender(Gender.MALE);

        rickApiDto = new ApiCharacterDto();
        rickApiDto.setId(1L);
        rickApiDto.setName("Rick Sanchez");
        rickApiDto.setStatus("Alive");
        rickApiDto.setGender("Male");
    }

    @Test
    void mapDtoToModel_ok() {
        MovieCharacter result = mapper.toModel(rickApiDto);
        Assertions.assertEquals(1L, result.getExternalId());
        Assertions.assertEquals("Rick Sanchez", result.getName());
        Assertions.assertEquals(Gender.MALE, result.getGender());
        Assertions.assertEquals(Status.ALIVE, result.getStatus());
    }

    @Test
    void toDto() {
        MovieResponseDto result = mapper.toDto(rick);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals(1L, result.getExternalId());
        Assertions.assertEquals("Rick Sanchez", result.getName());
        Assertions.assertEquals(Gender.MALE, result.getGender());
        Assertions.assertEquals(Status.ALIVE, result.getStatus());
    }

    @Test
    void updateMovieCharacter_ok() {
        rickApiDto.setName("Dumb Rick From C179");
        rickApiDto.setStatus("Dead");
        MovieCharacter result = mapper.updateModel(rick, rickApiDto);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals(1L, result.getExternalId());
        Assertions.assertEquals("Dumb Rick From C179", result.getName());
        Assertions.assertEquals(Gender.MALE, result.getGender());
        Assertions.assertEquals(Status.DEAD, result.getStatus());
    }
}
