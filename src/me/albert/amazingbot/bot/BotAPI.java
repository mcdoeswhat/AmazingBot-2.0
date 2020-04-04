package me.albert.amazingbot.bot;

import com.google.gson.JsonObject;
import me.albert.amazingbot.AmazingBot;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class BotAPI {

    private BotClient client;

    public BotAPI(BotClient client){
        this.client = client;
    }

    public void sendGroupMsg(String groupID,String msg){
        JsonObject jo = new JsonObject();
        JsonObject params = new JsonObject();
        jo.addProperty("action","send_group_msg");
        params.addProperty("group_id",groupID);
        params.addProperty("message",msg);
        jo.add("params",params);
        client.send(jo.toString());
    }

    public void sendPrivateMsg(String userID,String msg){
        JsonObject jo = new JsonObject();
        JsonObject params = new JsonObject();
        jo.addProperty("action","send_private_msg");
        params.addProperty("user_id",userID);
        params.addProperty("message",msg);
        jo.add("params",params);
        client.send(jo.toString());
    }

    public void sendRawMsg(String msg){
        client.send(msg);
    }

    public void changeTitle(Long groupID,Long userID,String title){
        JsonObject jo = new JsonObject();
        JsonObject params = new JsonObject();
        jo.addProperty("action","set_group_card");
        params.addProperty("user_id",userID);
        params.addProperty("group_id",groupID);
        params.addProperty("card",title);
        jo.add("params",params);
        client.send(jo.toString());
    }

    public void setBind(Long userID,UUID uuid){
        FileConfiguration data = AmazingBot.getData().getConfig();
        data.set(String.valueOf(userID),uuid.toString());
        AmazingBot.getData().save();
    }

    public UUID getPlayer(Long userID){
        UUID uuid = null;
        FileConfiguration data = AmazingBot.getData().getConfig();
        if (data.getString(String.valueOf(userID)) != null){
            uuid = UUID.fromString(data.getString(String.valueOf(userID)));
        }
        return uuid;
    }

    public Long getUser(UUID playerID){
        Long userID = null;
        FileConfiguration data = AmazingBot.getData().getConfig();
        for (String key : data.getConfigurationSection("").getKeys(false)){
            if (data.getString(key).equalsIgnoreCase(playerID.toString())){
                return
                        Long.parseLong(key);
            }
        }
        return userID;
    }


}
