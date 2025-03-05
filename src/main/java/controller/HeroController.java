package controller;

import equip.EquipSlot;
import hero.Hero;
import hero.HeroService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import mechanic.Ability;
import spark.Route;
import spark.Spark;
import spec.HeroClass;
import item.ItemService;

public class HeroController {

    HeroService heroService;

    ItemService itemService;

    public HeroController() {
        heroService = HeroService.getInstance();
        itemService = ItemService.getInstance();
    }

    public void registerRoutes() {
        Spark.post("/createCharacter", createCharacter);
        Spark.get("/createRandomCharacter", createRandomCharacter);

        Spark.get("/getPlayer/:id", getPlayer);
        Spark.get("/getPlayer/statistic/:id", getPlayerStatistic);
        Spark.get("/getPlayer/abilities/:id", getPlayerAbilities);

        Spark.post("/hero/dropItem/", dropItem);

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
            Hero hero = heroService.createPlayerHero(name, type);
            return gson.toJson(hero.getInfo());

        } catch (IllegalArgumentException e) {
            res.status(400);
            return gson.toJson("Ошибка: некорректный класс героя!");
        }
    };

    private final Route createRandomCharacter = (req, res) -> {
        Hero randomHero = heroService.createPlayerHero(null, null);
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

        JsonObject jsonResponse = new JsonObject();
        var gson = new Gson();
        jsonResponse.add("characteristic", gson.toJsonTree(player.getCharacteristic()));
        jsonResponse.add("statistic", gson.toJsonTree(player.getStatistic()));

        res.status(200);
        return gson.toJson(jsonResponse);
    };

    private final Route dropItem = (req, res) -> {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(req.body(), JsonObject.class);

        if (jsonObject == null || !jsonObject.has("playerId")
                || !jsonObject.has("newSlot")
                || !jsonObject.has("objectId")
        ) {

            res.status(400);
            return gson.toJson("Ошибка: отсутствуют обязательные поля!");
        }

        String playerId = jsonObject.get("playerId").getAsString();
        String objectId = jsonObject.get("objectId").getAsString();

        EquipSlot oldSlot = EquipSlot.valueOf(
                jsonObject.get("oldSlot")
                        .getAsString()
                        .toUpperCase());

        EquipSlot newSlot = EquipSlot.valueOf(
                jsonObject.get("newSlot")
                        .getAsString()
                        .toUpperCase());

        Hero hero = heroService.get(playerId);

        if (hero.moveItem(objectId, oldSlot, newSlot)) {
            res.status(200);
            return gson.toJson("Предмет перемещен в ячейку инвентаря [" + newSlot + "]");
        } else {
            res.status(400);
            return gson.toJson("Предмет не перемещён");
        }
    };

    /**
     * Возвращает способности героя
     */
    private final Route getPlayerAbilities = (req, res) -> {
        String id = req.params("id");

        var player = heroService.get(id);

        return new Gson().toJson(player.getAbilities()
                .stream()
                .map(Ability::toDto)
                .toList());
    };
}
