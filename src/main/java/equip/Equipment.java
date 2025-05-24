package equip;

import hero.Hero;
import item.Item;
import item.offhand.Offhand;
import lombok.Getter;
import item.weapon.Fist;
import item.weapon.abstracts.Weapon;
import lombok.extern.slf4j.Slf4j;

/**
 * Экипировка персонажа
 */
@Slf4j
@Getter
public class Equipment {

    /**
     * Владелец экипировки
     */
    private final Hero owner;

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
    private Offhand leftHand;

    /**
     * Двуручное оружие занимает обе руки
     */
    private Weapon bothHands;

    public Equipment(Hero owner) {
        this.owner = owner;
        this.fist = new Fist(owner);
        equipRightHand(fist);
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
                    weapon.equiped(owner);
                    return equipRightHand(weapon);
                } else {
                    log.info("Этот предмет нельзя экипировать в правую руку");
                    return false;
                }
            case LEFT_HAND:
                if (item instanceof Offhand offhand) {
                    return equipLeftHand(offhand);
                } else {
                    log.info("Этот предмет нельзя экипировать в левую руку");
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

        if (rightHand == fist) {
            rightHand = weapon;
        }

        owner.addAbilities(weapon.getAbilities());
        owner.getBaseDamage().plusDamage(weapon.getDamage());

        return true;
    }

    /**
     * Экипировать предмет в левую руку
     */
    private boolean equipLeftHand(Offhand item) {
        if (bothHands != null) {
            bothHands = null;
        }

        leftHand = item;
        return true;
    }

    public void unequipLeftHand() {
        leftHand = null;
    }

    public void unequipRightHand() {
        Weapon weapon = rightHand;
        owner.getAbilities().removeAll(rightHand.getAbilities());
        rightHand = fist;
        owner.getBaseDamage().minusDamage(weapon.getDamage());
    }

    public EquipmentDto getEquipmentDto() {
        return EquipmentDto.builder()
                .rightHand(rightHand)
                .leftHand(leftHand)
                .build();
    }
}
