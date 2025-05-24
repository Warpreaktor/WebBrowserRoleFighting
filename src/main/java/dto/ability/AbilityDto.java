package dto.ability;

import enums.AbilityTarget;
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
    private AbilityTarget type;

    @Getter
    private String description;

    @Getter
    private int cost;

    @Getter
    private Boolean isActive;
}
