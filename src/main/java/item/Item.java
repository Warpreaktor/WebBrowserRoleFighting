package item;

import hero.Hero;
import lombok.Getter;
import ability.Ability;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Item {

    private final String id;

    private final String name;

    private final String picture;

    private final transient Hero owner;

    /**
     * Способности предмета. Если нет, то пустой список.
     */
    private final List<Ability> abilities ;

    public Item(String id, String name, String picturePath, Hero owner) {
        this.id = id;
        this.name = name;
        this.picture = picturePath;
        this.owner = owner;
        this.abilities = new ArrayList<>(6);
    }

    /**
     * Предемт должен применить на героя все свои особенности при взятии его в инвентарь
     *
     * @param owner владелец
     */
    public abstract void equiped(Hero owner);

    /**
     * При снятии предмета, все особенности необходимо откатить
     *
     * @param owner владелец
     */
    public abstract void unequiped(Hero owner);
}
