package controller;

import character.Hero;
import character.HeroService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Route;
import spark.Spark;
import spec.HeroClass;

public class HeroController {

    HeroService heroService;

    public HeroController() {
        this.heroService = HeroService.getInstance();
    }

    public void registerRoutes() {
        Spark.post("/createCharacter", createCharacter);
        Spark.get("/createRandomCharacter", createRandomCharacter);
        Spark.get("/getPlayer/:id", getPlayer);
        Spark.get("/getPlayer/statistic:id", getPlayerStatistic);
    }

    private final Route createCharacter = (req, res) -> {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(req.body(), JsonObject.class);

        if (jsonObject == null
                || !jsonObject.has("name")
                || !jsonObject.has("heroClass")) {

            res.status(400);
            return gson.toJson("Ошибка: имя и класс героя обязательны!");
        }

        String name = jsonObject.get("name").getAsString();
        String heroClass = jsonObject.get("heroClass").getAsString();

        if (name.isEmpty()) {
            res.status(400);
            return gson.toJson("Ошибка: имя не может быть пустым!");
        }

        try {
            HeroClass type = HeroClass.valueOf(heroClass.toUpperCase());
            Hero hero = heroService.createHero(name, type);
            return gson.toJson(hero.getInfo());

        } catch (IllegalArgumentException e) {
            res.status(400);
            return gson.toJson("Ошибка: некорректный класс героя!");
        }
    };

    private final Route createRandomCharacter = (req, res) -> {
        Hero randomHero = heroService.createHero(null, null);
        return new Gson().toJson(randomHero.getInfo());
    };

    private final Route getPlayer = (req, res) -> {
        String id = req.params("id");

        Hero player = heroService.get(id);
        return new Gson().toJson(player.getInfo());
    };

    private final Route getPlayerStatistic = (req, res) -> {
        String id = req.params("id");

        Hero player = heroService.get(id);
        return new Gson().toJson(player.getStatistic());
    };
}
