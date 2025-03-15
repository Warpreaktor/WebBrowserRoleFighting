package mechanic;

import dto.MinMax;
import lombok.AllArgsConstructor;
import lombok.Builder;

import static constants.GlobalConstants.GLOBAL_DAMAGE_MULTIPLIER;

@AllArgsConstructor
@Builder
public class Damage {

    @Builder.Default
    private double localDamageMultiplier = 1;

    /**
     * Колющий урон
     */
    @Builder.Default
    private MinMax piercing = new MinMax(0d);

    /**
     * Режущий урон
     */
    @Builder.Default
    private MinMax cutting = new MinMax(0d);

    /**
     * Дробящий урон
     */
    @Builder.Default
    private MinMax crushing = new MinMax(0d);

    /**
     * Огненный урон
     */
    @Builder.Default
    private MinMax fire = new MinMax(0d);

    /**
     * Электрический урон
     */
    @Builder.Default
    private MinMax electric = new MinMax(0d);

    public Damage() {
        piercing = new MinMax(0d);
        cutting = new MinMax(0d);
        crushing = new MinMax(0d);
        fire = new MinMax(0d);
        electric = new MinMax(0d);
    }

    /**
     * Устанавливает огненный урон с учетом глобального множителя
     *
     * @param value значение огненного урона
     */
    public void setElectric(MinMax value) {
        electric = new MinMax(value)
                .multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Устанавливает значение урона заменяя его на новое с учетом глобального множителя
     */
    public void setElectric(double min, double max) {
        electric = new MinMax(min, max)
                .multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Добавляет урон к текущему с учетом глобального множителя
     */
    public void addElectric(MinMax value) {
        electric.plus(value.multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER));
    }

    /**
     * Добавляет значение урона к текущему с учетом глобального множителя
     */
    public void addElectric(double value) {
        addElectric(new MinMax(value, value)
                .multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER));
    }

    /**
     * Устанавливает огненный урон с учетом глобального множителя
     *
     * @param value значение огненного урона
     */
    public void setFire(MinMax value) {
        fire = new MinMax(value)
                .multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Устанавливает огненный урон с учетом глобального множителя
     *
     * @param min минимальное значение
     * @param max максимальное значение
     */
    public void setFire(double min, double max) {
        fire = new MinMax(min, max)
                .multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Добавляет огненный урон к текущему с учетом глобального множителя
     *
     * @param value значение огненного урона
     */
    public void addFire(MinMax value) {
        fire.plus(value.multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER));
    }

    /**
     * Добавляет значение огненного урона к текущему с учетом глобального множителя
     *
     * @param value значение огненного урона
     */
    public void addFire(double value) {
        addFire(new MinMax(value, value)
                .multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER));
    }

    /**
     * Устанавливает колющий урон с учетом глобального множителя
     *
     * @param value значение колющего урона
     */
    public void setPiercing(MinMax value) {
        piercing = new MinMax(value)
                .multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Устанавливает колющий урон с учетом глобального множителя
     *
     * @param min минимальное значение
     * @param max максимальное значение
     */
    public void setPiercing(double min, double max) {
        piercing = new MinMax(min, max)
                .multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Добавляет колющий урон к текущему с учетом глобального множителя
     *
     * @param value значение колющего урона
     */
    public void addPiercing(MinMax value) {
        piercing.plus(value.multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER));
    }

    /**
     * Добавляет значение колющего урона к текущему с учетом глобального множителя
     *
     * @param value значение колющего урона
     */
    public void addPiercing(double value) {
        addPiercing(new MinMax(value, value).multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER));
    }

    /**
     * Устанавливает дробящий урон с учетом глобального множителя
     *
     * @param value значение дробящего урона
     */
    public void setCrushing(MinMax value) {
        crushing = new MinMax(value)
                .multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Устанавливает дробящий урон с учетом глобального множителя
     *
     * @param min минимальное значение
     * @param max максимальное значение
     */
    public void setCrushing(double min, double max) {
        crushing = new MinMax(min, max)
                .multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Добавляет дробящий урон к текущему с учетом глобального множителя
     *
     * @param value значение дробящего урона
     */
    public void addCrushing(MinMax value) {
        crushing.plus(value.multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER));
    }

    /**
     * Добавляет значение дробящего урона к текущему с учетом глобального множителя
     *
     * @param value значение дробящего урона
     */
    public void addCrushing(double value) {
        addCrushing(new MinMax(value, value).multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER));
    }

    /**
     * Устанавливает режущий урон с учетом глобального множителя
     *
     * @param value значение режущего урона
     */
    public void setCutting(MinMax value) {
        cutting = new MinMax(value)
                .multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Устанавливает режущий урон с учетом глобального множителя
     *
     * @param min минимальное значение
     * @param max максимальное значение
     */
    public void setCutting(double min, double max) {
        cutting = new MinMax(min, max)
                .multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER);
    }

    /**
     * Добавляет режущий урон к текущему с учетом глобального множителя
     *
     * @param value значение режущего урона
     */
    public void addCutting(MinMax value) {
        cutting.plus(value.multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER));
    }

    /**
     * Добавляет значение режущего урона к текущему с учетом глобального множителя
     *
     * @param value значение режущего урона
     */
    public void addCutting(double value) {
        addCutting(new MinMax(value, value).multiply(localDamageMultiplier * GLOBAL_DAMAGE_MULTIPLIER));
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

    /**
     * Возвращает копию объекта.
     */
    public MinMax getFire() {
        return new MinMax(fire);
    }

    /**
     * Возвращает копию объекта.
     */
    public MinMax getElectric() {
        return new MinMax(electric);
    }

    /**
     * Увеличивает локальный множитель урона
     */
    public void localMultiplierPlus(double multiplier) {
        localDamageMultiplier += multiplier;
    }

    /**
     * Уменьшает локальный множитель урона
     */
    public void localMultiplierMinus(double multiplier) {
        localDamageMultiplier -= multiplier;
    }

    /**
     * Класс возвращает копию смиксованного урона по всем типам учитывая мультипликаторы
     */
    public Damage mixed(Damage damage) {
        return Damage
                .builder()
                .piercing(piercing.plus(damage.getPiercing()).multiply(localDamageMultiplier))
                .crushing(crushing.plus(damage.getCrushing()).multiply(localDamageMultiplier))
                .cutting(cutting.plus(damage.getCutting()).multiply(localDamageMultiplier))
                .electric(electric.plus(damage.getElectric()).multiply(localDamageMultiplier))
                .fire(fire.plus(damage.getFire()).multiply(localDamageMultiplier))
                .build();
    }
}
