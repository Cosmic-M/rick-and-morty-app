package rickandmorty.rickandmortyapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rickandmorty.rickandmortyapp.service.MovieCharacterService;

@RestController
@RequestMapping("/demo")
public class DemoController {
    private MovieCharacterService cartoonCharacterService;

    public DemoController(MovieCharacterService cartoonCharacterService) {
        this.cartoonCharacterService = cartoonCharacterService;
    }

    @GetMapping
    public String runDemo() {
        cartoonCharacterService.syncExternalCharacters();
        return "Done!";
    }
}
