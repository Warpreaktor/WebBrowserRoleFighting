package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

}
