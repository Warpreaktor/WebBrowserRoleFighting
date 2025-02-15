package item;

import lombok.Getter;

@Getter
public abstract class Item {

    private final String id;

    private final String name;

    private final String picture;

    public Item(String id, String name, String picturePath) {
        this.id = id;
        this.name = name;
        this.picture = picturePath;
    }
}
