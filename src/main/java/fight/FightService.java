package fight;

import hero.Hero;
import hero.HeroService;
import core.GameMaster;
import dto.attack.AttackDto;
import dto.defense.DefenseDto;
import dto.fightresult.FightResultDto;
import lombok.Getter;

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
public class FightService {

    private static FightService instance;

    private final HeroService heroService;

    @Getter
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
                    GameMaster.newInstance()

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

        return combatMoves();
    }

    public FightResultDto combatMoves() {
        Hero player1 = heroService.get(PLAYER1);
        Hero player2 = heroService.get(PLAYER2);

        var playebleHero = gameMaster.nextTurn();

        if (Objects.equals(playebleHero, player1)) {
            player1.focus();
            return result.getResultDto();
        }

        player2.getTactic().turn(player1, result);

        player2.focus();

        //Компьютерный игрок передаёт ход
        fight();

        return result.getResultDto();
    }

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
