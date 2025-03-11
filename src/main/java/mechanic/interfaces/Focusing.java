package mechanic.interfaces;

public interface Focusing extends HasMagicScreen, Healthy, Endured {

    default void focus() {
        getHealth().grow();
        getMagicScreen().grow();
        getEndurance().grow();
    }
}
