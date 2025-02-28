package equip;

import item.Item;
import item.WearableItem;
import lombok.Getter;
import item.weapon.Fist;
import item.weapon.abstracts.Weapon;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Экипировка персонажа
 */
@Slf4j
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

    /**
     * В левой руке может быть щит, offhand предмет или оружие (если его можно держать в левой руке)
     */
    private WearableItem leftHand;

    /**
     * Двуручное оружие занимает обе руки
     */
    private Weapon bothHands;

    public Equipment() {
        fist = new Fist();
        rightHand = fist;
        leftHand = null;
        bothHands = null;
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
                if (item instanceof Weapon weapon) {
                    return equipRightHand(weapon);
                } else {
                    log.info("Этот предмет нельзя экипировать в правую руку");
                    return false;
                }
            case LEFT_HAND:
                if (item instanceof WearableItem wearableItem && wearableItem.canEquipToSlot(EquipSlot.LEFT_HAND)) {
                    return equipLeftHand(wearableItem);
                } else {
                    log.info("Этот предмет нельзя экипировать в левую руку");
                    return false;
                }
            case BOTH_HANDS:
                if (item instanceof Weapon weapon && weapon.getTwoHand()) {
                    return equipBothHands(weapon);
                } else {
                    log.info("Этот предмет не является двуручным");
                    return false;
                }
            default:
                return false;
        }
    }

    /**
     * Экипировать предмет в правую руку
     */
    private boolean equipRightHand(Weapon weapon) {
        if(bothHands != null) {
            bothHands = null;
        }

        // Если это двуручное оружие, используем метод для двуручного
        if (weapon.getTwoHand()) {
            return equipBothHands(weapon);
        }

        rightHand = weapon.getRightHand() ? weapon : fist;
        return true;
    }

    /**
     * Экипировать предмет в левую руку
     */
    private boolean equipLeftHand(WearableItem item) {
        if (bothHands != null) {
            bothHands = null;
        }

        if (item instanceof Weapon weapon) {
            if (!weapon.getLeftHand()) {
                log.info("Это оружие нельзя держать в левой руке");
                return false;
            }
        }

        leftHand = item;
        return true;
    }

    /**
     * Экипировать двуручное оружие
     */
    private boolean equipBothHands(Weapon weapon) {
        if (!weapon.getTwoHand()) {
            log.info("Это оружие не является двуручным");
            return false;
        }

        rightHand = null;
        leftHand = null;
        bothHands = weapon;
        return true;
    }

    public void unequipLeftHand() {
        leftHand = null;
    }

    /**
     * Взять оружие в правую руку
     */
    public void takeWeapon(Weapon weapon) {
        rightHand = Objects.requireNonNullElse(weapon, fist);
    }

    public EquipmentDto getEquipmentDto() {
        return EquipmentDto.builder()
                .rightHand(rightHand == fist ? null : rightHand)
                .leftHand(leftHand)
                .bothHands(bothHands)
                .build();
    }
}
