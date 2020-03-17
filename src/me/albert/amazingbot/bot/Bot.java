package me.albert.amazingbot.bot;

import me.albert.amazingbot.AmazingBot;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Bot {

    private static BotClient client;
    private static BotAPI api;
    private static Boolean connected = false;

    public static void start(){
        try {
            FileConfiguration config = AmazingBot.getInstance().getConfig();
            URI uri = new URI(config.getString("URI"));
            String token = config.getString("token");
            Map<String,String> httpHeaders = new HashMap<>();
            httpHeaders.put( "Authorization", "Bearer "+token );
            client = new BotClient(uri, httpHeaders);
            client.connect();
            api = new BotAPI(client);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static BotClient getClient() {
        return client;
    }

    public static BotAPI getApi() {
        return api;
    }

    public static void setApi(BotAPI api) {
        Bot.api = api;
    }

    public static void setClient(BotClient client) {
        Bot.client = client;
    }

    public static void stop(){
        if (client == null)
            return;
        client.close();
        System.out.println("[AmazingBot] 断开");
    }

    public static Boolean getConnected() {
        return connected;
    }

    public static void setConnected(Boolean connected) {
        Bot.connected = connected;
    }
}
