package mechanic;

import lombok.Getter;
import mechanic.interfaces.Stat;

/**
 * Интеллект, является одним из основных статов персонажа.
 * Интеллект влияет на всё магическое, увеличивает восполнение щита, урон магией и проч.
 */
@Getter
public class Intelligence implements Stat {

    /**
     * Значение интеллекта.
     */
    Integer value;

    public Intelligence(int value) {
        setValue(value);
    }

    public void setValue(int value) {
        this.value = value;
    }
}
