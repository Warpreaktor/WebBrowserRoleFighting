package mechanic;

import dto.MinMax;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static constants.GlobalConstants.GLOBAL_DAMAGE_MULTIPLIER;

@AllArgsConstructor
public class Damage {

    /**
     * Колющий урон
     */
    private MinMax piercing;

    /**
     * Режущий урон
     */
    private MinMax cutting;

    /**
     * Дробящий урон
     */
    private MinMax crushing;

    /**
     * Огненный урон
     */
    @Getter
    private MinMax fire;

    public Damage() {
        piercing = new MinMax(0d);
        cutting = new MinMax(0d);
        crushing = new MinMax(0d);
        fire = new MinMax(0d);
    }

    /**
     * Устанавливает огненный урон с учетом глобального множителя
     *
     * @param value значение огненного урона
     */
    public void setFire(MinMax value) {
        fire = new MinMax(value)
                .multiply(GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Устанавливает огненный урон с учетом глобального множителя
     *
     * @param min минимальное значение
     * @param max максимальное значение
     */
    public void setFire(double min, double max) {
        fire = new MinMax(min, max)
                .multiply(GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Добавляет огненный урон к текущему с учетом глобального множителя
     *
     * @param value значение огненного урона
     */
    public void addFire(MinMax value) {
        fire.plus(value.multiply(GLOBAL_DAMAGE_MULTIPLIER));
    }

    /**
     * Добавляет значение огненного урона к текущему с учетом глобального множителя
     *
     * @param value значение огненного урона
     */
    public void addFire(double value) {
        addFire(new MinMax(value, value));
    }

    /**
     * Устанавливает колющий урон с учетом глобального множителя
     *
     * @param value значение колющего урона
     */
    public void setPiercing(MinMax value) {
        piercing = new MinMax(value)
                .multiply(GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Устанавливает колющий урон с учетом глобального множителя
     *
     * @param min минимальное значение
     * @param max максимальное значение
     */
    public void setPiercing(double min, double max) {
        piercing = new MinMax(min, max)
                .multiply(GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Добавляет колющий урон к текущему с учетом глобального множителя
     *
     * @param value значение колющего урона
     */
    public void addPiercing(MinMax value) {
        piercing.plus(value.multiply(GLOBAL_DAMAGE_MULTIPLIER));
    }

    /**
     * Добавляет значение колющего урона к текущему с учетом глобального множителя
     *
     * @param value значение колющего урона
     */
    public void addPiercing(double value) {
        addPiercing(new MinMax(value, value));
    }

    /**
     * Устанавливает дробящий урон с учетом глобального множителя
     *
     * @param value значение дробящего урона
     */
    public void setCrushing(MinMax value) {
        crushing = new MinMax(value)
                .multiply(GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Устанавливает дробящий урон с учетом глобального множителя
     *
     * @param min минимальное значение
     * @param max максимальное значение
     */
    public void setCrushing(double min, double max) {
        crushing = new MinMax(min, max)
                .multiply(GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Добавляет дробящий урон к текущему с учетом глобального множителя
     *
     * @param value значение дробящего урона
     */
    public void addCrushing(MinMax value) {
        crushing.plus(value.multiply(GLOBAL_DAMAGE_MULTIPLIER));
    }

    /**
     * Добавляет значение дробящего урона к текущему с учетом глобального множителя
     *
     * @param value значение дробящего урона
     */
    public void addCrushing(double value) {
        addCrushing(new MinMax(value, value));
    }


    /**
     * Устанавливает режущий урон с учетом глобального множителя
     *
     * @param value значение режущего урона
     */
    public void setCutting(MinMax value) {
        cutting = new MinMax(value)
                .multiply(GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Устанавливает режущий урон с учетом глобального множителя
     *
     * @param min минимальное значение
     * @param max максимальное значение
     */
    public void setCutting(double min, double max) {
        cutting = new MinMax(min, max)
                .multiply(GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Добавляет режущий урон к текущему с учетом глобального множителя
     *
     * @param value значение режущего урона
     */
    public void addCutting(MinMax value) {
        cutting.plus(value.multiply(GLOBAL_DAMAGE_MULTIPLIER));
    }

    /**
     * Добавляет значение режущего урона к текущему с учетом глобального множителя
     *
     * @param value значение режущего урона
     */
    public void addCutting(double value) {
        addCutting(new MinMax(value, value));
    }

    /**
     * Возвращает копию объекта с колющим уроном.
     *
     * @return копия MinMax объекта piercing
     */
    public MinMax getPiercing() {
        return new MinMax(piercing);
    }

    /**
     * Возвращает копию объекта с режущим уроном.
     *
     * @return копия MinMax объекта cutting
     */
    public MinMax getCutting() {
        return new MinMax(cutting);
    }

    /**
     * Возвращает копию объекта с дробящим уроном.
     *
     * @return копия MinMax объекта crushing
     */
    public MinMax getCrushing() {
        return new MinMax(crushing);
    }
}
