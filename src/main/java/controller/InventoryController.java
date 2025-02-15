package controller;

import com.google.gson.Gson;
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

        res.status(200);
        return new Gson().toJson(heroService.get(PLAYER1).getInventory());
    };

}
