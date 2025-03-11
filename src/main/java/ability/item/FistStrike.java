package ability.item;

import config.ApplicationProperties;
import dto.attack.AttackDto;
import enums.AbilityType;
import item.weapon.abstracts.Weapon;
import lombok.Getter;
import mechanic.Ability;
import mechanic.interfaces.Defensible;

import static enums.AbilityType.ENEMY_TARGET;
import static tools.Dice.byMinMaxChance;

/**
 * Удар кулаком
 */
public class FistStrike extends Ability {

    private static final String PICTURE_PATH = ApplicationProperties.getHost() + "/images/ability/item/fist_strike.png";

    private static final String NAME = "Удар кулаком";

    private static final AbilityType TYPE = ENEMY_TARGET;

    private static final String DESCRIPTION = "Бьёт кулаком как дробящим уроном!";

    private static final int COST = 1;

    private static final int COOL_DOWN = 0;

    //==================================================//
    //   ▄█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▄    //
    //  ▄█                                          █▄  //
    // ███       💢 СПЕЦИАЛЬНЫЕ СВОЙСТВА 💢        ███ //
    //  ▀█                                          █▀  //
    //   ▀█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▀    //
    //==================================================//
    /**
     * Информация о предмете которому принадлежит способность.
     */
    @Getter
    private final Weapon weapon;

    public FistStrike(Weapon abilityOwner) {
        super(PICTURE_PATH, NAME, TYPE, DESCRIPTION, COST, COOL_DOWN);

        this.weapon = abilityOwner;
    }

    @Override
    public void apply(Defensible target) {

        var damageDto = byMinMaxChance(weapon.getDamage());

        var attack = new AttackDto(damageDto, "ДЫЩ кулаком!");

        target.defense(attack);
    }

}
