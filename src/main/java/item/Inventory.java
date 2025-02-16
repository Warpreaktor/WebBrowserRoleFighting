package item;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Inventory {

    /**
     * Ячейки инвентаря. В каждой ячейки один предмет.
     */
    private final Item[] cells;

    public Inventory() {
        cells = new Item[12];
    }

    public boolean add(Item item) {
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] == null) {
                cells[i] = item;
                return true;
            }
        }
        return false;
    }

    public boolean moveItem(String objectId, int oldSlot, int newSlot) {
        if (cells[newSlot] != null) {
            return false;
        }

        if (Objects.equals(cells[oldSlot].getId(), objectId)) {
            cells[newSlot] = cells[oldSlot];
            cells[oldSlot] = null;
        }

        return true;
    }
}
