package item.weapon;

import config.ApplicationProperties;
import hero.Hero;
import item.weapon.abstracts.Weapon;
import lombok.Getter;

import java.util.UUID;

import static constants.WeaponGlobalDamage.GRANDPAS_RUSTY_SWORD;

/**
 * Grandpa’s Rusty Sword - Дедов ржавый меч
 *
 * Какая игра без короткого меча, верно?
 *
 * <p><b>Байка</b></p>
 * <p>
 * Говорят, кузнец, что его перековывал, был слеп на один глаз и вечно пьян,
 * а потому меч сразу же покрылся ржавчиной, будто кузнец обидел саму сталь.
 * Твоему деду он достался не весть откуда, а потом помер и дед.
 * Поздравляю! Теперь этот кусок бесполезного металла – твой.
 * </p>
 *
 */
public class GrandpasRustySword extends Weapon {

    @Getter
    private static final String picturePath = ApplicationProperties.getHost() + "/images/weapon/blade/grandpas_rusty_sword.png";

    public GrandpasRustySword(Hero owner) {
        super(UUID.randomUUID().toString(), "Короткий меч", picturePath, GRANDPAS_RUSTY_SWORD, owner);

        getDamage().setPiercing(0.9, 1.5);
        getDamage().setCrushing(1.0, 1.0);

        setAttackSpeed(1.0);
        setThrowing(false);

        // Позволяет использовать меч в любой руке (dual wielding)
        setAsDualWearable();
    }

    @Override
    public void equiped(Hero owner) {
        owner.addAbilities(getAbilities());
    }

    @Override
    public void unequiped(Hero owner) {
        owner.getAbilities().removeAll(getAbilities());
    }
}
