package fight;

import hero.Hero;
import hero.HeroService;
import core.GameMaster;
import dto.attack.AttackDto;
import dto.defense.DefenseDto;
import dto.fightresult.FightResultDto;

import java.util.Objects;

import static hero.constants.HeroConstants.PLAYER1;
import static hero.constants.HeroConstants.PLAYER2;

/**
 * Вторая имплементация поединка.
 * В нём не будет просто цикла как раньше. Теперь противники сражаются тактически.
 * У каждого есть очки действий и способности, которые они будут применять.
 * Обмены ударами происходят вне этого сервиса.
 * Этот сервис лишь следит за фазами боя и содержит вызовы для компьютерного игрока.
 */
public class FightServiceV2 {

    private static FightServiceV2 instance;

    private final HeroService heroService;

    private final FightResult result;

    private final GameMaster gameMaster;

    public FightServiceV2(
            HeroService heroService,
            FightResult fightResult,
            GameMaster gameMaster
    ) {
        this.heroService = heroService;
        this.result = fightResult;
        this.gameMaster = gameMaster;
    }

    public static FightServiceV2 getInstance() {
        if (instance == null) {
            instance = new FightServiceV2(
                    HeroService.getInstance(),
                    new FightResult(),
                    GameMaster.newInstance()

            );
            return instance;
        }

        return instance;
    }

    public static FightServiceV2 newInstance() {
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

        result.clear();

        result.setRoundCount(gameMaster.nextRound());

        return combatMoves();
    }

    public FightResultDto combatMoves() {
        Hero player1 = heroService.get(PLAYER1);
        Hero player2 = heroService.get(PLAYER2);

        var playebleHero = gameMaster.nextTurn();

        //TODO продумать как именно будет проходить фаза атаки человеческого игрока.
        if (Objects.equals(playebleHero, player1)) {
            return result.getResultDto();
        }

        AttackDto attackResult = attackCpu(player2, player1);

        if (attackResult.isFail()) {
            return result.getResultDto();
        }

        if (attackResult.isCritical()) {
            result.addEventAndLog(String.format(
                    "%s. !!!КРИТИЧЕСКИ УРОН!!! [%s]",
                    attackResult.getMessage(),
                    attackResult.getDamageDto().getSumDamage()));
        } else {
            result.addEventAndLog(String.format(
                    "%s. урон[%s]",
                    attackResult.getMessage(),
                    attackResult.getDamageDto().getSumDamage()));
        }

        DefenseDto defenseResult = defensePhase(attackResult, player1);

        gameMaster.switchOn(player1.getShield(), 1);

        result.addEventAndLog(defenseResult.getMessage());

        return result.getResultDto();
    }

    //==================================================//
//   ▄█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▀█▄    //
//  ▄█                                          █▄  //
// ███          💢 ФАЗА АТАКИ 💢                ███ //
//  ▀█                                          █▀  //
//   ▀█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▄█▀    //
//==================================================//
    // TODO Продумать алгоритм того, как будет проходить фаза атаки у компьютерного игрока
    public AttackDto attackCpu(Hero attacker, Hero defender) {

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
