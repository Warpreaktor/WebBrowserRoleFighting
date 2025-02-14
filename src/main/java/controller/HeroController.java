package controller;

import character.Hero;
import character.HeroService;
import com.google.gson.Gson;
import spark.Route;
import spark.Spark;
import spec.HeroClass;

public class HeroController {

    HeroService heroService;

    public HeroController() {
        this.heroService = HeroService.getInstance();
    }

    public void registerRoutes() {
        Spark.get("/createCharacter", createCharacter);
        Spark.get("/createRandomCharacter", createRandomCharacter);
        Spark.get("/getPlayer/:id", getPlayer);
    }

    private final Route createCharacter = (req, res) -> {
        String name = req.queryParams("name");
        String heroClass = req.queryParams("heroClass");

        if (name == null || name.trim().isEmpty() || heroClass == null) {
            res.status(400);
            return "Ошибка: имя и класс героя обязательны!";
        }

        try {
            HeroClass type = HeroClass.valueOf(heroClass.toUpperCase());
            Hero hero = heroService.createHero(name, type);
            return "Создан герой: " + hero.getInfo();
        } catch (IllegalArgumentException e) {
            res.status(400);
            return "Ошибка: некорректный класс героя!";
        }
    };

    private final Route createRandomCharacter = (req, res) -> {
//        res.type("application/json");

        Hero randomHero = heroService.createHero(null, null);
        return new Gson().toJson(randomHero.getInfo());
    };

    private final Route getPlayer = (req, res) -> {
        res.type("application/json");

        String id = req.params("id");

        Hero player = heroService.get(id);
        return new Gson().toJson(player.getInfo());
    };
}
