package controller;

import com.google.gson.Gson;
import fight.FightService;
import fight.dto.FightResultDto;
import spark.Route;
import spark.Spark;

public class FightController {

    private FightService fightService;

    public FightController() {
        fightService = FightService.getInstance();
    }

    public void registerRoutes() {
        Spark.get("/fight", fight);
    }

    public final Route fight = (req, res) -> {
        res.type("application/json");

        return new Gson().toJson(fightService.fight());
    };
}
