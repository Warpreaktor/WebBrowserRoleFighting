package core;

import dto.hero.HeroRequestDto;
import fight.FightService;
import hero.Hero;
import hero.HeroService;
import spec.HeroClass;

import static hero.constants.HeroConstants.PLAYER1;
import static hero.constants.HeroConstants.PLAYER2;

public class GameService {

    static GameService instance;

    HeroService heroService;

    private GameService() {
        heroService = HeroService.getInstance();
    }


    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
            return instance;
        }

        return instance;
    }

    /**
     * Сброс текущей и старт новой игры.
     *
     * @param player персонаж игрока.
     */
    public void startNewGame(HeroRequestDto player) {
        FightService.newInstance();

        heroService
                .saveCharacter(PLAYER1,
                        heroService.createHero(player.getName(), HeroClass.valueOf(player.getHeroClass())));

        heroService.createLevelingHeroes(10);

        heroService
                .saveCharacter(PLAYER2,
                        heroService.getLevelingHero(0));

    }

    /**
     * Продолжаем игру тем же персонажем
     */
    public void continueGame() {
        var player = heroService.get(PLAYER1);

        if (player.getStatistic().getWins() <= 0) {
            return;
        }

        //Сохраняем нового левельного моба в качестве текущего игрока player2
        heroService
                .saveCharacter(PLAYER2,
                        heroService.getLevelingHero(
                                player.getStatistic().getWins()));

        FightService.newInstance();
    }

    public void startTestGame() {
        FightService.newInstance();

        Hero player = heroService
                .saveCharacter(PLAYER1,
                        heroService.createHero(null, null));

        player.getStatistic().plusWin();

        heroService
                .saveCharacter(PLAYER2,
                        heroService.createHero(null, null));

    }
}
