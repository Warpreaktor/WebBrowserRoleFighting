package server;

import controller.HeroController;
import controller.FightController;
import controller.MainController;

import static spark.Spark.*;

public class WebServer {

    public static void main(String[] args) {
        port(4567);
        staticFiles.location("/static");

        // Разрешаем CORS для всех запросов
        options("/*", (req, res) -> {
            String accessControlRequestHeaders = req.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                res.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = req.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                res.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }
            return "OK";
        });

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            res.type("application/json");
        });

        // Регистрируем контроллеры
        HeroController heroController = new HeroController();
        heroController.registerRoutes();

        FightController fightController = new FightController();
        fightController.registerRoutes();

        MainController mainController = new MainController();
        mainController.registerRoutes();
    }
}

