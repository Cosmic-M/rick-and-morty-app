package rickandmorty.rickandmortyapp.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rickandmorty.rickandmortyapp.model.MovieCharacter;

@Repository
public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
    @Query("FROM MovieCharacter mc where mc.externalId = ?1")
    MovieCharacter findByRandomId(Long id);

    List<MovieCharacter> findAllByExternalIdIn(Set<Long> externalIds);

    List<MovieCharacter> findAllByNameContains(String namePart);
}
