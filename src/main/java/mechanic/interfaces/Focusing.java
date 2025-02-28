package mechanic.interfaces;

public interface Focusing extends Intelligent, Healthy, Restable {

    default void focus() {
        heal();
        shieldGrow();
        rest();
    }
}
