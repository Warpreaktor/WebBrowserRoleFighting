package fight;

import hero.Hero;
import hero.HeroService;
import hero.classes.Archer;
import hero.classes.Mage;
import core.GameMaster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static hero.constants.HeroConstants.PLAYER1;
import static hero.constants.HeroConstants.PLAYER2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CombatServiceTest {

    private FightServiceV2 fightServiceV2;

    public HeroService heroService;

    /**
     * Тест для подсчёта кто сколько раз ударил за матч.
     */
    @Test
    public void fight1() {
        heroService = mock(HeroService.class);

        fightServiceV2 = spy(new FightServiceV2(heroService, new FightResult(), new GameMaster()));

        Hero player1 = new Archer(PLAYER1);
        Hero player2 = new Mage(PLAYER2);

        when(heroService.get(PLAYER1))
                .thenReturn(player1);
        when(heroService.get(PLAYER2))
                .thenReturn(player2);

        for (int i = 0; i < 100; i++) {
            fightServiceV2.fight();
        }

        // Подсчитываем, сколько раз реально вызвался `combat`
        List<Invocation> details = Mockito.mockingDetails(fightServiceV2).getInvocations().stream()
                .filter(invocation -> invocation.getMethod().getName().equals("combat"))
                .collect(Collectors.toList());

        int player1Turns = 0;
        int player2Turns = 0;
        for (Invocation in : details) {
            if (
                    Objects.equals(player1, in.getArguments()[0])
                            && Objects.equals(player2, in.getArguments()[1])
            ) {
                player1Turns++;
            } else {
                player2Turns++;
            }
        }
        System.out.println(player1Turns);
        System.out.println(player2Turns);
    }

    /**
     * Player1 проиграл (HP <= 0)
     */
    @Test
    void checkWinner() {
        heroService = mock(HeroService.class);
        fightServiceV2 = new FightServiceV2(heroService,
                new FightResult(),
                new GameMaster());

        Hero player1 = new Archer(PLAYER1);
        Hero player2 = new Mage(PLAYER2);

        when(heroService.get(PLAYER1)).thenReturn(player1);
        when(heroService.get(PLAYER2)).thenReturn(player2);

        player1.getHealth().setValue(0D);
        player2.getHealth().setValue(10D);
        String result1 = fightServiceV2.fightIsOver();
        Assertions.assertEquals(PLAYER1 + " падает замертво", result1);
    }

    /**
     * Player2 проиграл (HP <= 0)
     */
    @Test
    void checkWinner2() {
        heroService = mock(HeroService.class);
        fightServiceV2 = new FightServiceV2(heroService,
                new FightResult(),
                new GameMaster());

        Hero player1 = new Archer(PLAYER1);
        Hero player2 = new Mage(PLAYER2);

        when(heroService.get(PLAYER1)).thenReturn(player1);
        when(heroService.get(PLAYER2)).thenReturn(player2);

        player1.getHealth().setValue(10D);
        player2.getHealth().setValue(0D);
        String result2 = fightServiceV2.fightIsOver();
        Assertions.assertEquals(PLAYER2 + " падает замертво", result2);
    }

    /**
     * Оба живы (HP > 0)
     */
    @Test
    void checkWinner3() {
        heroService = mock(HeroService.class);
        fightServiceV2 = new FightServiceV2(heroService, new FightResult(), new GameMaster());

        Hero player1 = new Archer(PLAYER1);
        Hero player2 = new Mage(PLAYER2);

        when(heroService.get(PLAYER1)).thenReturn(player1);
        when(heroService.get(PLAYER2)).thenReturn(player2);

        player1.getHealth().setValue(10D);
        player2.getHealth().setValue(10D);
        String result3 = fightServiceV2.fightIsOver();
        Assertions.assertEquals("", result3);
    }

    /**
     * Проверяем что победитель устанавливается корректно.
     * Player1 проиграл, Player2 должен быть победителем
     */
    @Test
    void checkWinner4() {
        heroService = mock(HeroService.class);
        FightResult mockResult = mock(FightResult.class);
        fightServiceV2 = new FightServiceV2(heroService,
                mockResult,
                new GameMaster());

        Hero player1 = new Archer(PLAYER1);
        Hero player2 = new Mage(PLAYER2);

        when(heroService.get(PLAYER1)).thenReturn(player1);
        when(heroService.get(PLAYER2)).thenReturn(player2);

        //Player1 проиграл, Player2 должен быть победителем
        player1.getHealth().setValue(0D);
        player2.getHealth().setValue(10D);
        fightServiceV2.fightIsOver();

        verify(mockResult, times(1)).setWinner(player2);
    }

    /**
     * Проверяем что победитель устанавливается корректно.
     * Player2 проиграл, Player1 должен быть победителем
     */
    @Test
    void checkWinner5() {
        heroService = mock(HeroService.class);
        FightResult mockResult = mock(FightResult.class);
        fightServiceV2 = new FightServiceV2(heroService,
                mockResult,
                new GameMaster());

        Hero player1 = new Archer(PLAYER1);
        Hero player2 = new Mage(PLAYER2);

        when(heroService.get(PLAYER1)).thenReturn(player1);
        when(heroService.get(PLAYER2)).thenReturn(player2);

        player1.getHealth().setValue(10D);
        player2.getHealth().setValue(0D);
        fightServiceV2.fightIsOver();

        verify(mockResult, times(1)).setWinner(player1);
    }

    /**
     * Проверяется, что щит после удара не будет регенерировать 1 ход, ни меньше ни больше.
     */
    @Test
    void shieldRecoveryAfterBlock() {
        // Создаем заглушки сервисов
        heroService = mock(HeroService.class);

        // Создаем двух магов
        Hero player1 = new Mage(PLAYER1);
        Hero player2 = new Mage(PLAYER2);
        when(heroService.get(PLAYER1)).thenReturn(player1);
        when(heroService.get(PLAYER2)).thenReturn(player2);

        FightResult mockResult = mock(FightResult.class);
        GameMaster gameMaster = new GameMaster();

        fightServiceV2 = new FightServiceV2(heroService, mockResult, gameMaster);

        //Чтобы точно попал
        player1.setAccuracy(100D);
        player1.setEndurance(1D);
        player1.getStaticDamage().setCrushing(1D, 1D);

        player2.getShield().setMaxValue(10D);
        player2.getShield().setValue(10D);

        // Маг атакует, у противника падает маг. щит и блокируется его восстановление
        while (player2.getShield().getValue() >= 10){
            fightServiceV2.fight();
        }

        assertTrue(player2.getShield().getIsBlocked().get(), "Щит должен быть заблокирован на 1 ход");

        player1.setAccuracy(0.0);
        player1.setEndurance(0.0);

        var shieldValue = player2.getShield().getValue();
        //Щит должен быть заблокирован
        fightServiceV2.fight();

        // Щит всё ещё заблокирован, т.к. был только 1 ход после атаки
        assertTrue(player2.getShield().getIsBlocked().get(), "Щит должен быть всё еще заблокирован");
        assertEquals(shieldValue, player2.getShield().getValue(), "Щит не должен восстанавливаться");

        player1.setAccuracy(0.0);
        player1.setEndurance(0.0);
        //Щит должен разблокироваться и восстановиться в конце раунда
        fightServiceV2.fight();

        assertFalse(player2.getShield().getIsBlocked().get(), "Щит должен разблокироваться");
        assertTrue(shieldValue < player2.getShield().getValue(), "Щит должен немного восстановиться после удара");
    }



}