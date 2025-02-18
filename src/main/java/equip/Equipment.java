package equip;

import item.Item;
import lombok.Getter;
import item.weapon.Fist;
import item.weapon.Weapon;

import java.util.Objects;

/**
 * Экипировка персонажа
 */
@Getter
public class Equipment {

    /**
     * Кулак всегда один и он сохраняется как объект на персонаже.
     * При снятии оружия он становится на его место.
     */
    private final Fist fist;

    /**
     * В правой руке может быть только оружие.
     */
    private Weapon rightHand;

    public Equipment() {
        fist = new Fist();
        rightHand = fist;
    }

    /**
     * Абстрактный метод, позволяющий установить какой-то предмет в какой-то слот.
     */
    public boolean equipped(EquipSlot slot, Item item) {
        if (item == null){
            return false;
        }

        switch (slot) {
            case RIGHT_HAND:
                if (item instanceof Weapon) {
                    takeWeapon((Weapon) item);
                    break;
            } else {
                    System.out.println("Это не вставить в правую руку");
                    return false;
                }
        }
        return true;
    }

    /**
     * Взять оружие в правую руку
     */
    public void takeWeapon(Weapon weapon) {
        rightHand = Objects.requireNonNullElse(weapon, fist);
    }

    public EquipmentDto getEquipmentDto() {

        return EquipmentDto
                .builder()
                .rightHand(rightHand == fist ? null : rightHand)
                .build();
    }
}
