package ability;

import core.GameMaster;
import dto.ability.AbilityDto;
import enums.AbilityType;
import hero.Hero;
import lombok.Getter;
import mechanic.interfaces.Switchable;

public abstract class Ability implements Switchable {

    /**
     * Путь к иконке способности
     */
    @Getter
    private final String picturePath;

    /**
     * Наименование способности
     */
    @Getter
    private final String name;

    /**
     * Тип применения способности.
     */
    @Getter
    private final AbilityType type;

    /**
     * Описание способности
     */
    @Getter
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

    /**
     * Активна ли способность. Если способность не активно, то использовать её нельзя.
     */
    private boolean isActive = true;

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
        this.coolDown = coolDown;
    }

    /**
     * Применить способность
     */
    public abstract void apply(Hero target);

    /**
     * Возвращает текущее состояние способности. Активна ли она.
     */
    @Override
    public boolean isActive() {
        return isActive;
    }

    /**
     * Включает способность
     */
    @Override
    public void switchOn() {
        isActive = true;
    }

    /**
     * Выключает способность
     */
    @Override
    public void switchOff() {
        isActive = false;
    }

    public AbilityDto toDto() {
        return AbilityDto
                .builder()
                .picturePath(picturePath)
                .name(name)
                .type(type)
                .description(description)
                .cost(cost)
                .isActive(isActive)
                .build();
    }

    public abstract int getGameWeight();

    public void coolDown() {
        GameMaster.getInstance().switchOff(this, coolDown);
    }

}
