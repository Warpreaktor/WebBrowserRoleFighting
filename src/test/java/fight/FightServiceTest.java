package fight;

import hero.HeroService;
import hero.Hero;
import core.GameMaster;
import fight.dto.FightResultDto;
import hero.classes.Archer;
import hero.classes.Mage;
import hero.classes.Warrior;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Stream;

import static hero.constants.HeroConstants.PLAYER1;
import static hero.constants.HeroConstants.PLAYER2;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FightServiceTest {

    private static final int TEST_FIGHT_COUNT = 10;

    private static final double LOOSES_FOR_FAIL = TEST_FIGHT_COUNT * 0.2;

    private static final double AVERAGE_ROUND_COUNT = 30;

    private FightService fightService;

    public HeroService heroService;

    @ParameterizedTest
    @MethodSource("all")
    public void testFights(Class<? extends Hero> player1Class, Class<? extends Hero> player2Class) {
        testFight(player1Class, player2Class);
    }

    private static Stream<Arguments> all() {
        return Stream.of(
                Arguments.of(Warrior.class, Warrior.class),
                Arguments.of(Warrior.class, Mage.class),
                Arguments.of(Warrior.class, Archer.class),

                Arguments.of(Archer.class, Archer.class),
                Arguments.of(Archer.class, Warrior.class),
                Arguments.of(Archer.class, Mage.class),

                Arguments.of(Mage.class, Mage.class),
                Arguments.of(Mage.class, Archer.class),
                Arguments.of(Mage.class, Warrior.class)
        );
    }

    private static Stream<Arguments> warrior() {
        return Stream.of(
                Arguments.of(Warrior.class, Warrior.class),
                Arguments.of(Warrior.class, Mage.class),
                Arguments.of(Warrior.class, Archer.class)
        );
    }

    protected void testFight(Class<? extends Hero> player1Class, Class<? extends Hero> player2Class) {
        heroService = mock(HeroService.class);

        Hero player1 = null;
        Hero player2 = null;

        int player1Wins = 0;
        int player2Wins = 0;
        FightResultDto result;

        LinkedList<Integer> average = new LinkedList<>();

        for (int i = 0; i < TEST_FIGHT_COUNT; i++) {
            fightService = new FightService(
                    heroService,
                    new FightResult(),
                    new GameMaster());

            try {
                // Создаём экземпляры персонажей через рефлексию
                player1 = player1Class.getDeclaredConstructor(String.class).newInstance(PLAYER1);
                player2 = player2Class.getDeclaredConstructor(String.class).newInstance(PLAYER2);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при создании экземпляров персонажей", e);
            }

            when(heroService.get(PLAYER1)).thenReturn(player1);
            when(heroService.get(PLAYER2)).thenReturn(player2);

            int roundCount = 0;
            do {
                result = fightService.fight();
                roundCount++;
            } while (!result.isOver());

            average.add(roundCount);

            if (Objects.equals(result.getWinner(), player1)) {
                player1Wins++;
            } else {
                player2Wins++;
            }
        }

        double aver = average
                .stream()
                .reduce(Integer::sum)
                .get()
                .doubleValue() / TEST_FIGHT_COUNT;

        System.out.println("Среднее количество раундов в матче = " + aver);
        System.out.println(player1.getHeroClass() + " победил " + player2.getHeroClass() + " " + player1Wins + " раз из " + TEST_FIGHT_COUNT);

        Assertions.assertTrue(player1Wins - player2Wins > - LOOSES_FOR_FAIL,
                player1.getHeroClass() + " проиграл " + player2.getHeroClass() + " " + player2Wins + " раз из " + TEST_FIGHT_COUNT);

        Assertions.assertTrue(
                aver < AVERAGE_ROUND_COUNT,
                "среднее количество раундов > " + AVERAGE_ROUND_COUNT);

    }
}