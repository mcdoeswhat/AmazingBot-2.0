package me.albert.amazingbot.bot;

import me.albert.amazingbot.AmazingBot;
import me.albert.amazingbot.events.MessageReceiveEvent;
import org.bukkit.Bukkit;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/** This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded. */
public class BotClient extends WebSocketClient {

    public int taskID;


    public BotClient(URI serverUri , Draft draft ) {
        super( serverUri, draft );
    }

    public BotClient(URI serverURI ) {
        super( serverURI );
    }

    public BotClient(URI serverUri, Map<String, String> httpHeaders ) {
        super(serverUri, httpHeaders);
    }

    @Override
    public void onOpen( ServerHandshake handshakedata ) {
        System.out.println( "[Amazingbot] 客户端连接成功" );
        Bot.setConnected(true);
    }

    @Override
    public void onMessage( String message ) {
        MessageReceiveEvent event = new MessageReceiveEvent(message);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }



    @Override
    public void onClose( int code, String reason, boolean remote ) {
        Bot.setConnected(false);
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println( "[AmazingBot] 机器人连接关闭: " + ( remote ? "remote peer" : "us" ) + " Code: " + code + " Reason: " + reason );
        if (!AmazingBot.getInstance().getConfig().getBoolean("auto_reconnect")){
            return;
        }
        if (AmazingBot.getInstance().isEnabled()) {
            if (Bot.getConnected()){
                return;
            }
            System.out.println("[AmazingBot] 将在10秒后再次尝试连接");
            Bot.setConnected(true);
            taskID =
                    Bukkit.getScheduler().runTaskLater(AmazingBot.getInstance(), () -> {
                        Bot.setConnected(false);
                        Bot.start();
                    },20*10).getTaskId();
        }
    }

    @Override
    public void onError( Exception ex ) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }

    public static void main( String[] args ) throws URISyntaxException {
        BotClient c = new BotClient( new URI( "ws://localhost:8887" )); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        c.connect();
    }

}