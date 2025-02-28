package controller;

import com.google.gson.Gson;
import fight.FightService;
import spark.Route;
import spark.Spark;

public class FightController {

    public FightController() {
    }

    public void registerRoutes() {
        Spark.get("/fight", fight);
    }

    public final Route fight = (req, res) -> {
        res.type("application/json");

        return new Gson().toJson(FightService.getInstance().fight());
    };
}
