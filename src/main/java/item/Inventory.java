package item;

import lombok.Getter;

@Getter
public class Inventory {

    /**
     * Ячейки инвентаря. В каждой ячейки один предмет.
     */
    private final Item[] cells;

    public Inventory() {
        cells = new Item[12];
    }

    public boolean put(Item item) {
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] == null) {
                cells[i] = item;
                return true;
            }
        }
        return false;
    }

    public boolean put(Item item, int slot) {
        if (cells[slot] != null) {
            return false;
        }

        cells[slot] = item;
        return true;
    }
}
