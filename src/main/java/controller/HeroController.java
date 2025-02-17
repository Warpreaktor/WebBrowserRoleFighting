package controller;

import equip.EquipSlot;
import equip.Equipment;
import hero.Hero;
import hero.HeroService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import spark.Route;
import spark.Spark;
import spec.HeroClass;
import item.weapon.Weapon;
import item.weapon.ItemService;

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

        Spark.post("/hero/equipped/", equipped);
        Spark.post("/hero/unequipped/", unequipped);

        Spark.post("/hero/dropItem/", dropItem);

        Spark.post("/hero/takeItem/", takeItem);

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

    /**
     * Одеть предмет на персонажа
     */
    private final Route equipped = (req, res) -> {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(req.body(), JsonObject.class);

        if (jsonObject == null || !jsonObject.has("playerId")
                || !jsonObject.has("objectId")
                || !jsonObject.has("oldSlot")
                || !jsonObject.has("newSlot")
        ) {

            res.status(400);
            return gson.toJson("Ошибка: отсутствуют обязательные поля!");
        }

        String playerId = jsonObject.get("playerId").getAsString();
        String objectId = jsonObject.get("objectId").getAsString();
        Integer oldSlot = jsonObject.get("oldSlot").getAsInt();
        String newSlot = jsonObject.get("newSlot").getAsString();

        Weapon weapon = itemService.get(objectId).orElseThrow();
        Hero hero = heroService.get(playerId);
        EquipSlot objectSlot = EquipSlot.valueOf(newSlot);

        hero.equipped(objectSlot, weapon);

        res.status(200);
        return gson.toJson("Предмет экипирован в " + objectSlot);
    };

    /**
     * Снятие предмета с персонажа
     */
    private final Route unequipped = (req, res) -> {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(req.body(), JsonObject.class);

        if (jsonObject == null || !jsonObject.has("playerId")
                || !jsonObject.has("objectId")
                || !jsonObject.has("slot")) {

            res.status(400);
            return gson.toJson("Ошибка: отсутствуют обязательные поля!");
        }

        String playerId = jsonObject.get("playerId").getAsString();
        String objectId = jsonObject.get("objectId").getAsString();
        String slot = jsonObject.get("slot").getAsString();

        Hero hero = heroService.get(playerId);
        EquipSlot objectSlot = EquipSlot.valueOf(slot);

        hero.unequipped(objectSlot, objectId);

        res.status(200);
        return gson.toJson("Предмет снят из " + objectSlot);
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

        if (hero.dropItem(objectId, oldSlot, newSlot)) {
            res.status(200);
            return gson.toJson("Предмет перемещен в ячейку инвентаря [" + newSlot + "]");
        } else {
            res.status(400);
            return gson.toJson("Предмет не перемещён");
        }
    };

    @Deprecated
    private final Route takeItem = (req, res) -> {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(req.body(), JsonObject.class);

        if (jsonObject == null || !jsonObject.has("playerId")
                || !jsonObject.has("objectId")
                || !jsonObject.has("oldSlot")
        ) {

            res.status(400);
            return gson.toJson("Ошибка: отсутствуют обязательные поля!");
        }

        String playerId = jsonObject.get("playerId").getAsString();
        String objectId = jsonObject.get("objectId").getAsString();
        String oldSlot = jsonObject.get("oldSlot").getAsString();

        Hero hero = heroService.get(playerId);
        EquipSlot equipSlot = EquipSlot.valueOf(oldSlot.toUpperCase());

        if (hero.takeItem(objectId, equipSlot)) {
            res.status(200);
            return gson.toJson("Предмет схвачен");
        } else {
            res.status(400);
            return gson.toJson("Предмет не схвачен");
        }
    };
}
