package server;

import config.ApplicationProperties;
import controller.HeroController;
import controller.FightController;
import controller.InventoryController;
import controller.MainController;
import controller.ItemController;

import static spark.Spark.*;

public class WebServer {

    public static void main(String[] args) {
        ApplicationProperties.initializeProperties();

        port(ApplicationProperties.getPort());

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

            //Лог всех запросов
            System.out.println("Обрабатывается запрос: " + req.requestMethod() + " " + req.pathInfo());
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

