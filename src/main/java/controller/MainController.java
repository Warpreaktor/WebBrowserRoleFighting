package controller;

import character.HeroService;
import dto.hero.HeroRequestDto;
import com.google.gson.Gson;
import fight.FightService;
import spark.Route;
import spark.Spark;
import spec.HeroClass;

import static character.constants.HeroConstants.PLAYER1;
import static character.constants.HeroConstants.PLAYER2;

public class MainController {

    HeroService heroService;

    public MainController() {
        this.heroService = HeroService.getInstance();
    }

    public void registerRoutes() {
        Spark.post("/start", start);
    }

    //TODO вынести логику из контроллера в MainService
    private final Route start = (req, res) -> {
        Gson gson = new Gson();
        HeroRequestDto player = gson.fromJson(req.body(), HeroRequestDto.class);

        FightService.newInstance();

        heroService
                .saveCharacter(PLAYER1,
                        heroService.createHero(player.getName(), HeroClass.valueOf(player.getHeroClass())));

        heroService
                .saveCharacter(PLAYER2,
                        heroService.createHero(null, null));

        return true;
    };
}
