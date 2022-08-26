package rickandmorty.rickandmortyapp.dto;

import lombok.Data;

@Data
public class ApiResponseDto {
    private ApiInfoDto info;
    private ApiCharacterDto[] results;
}
