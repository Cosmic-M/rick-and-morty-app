package rickandmorty.rickandmortyapp.dto;

import lombok.Data;

@Data
public class ApiCharacterDto {
    private Long id;
    private String name;
    private String image;
    private String status;
    private String gender;
}
