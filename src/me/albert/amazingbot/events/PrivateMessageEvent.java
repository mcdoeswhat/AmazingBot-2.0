package me.albert.amazingbot.events;

import me.albert.amazingbot.bot.Bot;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PrivateMessageEvent extends Event {
    private String msg;
    private Long userID;
    private String rawMessage;

    public PrivateMessageEvent(String msg,Long userID,String rawMessage){
        this.userID = userID;
        this.msg = msg;
        this.rawMessage = rawMessage;
    }

    private static final HandlerList handlers = new HandlerList();

    public void response(String msg){
        Bot.getApi().sendPrivateMsg(String.valueOf(userID),msg);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getMsg() {
        return msg;
    }

    public Long getUserID() {
        return userID;
    }



    public String getRawMessage() {
        return rawMessage;
    }
}
