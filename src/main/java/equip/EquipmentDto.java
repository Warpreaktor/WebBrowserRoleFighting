package equip;

import item.offhand.Offhand;
import item.weapon.abstracts.Weapon;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class EquipmentDto {

    /**
     * Оружие в правой руке.
     */
    private Weapon rightHand;

    /**
     * Предмет в левой руке (щит, offhand или оружие)
     */
    private Offhand leftHand;

    /**
     * Двуручное оружие
     */
    private Weapon bothHands;
}
