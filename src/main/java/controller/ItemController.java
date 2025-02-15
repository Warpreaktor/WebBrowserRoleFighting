package controller;

import hero.HeroService;
import com.google.gson.Gson;
import spark.Route;
import spark.Spark;
import item.weapon.ItemService;

public class ItemController {

    HeroService heroService;

    ItemService itemService;

    public ItemController() {
        heroService = HeroService.getInstance();
        itemService = ItemService.getInstance();
    }

    public void registerRoutes() {
        Spark.get("/item/:itemId", getItem);
    }

    private final Route getItem = (req, res) -> {
        String id = req.params("weaponId");

        return new Gson().toJson(itemService.get(id));
    };
}
