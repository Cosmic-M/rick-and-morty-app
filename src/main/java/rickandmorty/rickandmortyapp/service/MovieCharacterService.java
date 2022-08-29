package rickandmorty.rickandmortyapp.service;

import java.util.List;
import rickandmorty.rickandmortyapp.model.MovieCharacter;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter getRandomCharacters();

    List<MovieCharacter> findAllByNameContains(String namePart);
}
