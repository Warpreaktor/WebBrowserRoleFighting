package mechanic;

import dto.ability.AbilityDto;
import dto.damage.DamageDto;
import enums.AbilityType;
import lombok.Getter;
import mechanic.interfaces.Defensible;
import mechanic.interfaces.Switchable;

public abstract class Ability implements Switchable {

    /**
     * Путь к иконке способности
     */
    private final String picturePath;

    /**
     * Наименование способности
     */
    @Getter
    private final String name;

    /**
     * Тип применения способности.
     */
    private final AbilityType type;

    /**
     * Описание способности
     */
    private final String description;

    /**
     * Стоимость способности (в очках выносливости)
     */
    @Getter
    private int cost;

    /**
     * Количество раундов на которое будет отключена способность после применения.
     */
    @Getter
    private int coolDown;

    protected Ability(String picturePath,
                      String name,
                      AbilityType type,
                      String description,
                      int cost,
                      int coolDown
    ) {

        this.picturePath = picturePath;
        this.name = name;
        this.type = type;
        this.description = description;
        this.cost = cost;
    }

    public abstract DamageDto apply(Defensible target);

    public abstract AbilityDto toDto();

}
