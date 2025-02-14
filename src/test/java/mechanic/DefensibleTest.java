package mechanic;

import character.Hero;
import character.HeroService;
import character.classes.Archer;
import character.classes.Mage;
import character.classes.Warrior;
import fight.dto.AttackDto;
import mechanic.interfaces.Defensible;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class DefensibleTest {

    HeroService heroService = HeroService.getInstance();

    /**
     * Проверяется, что весь физический дэмедж входит в щит
     */
    @ParameterizedTest
    @MethodSource("defenseAll")
    public void defensibleTest1(Hero defensible) {
        AttackDto attack = new AttackDto();
        attack.setAttacker(
                heroService.createHero(null, null));
        attack.setDamageDto(new DamageDto(10.0, 0.0));

        var shield = new Shield();
        shield.setValue(10.0);
        shield.setMaxValue(10.0);

        defensible.setShield(shield);
        defensible.defense(attack);
        Assertions.assertEquals(0.0, shield.getValue());
    }

    /**
     * Проверяется, что при пробитии щита урон получит здоровье героя.
     */
    @ParameterizedTest
    @MethodSource("defenseAll")
    public void defensibleTest2(Hero defensible) {
        AttackDto attack = new AttackDto();
        attack.setAttacker(
                heroService.createHero(null, null));
        attack.setDamageDto(new DamageDto(10.0, 0.0));

        defensible.getShield().setValue(5.0);
        defensible.getShield().setMaxValue(5.0);

        defensible.getHealth().setValue(10.0);
        defensible.getHealth().setMaxValue(10.0);

        defensible.defense(attack);
        Assertions.assertEquals(5.0,
                defensible.getHealth().getValue());
    }

    static Stream<Arguments> defenseAll(){
        return Stream.of(
                Arguments.of(new Warrior("defensible")),
                Arguments.of(new Archer("defensible")),
                Arguments.of(new Mage("defensible"))
        );
    }
}
