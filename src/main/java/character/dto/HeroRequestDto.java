package character.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeroRequestDto {

    private String name;

    private String heroClass;
}
