package rickandmorty.rickandmortyapp.dto;

import lombok.Data;
import rickandmorty.rickandmortyapp.model.Gender;
import rickandmorty.rickandmortyapp.model.Status;

@Data
public class MovieResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Gender gender;
    private Status status;
}
