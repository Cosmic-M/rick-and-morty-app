package rickandmorty.rickandmortyapp.service.mapper;

import org.springframework.stereotype.Component;
import rickandmorty.rickandmortyapp.dto.ApiCharacterDto;
import rickandmorty.rickandmortyapp.dto.MovieResponseDto;
import rickandmorty.rickandmortyapp.model.Gender;
import rickandmorty.rickandmortyapp.model.MovieCharacter;
import rickandmorty.rickandmortyapp.model.Status;

@Component
public class MovieCharacterMapper {
    public MovieCharacter toModel(ApiCharacterDto characterDto) {
        MovieCharacter character = new MovieCharacter();
        character.setExternalId(characterDto.getId());
        character.setName(characterDto.getName());
        character.setImageUrl(characterDto.getImage());
        character.setStatus(Status.valueOf(characterDto.getStatus().toUpperCase()));
        character.setGender(Gender.valueOf(characterDto.getGender().toUpperCase()));
        return character;
    }

    public MovieResponseDto toDto(MovieCharacter character) {
        MovieResponseDto responseDto = new MovieResponseDto();
        responseDto.setId(character.getId());
        responseDto.setExternalId(character.getExternalId());
        responseDto.setName(character.getName());
        responseDto.setImageUrl(character.getImageUrl());
        responseDto.setGender(character.getGender());
        responseDto.setStatus(character.getStatus());
        return responseDto;
    }

    public MovieCharacter updateModel(MovieCharacter existing, ApiCharacterDto fromSite) {
        existing.setName(fromSite.getName());
        existing.setImageUrl(fromSite.getImage());
        existing.setGender(Gender.valueOf(fromSite.getGender().toUpperCase()));
        existing.setStatus(Status.valueOf(fromSite.getStatus().toUpperCase()));
        return existing;
    }
}
