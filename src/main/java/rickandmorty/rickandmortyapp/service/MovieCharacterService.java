package rickandmorty.rickandmortyapp.service;

import rickandmorty.rickandmortyapp.model.MovieCharacter;

import java.util.List;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter getRandomCharacters();

    List<MovieCharacter> findAllByNameContains(String namePart);
}
