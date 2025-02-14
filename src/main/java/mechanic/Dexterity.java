package mechanic;

import lombok.Getter;
import lombok.Setter;
import mechanic.interfaces.Stat;

/**
 * Ловкость, является одним из основных статов персонажа.
 * Ловкость влияет на всё где требуется сноровка,
 * увеличивает шанс уклонения от атак,
 * увеличивает скорость атаки.
 */
@Getter
public class Dexterity implements Stat {

    /**
     * Значение ловкости
     */
    private Integer value;

    public Dexterity(int value) {
        setValue(value);
    }

    public void setValue(int value) {
        this.value = value;
    }
}
