package config;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {

    @Getter
    private static int port;

    @Getter
    private static String host;

    public static void initializeProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
            port = Integer.parseInt(properties.getProperty("server.port"));
            host = String.valueOf(properties.getProperty("server.host"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
