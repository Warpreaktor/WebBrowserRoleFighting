package dto.ability;

import enums.AbilityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
public class AbilityDto {

    @Getter
    private String picturePath;

    @Getter
    private String name;

    @Getter
    private AbilityType type;

    @Getter
    private String description;

    @Getter
    private int cost;

    @Getter
    private String isActive;
}
