package controller;

import com.google.gson.Gson;
import fight.FightServiceV2;
import spark.Route;
import spark.Spark;

public class FightController {

    public FightController() {
    }

    public void registerRoutes() {
        Spark.get("/fight", fight);
        Spark.get("/fight/attack", attack);
    }

    public final Route fight = (req, res) -> {
        res.type("application/json");

        return new Gson().toJson(FightServiceV2.getInstance().fight());
    };

    public final Route attack = (req, res) -> {
        res.type("application/json");

        return new Gson().toJson(FightServiceV2.getInstance().combatMoves());
    };
}
