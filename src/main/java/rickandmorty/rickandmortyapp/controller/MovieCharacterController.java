package rickandmorty.rickandmortyapp.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rickandmorty.rickandmortyapp.dto.MovieResponseDto;
import rickandmorty.rickandmortyapp.model.MovieCharacter;
import rickandmorty.rickandmortyapp.service.MovieCharacterService;
import rickandmorty.rickandmortyapp.service.mapper.MovieCharacterMapper;

@RestController
@RequestMapping("/movie-characters")
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper mapper;

    public MovieCharacterController(MovieCharacterService movieCharacterService,
                                    MovieCharacterMapper mapper) {
        this.movieCharacterService = movieCharacterService;
        this.mapper = mapper;
    }

    @GetMapping("/random")
    @ApiOperation(value = "get random movie character from DB")
    public MovieResponseDto getRandom() {
        MovieCharacter character = movieCharacterService.getRandomCharacters();
        return mapper.toDto(character);
    }

    @GetMapping("/by-name")
    @ApiOperation(value = "get list of movie characters with part of name")
    public List<MovieResponseDto> findAllByName(@RequestParam("name") String namePart) {
        return movieCharacterService.findAllByNameContains(namePart).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
