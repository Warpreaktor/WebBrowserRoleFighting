package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import hero.Hero;
import hero.HeroService;
import item.weapon.ItemService;
import spark.Route;
import spark.Spark;

import static hero.constants.HeroConstants.PLAYER1;

public class InventoryController {
    HeroService heroService;

    ItemService itemService;

    public InventoryController() {
        heroService = HeroService.getInstance();
        itemService = ItemService.getInstance();
    }

    public void registerRoutes() {
        Spark.get("/inventory", getInventory);
        Spark.post("/claim-reward", claimReward);
    }
    private final Route getInventory = (req, res) -> {
        Hero hero = heroService.get(PLAYER1);

        JsonObject jsonResponse = new JsonObject();

        Gson gson = new Gson();
        jsonResponse.add("inventory", gson.toJsonTree(hero.getInventory()));

        jsonResponse.add("equipment", gson.toJsonTree(hero.getEquipment().getEquipmentDto()));

        res.status(200);
        return gson.toJson(jsonResponse);
    };

    private final Route claimReward = (req, res) -> {
        Hero hero = heroService.get(PLAYER1);

        JsonObject jsonResponse = new JsonObject();

        Gson gson = new Gson();

        JsonObject requestBody = JsonParser.parseString(req.body()).getAsJsonObject();

        String itemId = requestBody.get("itemId").getAsString();

        if (itemId == null) {
            throw new RuntimeException("Отcутствует обязательное поле itemId");
        }

        var item = itemService.get(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Предмета с таким идентификатором не найдено"));

        hero.getInventory().add(item);

        res.status(200);
        return gson.toJson(jsonResponse);
    };

}
