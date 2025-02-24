package controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import hero.Hero;
import hero.HeroService;
import item.lootbox.LootBoxService;
import item.ItemService;
import spark.Route;
import spark.Spark;

import static hero.constants.HeroConstants.PLAYER1;

public class LootBoxController {
    HeroService heroService;

    ItemService itemService;

    LootBoxService lootBoxService;

    public LootBoxController() {
        heroService = HeroService.getInstance();
        itemService = ItemService.getInstance();
        lootBoxService = LootBoxService.getInstance();
    }

    public void registerRoutes() {
        Spark.get("/lootbox", lootBox);
    }
    private final Route lootBox = (req, res) -> {
        Hero hero = heroService.get(PLAYER1);

        JsonObject jsonResponse = new JsonObject();

        Gson gson = new Gson();
        jsonResponse.add("lootbox", gson.toJsonTree(lootBoxService.generateLoot()));

        res.status(200);
        return gson.toJson(jsonResponse);
    };

}
