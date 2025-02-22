package controller;

import core.GameService;
import dto.hero.HeroRequestDto;
import com.google.gson.Gson;
import spark.Route;
import spark.Spark;

public class GameController {

    GameService gameService;

    public GameController() {
        this.gameService = GameService.getInstance();
    }

    public void registerRoutes() {
        Spark.post("/startNewGame", startNewGame);
        Spark.post("/continueGame", continueGame);
    }

    private final Route startNewGame = (req, res) -> {
        Gson gson = new Gson();
        HeroRequestDto player = gson.fromJson(req.body(), HeroRequestDto.class);

        gameService.startNewGame(player);

        return true;
    };

    private final Route continueGame = (req, res) -> {

        gameService.continueGame();

        return true;
    };
}
