package mechanic.interfaces;

public interface Focusing extends Intelligent, Healthy, Reloadable {

    default void focus() {
        heal();
        shieldGrow();
        reload();
    }
}
