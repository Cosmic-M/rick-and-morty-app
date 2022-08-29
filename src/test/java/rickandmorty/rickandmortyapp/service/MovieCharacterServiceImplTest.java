package rickandmorty.rickandmortyapp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import rickandmorty.rickandmortyapp.dto.ApiCharacterDto;
import rickandmorty.rickandmortyapp.dto.ApiInfoDto;
import rickandmorty.rickandmortyapp.dto.ApiResponseDto;
import rickandmorty.rickandmortyapp.model.Gender;
import rickandmorty.rickandmortyapp.model.MovieCharacter;
import rickandmorty.rickandmortyapp.model.Status;
import rickandmorty.rickandmortyapp.repository.MovieCharacterRepository;
import rickandmorty.rickandmortyapp.service.mapper.MovieCharacterMapper;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MovieCharacterServiceImplTest {
    @InjectMocks
    private MovieCharacterServiceImpl movieCharacterService;
    @Mock
    private MovieCharacterRepository movieCharacterRepository;
    @Mock
    private MovieCharacterMapper movieCharacterMapper;
    private MovieCharacter rick;
    private ApiCharacterDto apiCharacterDto;
    private ApiResponseDto apiResponseDto;

    @BeforeEach
    void setUp() {
        rick = new MovieCharacter();
        rick.setId(1L);
        rick.setExternalId(1L);
        rick.setName("Rick Sanchez");
        rick.setStatus(Status.ALIVE);
        rick.setGender(Gender.MALE);

        ApiInfoDto apiInfoDto = new ApiInfoDto();
        apiInfoDto.setCount(1);
        apiInfoDto.setPages(1);
        apiInfoDto.setNext(null);
        apiInfoDto.setPrev(null);

        apiCharacterDto = new ApiCharacterDto();
        apiCharacterDto.setId(1L);
        apiCharacterDto.setName("Rick Sanchez");
        apiCharacterDto.setStatus("Alive");
        apiCharacterDto.setGender("Male");

        apiResponseDto = new ApiResponseDto();
        apiResponseDto.setInfo(apiInfoDto);
        apiResponseDto.setResults(new ApiCharacterDto[]{apiCharacterDto});
    }

    @Test
    void addNewCharacterToListToSave_oK() {
        Mockito.when(movieCharacterRepository.findAllByExternalIdIn(any())).thenReturn(Collections.emptyList());
        Mockito.when(movieCharacterMapper.toModel(apiCharacterDto)).thenReturn(rick);
        Assertions.assertEquals(1, movieCharacterService.getListToSave(apiResponseDto).size());
        Assertions.assertEquals("Rick Sanchez", movieCharacterService.getListToSave(apiResponseDto).get(0).getName());
    }

    @Test
    void updateDataInExistingCharacterList_oK() {
        apiCharacterDto.setName("Goofy Rick from University 1f564");
        Mockito.when(movieCharacterRepository.findAllByExternalIdIn(Set.of(1L))).thenReturn(List.of(rick));
        Assertions.assertEquals(1, movieCharacterService.getListToSave(apiResponseDto).size());
        Assertions.assertEquals("Goofy Rick from University 1f564", movieCharacterService.getListToSave(apiResponseDto).get(0).getName());
    }
}
