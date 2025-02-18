package equip;

import item.weapon.Weapon;
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
}
