package me.albert.amazingbot.events;

import me.albert.amazingbot.bot.Bot;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GroupMessageEvent extends Event {
    private String msg;
    private Long userID;
    private Long groupID;
    private String rawMessage;

    public GroupMessageEvent(String msg,Long userID,Long groupID,String rawMessage){
        this.userID = userID;
        this.groupID = groupID;
        this.msg = msg;
        this.rawMessage = rawMessage;
    }

    private static final HandlerList handlers = new HandlerList();

    public void response(String msg){
        Bot.getApi().sendGroupMsg(String.valueOf(groupID),msg);
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

    public Long getGroupID() {
        return groupID;
    }

    public String getRawMessage() {
        return rawMessage;
    }
}
