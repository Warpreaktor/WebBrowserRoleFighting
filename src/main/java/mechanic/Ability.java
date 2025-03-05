package mechanic;

import dto.ability.AbilityDto;
import dto.damage.DamageDto;
import enums.AbilityType;
import mechanic.interfaces.Defensible;

public abstract class Ability {

    private final String picturePath;

    private final String name;

    private final AbilityType type;

    private final String description;

    protected Ability(String picturePath, String name, AbilityType type, String description) {
        this.picturePath = picturePath;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public abstract DamageDto apply(Defensible target);

    public abstract AbilityDto toDto();
}
