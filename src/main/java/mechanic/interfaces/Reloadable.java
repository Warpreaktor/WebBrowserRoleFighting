package mechanic.interfaces;

/**
 * Перезаряжается прежде чем ударить.
 * Имеет скорость перезарядки.
 */
public interface Reloadable {

    Double getReloader();

    void setReloader(Double value);

    void reload();

    Double getAgility();
}
