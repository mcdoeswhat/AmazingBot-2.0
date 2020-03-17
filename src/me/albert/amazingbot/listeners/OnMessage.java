package me.albert.amazingbot.listeners;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.albert.amazingbot.events.GroupMessageEvent;
import me.albert.amazingbot.events.MessageReceiveEvent;
import me.albert.amazingbot.events.PrivateMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnMessage implements Listener {
    @EventHandler
    public void onMessage(MessageReceiveEvent e){
        if (!isJson(e.getMsg())){
            return;
        }
        Gson g = new Gson();
        JsonObject jo = g.fromJson(e.getMsg(),JsonObject.class);
        if (jo.has("message_type")){
            String messageType = jo.get("message_type").getAsString();
            if (messageType.equalsIgnoreCase("group")){
                Long groupID = jo.get("group_id").getAsLong();
                Long userID = jo.get("user_id").getAsLong();
                String message = jo.get("message").getAsString();
                GroupMessageEvent event = new GroupMessageEvent(message,userID,groupID,e.getMsg());
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
            if (messageType.equalsIgnoreCase("private")){
                Long userID = jo.get("user_id").getAsLong();
                String message = jo.get("message").getAsString();
                PrivateMessageEvent event = new PrivateMessageEvent(message,userID,e.getMsg());
                Bukkit.getServer().getPluginManager().callEvent(event);
            }
        }
    }

    private boolean isJson(String string){
        try {
            Gson g = new Gson();
            JsonObject jo = g.fromJson(string,JsonObject.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
