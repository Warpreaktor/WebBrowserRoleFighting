package mechanic;

import lombok.Getter;
import mechanic.interfaces.Stat;

/**
 * Сила, является одним из основных статов персонажа.
 * Сила влияет на всё физическое с чем взаимодействует персонаж,
 * увеличивает физический урон не метательного оружия,
 * увеличивает максимальное значение хитпоинтов и проч.
 */
@Getter
public class Strength implements Stat {

    /**
     * Значение силы
     */
    private Integer value;

    public Strength(int value) {
        setValue(value);
    }

    public void setValue(int value) {
        this.value = value;
    }
}
