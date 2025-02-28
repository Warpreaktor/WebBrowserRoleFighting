package mechanic.interfaces;

/**
 * Перезаряжается прежде чем ударить.
 * Имеет скорость перезарядки.
 */
public interface Restable {

    Double getEndurance();

    void setEndurance(Double value);

    void rest();

    Double getAgility();
}
