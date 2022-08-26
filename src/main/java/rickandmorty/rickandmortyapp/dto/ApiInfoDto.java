package rickandmorty.rickandmortyapp.dto;

import lombok.Data;

@Data
public class ApiInfoDto {
    private int count;
    private int pages;
    private String next;
    private String prev;
}
