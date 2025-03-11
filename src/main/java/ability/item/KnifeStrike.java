package ability.item;

import config.ApplicationProperties;
import dto.MinMax;
import dto.attack.AttackDto;
import dto.damage.DamageDto;
import enums.AbilityType;
import item.weapon.abstracts.Weapon;
import lombok.Getter;
import mechanic.Ability;
import mechanic.Damage;
import mechanic.interfaces.Defensible;

import static constants.GlobalDamage.KNIFE_STRIKE;
import static enums.AbilityType.ENEMY_TARGET;
import static tools.Dice.byMinMaxChance;

/**
 * Удар ножом
 */
public class KnifeStrike extends Ability {

    private static final String PICTURE_PATH = ApplicationProperties.getHost() + "/images/ability/item/knife_strike.png";

    private static final String NAME = "Удар ножом";

    private static final AbilityType TYPE = ENEMY_TARGET;

    private static final String DESCRIPTION = "Наносит противнику колющий урон";

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

    public KnifeStrike(Weapon abilityOwner) {
        super(PICTURE_PATH, NAME, TYPE, DESCRIPTION, COST, COOL_DOWN);

        this.weapon = abilityOwner;

    }

    @Override
    public void apply(Defensible target) {

        var damageDto = byMinMaxChance(weapon.getDamage());

        var attack = new AttackDto(damageDto, "ПЫРЬ ножом!!");

        target.defense(attack);
    }

}
