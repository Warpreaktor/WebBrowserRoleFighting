package item;

import lombok.Getter;
import mechanic.Ability;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Item {

    private final String id;

    private final String name;

    private final String picture;

    /**
     * Способности предмета. Если нет, то пустой список.
     */
    private final List<Ability> abilities ;

    public Item(String id, String name, String picturePath) {
        this.id = id;
        this.name = name;
        this.picture = picturePath;
        this.abilities = new ArrayList<>(6);
    }
}
