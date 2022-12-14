package rickandmorty.rickandmortyapp.controller;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rickandmorty.rickandmortyapp.dto.MovieResponseDto;
import rickandmorty.rickandmortyapp.model.MovieCharacter;
import rickandmorty.rickandmortyapp.service.MovieCharacterService;
import rickandmorty.rickandmortyapp.service.mapper.MovieCharacterMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie-characters")
@CrossOrigin(origins = "http://localhost:4200")
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper mapper;

    @GetMapping("/random")
    @ApiOperation(value = "get random movie character from DB")
    public MovieResponseDto getRandom() {
        MovieCharacter character = movieCharacterService.getRandomCharacters();
        return mapper.toDto(character);
    }

    @GetMapping("/all")
    @ApiOperation(value = "get all movie character from DB")
    public List<MovieResponseDto> getAll() {
        List<MovieCharacter> characters = movieCharacterService.findAll();
        return characters.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/by-name")
    @ApiOperation(value = "get list of movie characters with part of name")
    public List<MovieResponseDto> findAllByName(@RequestParam("name") String namePart) {
        return movieCharacterService.findAllByNameContains(namePart).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
