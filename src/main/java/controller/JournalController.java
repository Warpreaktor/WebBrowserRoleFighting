package controller;

import com.google.gson.Gson;
import fight.FightService;
import spark.Route;
import spark.Spark;

public class JournalController {

    public JournalController() {
    }

    public void registerRoutes() {
        Spark.get("/journal/getMessage", getMessage);
    }

    private final Route getMessage = (req, res) -> {
        var journal = FightService.getInstance().getResult().getMessage();
        return new Gson().toJson(journal);
    };
}
