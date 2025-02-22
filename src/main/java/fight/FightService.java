package fight;

import hero.Hero;
import hero.HeroService;
import commentator.CommentatorService;
import core.GameMaster;
import fight.dto.AttackDto;
import fight.dto.DefenseDto;
import fight.dto.FightResultDto;
import tools.Dice;

import static hero.constants.HeroConstants.PLAYER1;
import static hero.constants.HeroConstants.PLAYER2;

public class FightService {

    private static FightService instance;

    private final HeroService heroService;

    private final FightResult result;

    private final GameMaster gameMaster;

    public FightService(
            HeroService heroService,
            FightResult fightResult,
            GameMaster gameMaster
    ) {
        this.heroService = heroService;
        this.result = fightResult;
        this.gameMaster = gameMaster;
    }

    public static FightService getInstance() {
        if (instance == null) {
            instance = new FightService(
                    HeroService.getInstance(),
                    new FightResult(),
                    new GameMaster()

            );
            return instance;
        }

        return instance;
    }

    public static FightService newInstance() {
        instance = null;
        return getInstance();
    }

    //==================================================//
//   ▄█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▄   //
//  ▄█                                          █▄  //
// ███          ⚔️ МЕХАНИКА БОЯ ⚔️              ███ //
//  ▀█                                          █▀  //
//   ▀█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▀   //
//==================================================//
    public FightResultDto fight() {
        if (result.isOver()) {
            return result.getResultDto();
        }

        Hero player1 = heroService.get(PLAYER1);
        Hero player2 = heroService.get(PLAYER2);

        result.clear();
        result.addEventAndLog("Раунд - [" + gameMaster.nextRound() + "]");


        if (isAnyBodyDeath(player1, player2)) {
            result.addEventAndLog(result.getWinner() + " пинает мёртвое тело");
        }

        //TODO Написать функцию определяющую того кто первый ходит.
        // Она может основываться на какой-то из характеристик персонажа
        // Если эти характеристики равны, то старый добрый рандом
        // Они определяются раз и на весь матч.
        int player1Dice = Dice.rollSix();
        int player2Dice = Dice.rollSix();
        Hero attacker;
        Hero defender;

        if (player1Dice == player2Dice) {
            result.addEventAndLog(CommentatorService
                    .getRandomBattleMessage(
                            player1.getName(), player2.getName()));

            player1.focus();
            player2.focus();
            return result.getResultDto();
        }

        if (player1Dice > player2Dice) {
            attacker = player1;
            defender = player2;
        } else {
            attacker = player2;
            defender = player1;
        }

        combat(attacker, defender);

        return result.getResultDto();
    }

    public void combat(Hero attacker, Hero defender) {

        combatMoves(attacker, defender);

        result.addEventAndLog(fightIsOver());

        if (!result.isOver()) {
            combatMoves(defender, attacker);
        }

        if (!result.isOver()) {
            attacker.focus();
            defender.focus();
        }
    }

    public void combatMoves(Hero attacker, Hero defender) {

        AttackDto attackResult = attackPhase(attacker, defender);
        result.addEventAndLog(attackResult.getMessage());

        if (attackResult.isFail()) {
            return;
        }

        DefenseDto defenseResult = defensePhase(attackResult, defender);

        gameMaster.block(defender.getShield().getIsBlocked(), 1);

        result.addEventAndLog(defenseResult.getMessage());
    }

//==================================================//
//   ▄█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▄    //
//  ▄█                                          █▄  //
// ███          💢 ФАЗА АТАКИ 💢                ███ //
//  ▀█                                          █▀  //
//   ▀█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▀    //
//==================================================//
    private AttackDto attackPhase(Hero attacker, Hero defender) {

        if (gameMaster.isHit(attacker, defender)) {
            return attacker.attack(defender);
        } else {
            return attacker.doMissedEvent();
        }
    }

//==================================================//
//   ▄█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▄    //
//  ▄█                                          █▄  //
// ███          ⚔️ ФАЗА ЗАЩИТЫ ⚔️               ███ //
//  ▀█                                          █▀  //
//   ▀█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▀    //
//==================================================//
    private DefenseDto defensePhase(AttackDto attack, Hero defender) {
        return defender.defense(attack);
    }

    /**
     * Метод проверяет здоровье каждого персонажа.
     * Если здоровье одного из них ниже нуля, то бой заканчивается.
     * Победителем объявляется персонаж, чьё здоровье выше нуля.
     *
     * @return описание события.
     */
    public String fightIsOver() {
        Hero player1 = heroService.get(PLAYER1);
        Hero player2 = heroService.get(PLAYER2);

        if (player1.getHealth().getValue() <= 0) {
            result.setWinner(player2);
            player2.getStatistic().plusWin();
            return player1.getName() + " падает замертво";

        } else if (player2.getHealth().getValue() <= 0) {
            result.setWinner(player1);
            player1.getStatistic().plusWin();
            return player2.getName() + " падает замертво";

        } else {
            return "";
        }
    }

    private boolean isAnyBodyDeath(Hero player1, Hero player2) {
        return player1.getHealth().getIsDead()
                || player2.getHealth().getIsDead();
    }
}
