package rickandmorty.rickandmortyapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import rickandmorty.rickandmortyapp.dto.ApiCharacterDto;
import rickandmorty.rickandmortyapp.dto.ApiResponseDto;
import rickandmorty.rickandmortyapp.model.MovieCharacter;
import rickandmorty.rickandmortyapp.repository.MovieCharacterRepository;
import rickandmorty.rickandmortyapp.service.mapper.MovieCharacterMapper;

@Log4j2
@Service
@RequiredArgsConstructor
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    private final MovieCharacterMapper movieCharacterMapper;
    @Value(value = "${link}")
    private String linkToApiRickAndMorty;

    @PostConstruct
    @Scheduled(cron = "0 30 8 * * ?", zone = "Europe/Kiev")
    @Override
    public void syncExternalCharacters() {
        log.info("syncExternalCharacters was called at " + LocalDateTime.now());

        ApiResponseDto responseDto = httpClient
                .get(linkToApiRickAndMorty, ApiResponseDto.class);
        List<MovieCharacter> toSave = new ArrayList<>(getListToSave(responseDto));
        while (responseDto.getInfo().getNext() != null) {
            responseDto = httpClient.get(responseDto.getInfo().getNext(), ApiResponseDto.class);
            toSave.addAll(getListToSave(responseDto));
        }
        movieCharacterRepository.saveAll(toSave);
    }

    @Override
    public MovieCharacter getRandomCharacters() {
        long count = movieCharacterRepository.count();
        long randomId = (long) (Math.random() * count);
        return movieCharacterRepository.findByRandomId(randomId);
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart) {
        return movieCharacterRepository.findAllByNameContains(namePart);
    }

    List<MovieCharacter> getListToSave(ApiResponseDto responseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(responseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));
        Set<Long> externalIds = externalDtos.keySet();
        List<MovieCharacter> existingCharacters = movieCharacterRepository
                .findAllByExternalIdIn(externalIds);
        Map<Long, MovieCharacter> existingCharactersWithIds = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));
        Set<Long> existingIds = existingCharactersWithIds.keySet();

        existingIds.retainAll(externalIds);
        List<MovieCharacter> toUpdate = new java.util.ArrayList<>(existingCharacters.stream()
                .map(i -> movieCharacterMapper.updateModel(i, externalDtos.get(i.getExternalId())))
                .toList());

        existingIds = existingCharactersWithIds.keySet();
        externalIds.removeAll(existingIds);
        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> movieCharacterMapper
                        .toModel(externalDtos.get(i)))
                .collect(Collectors.toList());
        charactersToSave.addAll(toUpdate);
        return charactersToSave;
    }
}
